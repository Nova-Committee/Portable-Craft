package committee.nova.portablecraft.common.menus;

import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 21:53
 * Version: 1.0
 */
public class EnchantmentContainer extends ScreenHandler {
    public final int[] enchantmentPower = new int[3];
    private final Inventory inventory = new SimpleInventory(3) {

        @Override
        public void markDirty() {
            super.markDirty();
            onContentChanged(this);
        }
    };
    public final int[] enchantmentId = new int[]{-1, -1, -1};
    public final int[] enchantmentLevel = new int[]{-1, -1, -1};
    private final Random random = new Random();
    private final Property seed = Property.create();
    private final World level;
    private final BlockPos playerPosition;
    private float power = 0.0F;

    public EnchantmentContainer(int syncId, PlayerInventory playerInventory) {
        super(ModContainers.ENCHANTMENT, syncId);
        this.level = playerInventory.player.world;
        this.playerPosition = playerInventory.player.getBlockPos();
        int i;
        this.addSlot(new Slot(this.inventory, 0, 5, 44) {

            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.inventory, 1, 23, 44) {

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.LAPIS_LAZULI);
            }
        });

        //书架的多少影响附魔
        this.addSlot(new Slot(this.inventory, 2, 41, 44) {
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Item.fromBlock(Blocks.BOOKSHELF);
            }

            public int getMaxItemCount() {
                return 15;
            }
        });

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addProperty(Property.create(this.enchantmentPower, 0));
        this.addProperty(Property.create(this.enchantmentPower, 1));
        this.addProperty(Property.create(this.enchantmentPower, 2));
        this.addProperty(this.seed).set(playerInventory.player.getEnchantmentTableSeed());
        this.addProperty(Property.create(this.enchantmentId, 0));
        this.addProperty(Property.create(this.enchantmentId, 1));
        this.addProperty(Property.create(this.enchantmentId, 2));
        this.addProperty(Property.create(this.enchantmentLevel, 0));
        this.addProperty(Property.create(this.enchantmentLevel, 1));
        this.addProperty(Property.create(this.enchantmentLevel, 2));
    }

    @Override
    public void onContentChanged(Inventory pInventory) {
        if (pInventory == this.inventory) {
            ItemStack bookshelf = pInventory.getStack(2);
            if (bookshelf != null) {
                this.power = (float) bookshelf.getCount();
            }
            ItemStack itemstack = pInventory.getStack(0);
            if (!itemstack.isEmpty() && itemstack.isEnchantable()) {

                this.random.setSeed(this.seed.get());
                for (int j = 0; j < 3; ++j) {
                    this.enchantmentPower[j] = EnchantmentHelper.calculateRequiredExperienceLevel(this.random, j, (int) power, itemstack);
                    this.enchantmentId[j] = -1;
                    this.enchantmentLevel[j] = -1;
                    if (this.enchantmentPower[j] >= j + 1) continue;
                    this.enchantmentPower[j] = 0;
                }

                for (int j1 = 0; j1 < 3; ++j1) {
                    if (this.enchantmentPower[j1] > 0) {
                        List<EnchantmentLevelEntry> list = this.generateEnchantments(itemstack, j1, this.enchantmentPower[j1]);
                        if (!list.isEmpty()) {
                            EnchantmentLevelEntry enchantmentdata = list.get(this.random.nextInt(list.size()));
                            this.enchantmentId[j1] = Registry.ENCHANTMENT.getRawId(enchantmentdata.enchantment);
                            this.enchantmentLevel[j1] = enchantmentdata.level;
                        }
                    }
                }

                this.sendContentUpdates();

            } else {
                for (int i = 0; i < 3; ++i) {
                    this.enchantmentPower[i] = 0;
                    this.enchantmentId[i] = -1;
                    this.enchantmentLevel[i] = -1;
                }
            }
        }
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id < 0 || id >= this.enchantmentPower.length) {
            Util.error(player.getName() + " pressed invalid button id: " + id);
            return false;
        }
        ItemStack itemStack = this.inventory.getStack(0);
        ItemStack itemStack2 = this.inventory.getStack(1);
        int i = id + 1;
        if ((itemStack2.isEmpty() || itemStack2.getCount() < i) && !player.getAbilities().creativeMode) {
            return false;
        }
        if (this.enchantmentPower[id] > 0 && !itemStack.isEmpty() && (player.experienceLevel >= i && player.experienceLevel >= this.enchantmentPower[id] || player.getAbilities().creativeMode)) {
            List<EnchantmentLevelEntry> list = this.generateEnchantments(itemStack, id, this.enchantmentPower[id]);
            if (!list.isEmpty()) {
                player.applyEnchantmentCosts(itemStack, i);
                boolean flag = itemStack.getItem() == Items.BOOK;
                if (flag) {
                    itemStack = new ItemStack(Items.ENCHANTED_BOOK);
                    this.inventory.setStack(0, itemStack);
                }

                for (EnchantmentLevelEntry enchantmentData : list) {
                    if (flag) {
                        EnchantedBookItem.addEnchantment(itemStack, enchantmentData);
                    } else {
                        itemStack.addEnchantment(enchantmentData.enchantment, enchantmentData.level);
                    }
                }

                if (!player.getAbilities().creativeMode) {
                    itemStack2.decrement(i);
                    if (itemStack2.isEmpty()) {
                        this.inventory.setStack(1, ItemStack.EMPTY);
                    }
                }

                player.incrementStat(Stats.ENCHANT_ITEM);
                if (player instanceof ServerPlayerEntity) {
                    Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity) player, itemStack2, i);
                }

                this.inventory.markDirty();
                this.seed.set(player.getEnchantmentTableSeed());
                this.onContentChanged(this.inventory);
                this.level.playSound(null, playerPosition, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
            return true;
        }
        return false;
    }

    private List<EnchantmentLevelEntry> generateEnchantments(ItemStack stack, int slot, int level) {
        this.random.setSeed(this.seed.get() + slot);
        List<EnchantmentLevelEntry> list = EnchantmentHelper.generateEnchantments(this.random, stack, level, false);
        if (stack.isOf(Items.BOOK) && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }
        return list;
    }

    public int getGoldCount() {
        ItemStack itemStack = this.inventory.getStack(1);
        if (itemStack.isEmpty()) {
            return 0;
        }
        return itemStack.getCount();
    }

    public int getEnchantmentSeed() {
        return this.seed.get();
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.dropInventory(player, inventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 1) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 2) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.isOf(Items.LAPIS_LAZULI)) {
                if (!this.insertItem(itemStack2, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.getItem() == Item.fromBlock(Blocks.BOOKSHELF)) {
                if (!this.insertItem(itemStack2, 2, 3, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.slots.get(0).hasStack() && this.slots.get(0).canInsert(itemStack2)) {
                ItemStack itemStack3 = itemStack2.copy();
                itemStack3.setCount(1);
                itemStack2.decrement(1);
                this.slots.get(0).setStack(itemStack3);
            } else {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }


}
