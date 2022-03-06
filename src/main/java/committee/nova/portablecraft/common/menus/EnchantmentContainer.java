package committee.nova.portablecraft.common.menus;

import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/21 11:33
 * Version: 1.0
 */
public class EnchantmentContainer extends AbstractContainerMenu {
    public final int[] costs = new int[3];
    public final int[] enchantClue = new int[]{-1, -1, -1};
    private final Container enchantSlots = new SimpleContainer(3) {
        public void setChanged() {
            super.setChanged();
            EnchantmentContainer.this.slotsChanged(this);
        }
    };
    public final int[] levelClue = new int[]{-1, -1, -1};
    private final Random random = new Random();
    private final DataSlot enchantmentSeed = DataSlot.standalone();
    private final Level level;
    private final BlockPos playerPosition;
    private float power = 0.0F;

    public EnchantmentContainer(int pContainerId, Inventory pPlayerInventory) {
        super(ModContainers.ENCHANTMENT, pContainerId);
        this.level = pPlayerInventory.player.level;
        this.playerPosition = pPlayerInventory.player.blockPosition();
        this.addSlot(new Slot(this.enchantSlots, 0, 5, 44) {
            public boolean mayPlace(ItemStack pStack) {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 23, 44) {
            public boolean mayPlace(ItemStack pStack) {
                return net.minecraftforge.common.Tags.Items.GEMS_LAPIS.equals(pStack.getItem());
            }
        });

        //书架的多少影响附魔
        this.addSlot(new Slot(this.enchantSlots, 2, 41, 44) {
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() == Item.byBlock(Blocks.BOOKSHELF);
            }

            public int getMaxStackSize() {
                return 15;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(DataSlot.shared(this.costs, 0));
        this.addDataSlot(DataSlot.shared(this.costs, 1));
        this.addDataSlot(DataSlot.shared(this.costs, 2));
        this.addDataSlot(this.enchantmentSeed).set(pPlayerInventory.player.getEnchantmentSeed());
        this.addDataSlot(DataSlot.shared(this.enchantClue, 0));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 1));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 2));
        this.addDataSlot(DataSlot.shared(this.levelClue, 0));
        this.addDataSlot(DataSlot.shared(this.levelClue, 1));
        this.addDataSlot(DataSlot.shared(this.levelClue, 2));

    }

    public static EnchantmentContainer create(int pContainerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        return new EnchantmentContainer(pContainerId, playerInventory);
    }

    public void slotsChanged(Container pInventory) {
        if (pInventory == this.enchantSlots) {
            ItemStack bookshelf = pInventory.getItem(2);
            if (bookshelf != null) {
                this.power = (float) bookshelf.getCount();
            }
            ItemStack itemstack = pInventory.getItem(0);
            if (!itemstack.isEmpty() && itemstack.isEnchantable()) {

                this.random.setSeed(this.enchantmentSeed.get());

                for (int i1 = 0; i1 < 3; ++i1) {
                    this.costs[i1] = EnchantmentHelper.getEnchantmentCost(this.random, i1, (int) power, itemstack);
                    this.enchantClue[i1] = -1;
                    this.levelClue[i1] = -1;
                    if (this.costs[i1] < i1 + 1) {
                        this.costs[i1] = 0;
                    }
                    this.costs[i1] = net.minecraftforge.event.ForgeEventFactory.onEnchantmentLevelSet(level, playerPosition, i1, (int) power, itemstack, costs[i1]);
                }

                for (int j1 = 0; j1 < 3; ++j1) {
                    if (this.costs[j1] > 0) {
                        List<EnchantmentInstance> list = this.getEnchantmentList(itemstack, j1, this.costs[j1]);
                        if (!list.isEmpty()) {
                            EnchantmentInstance enchantmentdata = list.get(this.random.nextInt(list.size()));
                            this.enchantClue[j1] = Registry.ENCHANTMENT.getId(enchantmentdata.enchantment);
                            this.levelClue[j1] = enchantmentdata.level;
                        }
                    }
                }

                this.broadcastChanges();

            } else {
                for (int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }

    }

    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        ItemStack itemstack = this.enchantSlots.getItem(0);
        ItemStack itemstack1 = this.enchantSlots.getItem(1);
        int i = pId + 1;
        if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !pPlayer.getAbilities().instabuild) {
            return false;
        } else if (this.costs[pId] <= 0 || itemstack.isEmpty() || (pPlayer.experienceLevel < i || pPlayer.experienceLevel < this.costs[pId]) && !pPlayer.getAbilities().instabuild) {
            return false;
        } else {
            List<EnchantmentInstance> list = this.getEnchantmentList(itemstack, pId, this.costs[pId]);
            if (!list.isEmpty()) {
                pPlayer.onEnchantmentPerformed(itemstack, i);
                boolean flag = itemstack.getItem() == Items.BOOK;
                if (flag) {
                    itemstack = new ItemStack(Items.ENCHANTED_BOOK);
                    this.enchantSlots.setItem(0, itemstack);
                }

                for (EnchantmentInstance enchantmentData : list) {
                    if (flag) {
                        EnchantedBookItem.addEnchantment(itemstack, enchantmentData);
                    } else {
                        itemstack.enchant(enchantmentData.enchantment, enchantmentData.level);
                    }
                }

                if (!pPlayer.getAbilities().instabuild) {
                    itemstack1.shrink(i);
                    if (itemstack1.isEmpty()) {
                        this.enchantSlots.setItem(1, ItemStack.EMPTY);
                    }
                }

                pPlayer.awardStat(Stats.ENCHANT_ITEM);
                if (pPlayer instanceof ServerPlayer) {
                    CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer) pPlayer, itemstack, i);
                }

                this.enchantSlots.setChanged();
                this.enchantmentSeed.set(pPlayer.getEnchantmentSeed());
                this.slotsChanged(this.enchantSlots);
                this.level.playSound(null, playerPosition, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
            return true;
        }
    }

    private List<EnchantmentInstance> getEnchantmentList(ItemStack pStack, int pEnchantSlot, int pLevel) {
        this.random.setSeed((long) (this.enchantmentSeed.get() + pEnchantSlot));
        List<EnchantmentInstance> list = EnchantmentHelper.selectEnchantment(this.random, pStack, pLevel, false);
        if (pStack.getItem() == Items.BOOK && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }

        return list;
    }

    @OnlyIn(Dist.CLIENT)
    public int getGoldCount() {
        ItemStack itemstack = this.enchantSlots.getItem(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnchantmentSeed() {
        return this.enchantmentSeed.get();
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.clearContainer(pPlayer, enchantSlots);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 0) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex == 1) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() == Items.LAPIS_LAZULI) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack.getItem() == Item.byBlock(Blocks.BOOKSHELF)) {
                if (!this.moveItemStackTo(itemstack1, 2, 3, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }
                if (itemstack1.hasTag() && itemstack1.getCount() == 1) {
                    this.slots.get(0).set(itemstack1.copy());
                    itemstack1.setCount(0);
                } else if (!itemstack1.isEmpty()) {
                    this.slots.get(0).set(new ItemStack(itemstack1.getItem(), 1));
                    itemstack1.shrink(1);
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }


}
