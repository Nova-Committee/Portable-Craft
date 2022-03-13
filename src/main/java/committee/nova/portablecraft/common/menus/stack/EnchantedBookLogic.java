//package committee.nova.portablecraft.common.menus.stack;
//
//import com.google.common.collect.Maps;
//import committee.nova.portablecraft.common.enchants.book.EnumBookStatus;
//import committee.nova.portablecraft.common.enchants.book.EnumMode;
//import committee.nova.portablecraft.common.menus.EnchantmentEditContainer;
//import committee.nova.portablecraft.utils.EnchantmentUtil;
//import net.minecraft.enchantment.Enchantment;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.item.EnchantedBookItem;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
///**
// * Description:
// * Author: cnlimiter
// * Date: 2022/2/27 8:25
// * Version: 1.0
// */
//public class EnchantedBookLogic {
//    private static final Integer PAGE_SIZE = 9;
//    private final EnchantmentEditContainer container;
//    private final ItemStackHandlerEnchant tableInventory;
//    private final ItemStackHandlerBookArray bookArrayInventory;
//    private Map<Enchantment, Integer> enchantments = Maps.newLinkedHashMap();
//    private Integer index = 1;
//    private EnumMode mode;
//    private EnumBookStatus status;
//    private boolean edit;
//    private boolean size;
//    private boolean update;
//
//    public EnchantedBookLogic(EnchantmentEditContainer container) {
//        this.mode = EnumMode.DEFAULT;
//        this.status = EnumBookStatus.OPEN;
//        this.edit = false;
//        this.size = false;
//        this.update = false;
//        this.container = container;
//        this.tableInventory = container.getTableInventory();
//        this.bookArrayInventory = container.getBookArrayInventory();
//    }
//
//    public void update() {
//        ItemStack itemStack = this.getEnchantingStack();
//        if (this.mode != EnumMode.DEFAULT && this.mode != EnumMode.NULL) {
//            this.enchantments = this.getEnchantments(itemStack);
//            this.index = 1;
//            this.status = EnumBookStatus.CLOSE;
//            this.clearBookArrayStack();
//            this.status = EnumBookStatus.OPEN;
//            this.mode = EnumMode.DEFAULT;
//            this.update();
//        } else {
//            if (this.enchantments.size() == 0) {
//                this.enchantments = this.getEnchantments(itemStack);
//                this.index = 1;
//            }
//
//            if (this.enchantments.size() == 1 && itemStack.getItem() == Items.ENCHANTED_BOOK) {
//                this.mode = EnumMode.BOOK_EDIT;
//            }
//
//            if (this.enchantments.size() != 0) {
//                if (this.mode != EnumMode.BOOK_EDIT) {
//                    if (itemStack.getItem() != Items.ENCHANTED_BOOK) {
//                        this.mode = EnumMode.ENCHANT;
//                    } else {
//                        this.mode = EnumMode.BOOK;
//                    }
//                }
//
//                if (this.mode != EnumMode.ENCHANT && this.mode != EnumMode.BOOK) {
//                    this.status = EnumBookStatus.CLOSE;
//                    this.updateBookArrayStack(true);
//                    this.status = EnumBookStatus.OPEN;
//                } else {
//                    this.status = EnumBookStatus.CLOSE;
//                    this.updateBookArrayStack();
//                    this.status = EnumBookStatus.OPEN;
//                }
//            } else if (itemStack.getItem() != Items.ENCHANTED_BOOK) {
//                this.mode = EnumMode.NULL;
//            }
//        }
//
//        System.out.println(this.mode);
//        System.out.println(this.enchantments.size());
//    }
//
//    public void close() {
//        if (this.enchantments.size() != 0) {
//            this.status = EnumBookStatus.CLOSE;
//            this.enchantments = Maps.newLinkedHashMap();
//            this.clearBookArrayStack();
//            this.mode = EnumMode.DEFAULT;
//            this.status = EnumBookStatus.OPEN;
//        }
//
//    }
//
//    public void bookArray() {
//        if (!this.edit && !this.size && this.status != EnumBookStatus.CLOSE) {
//            if (this.mode != EnumMode.DEFAULT) {
//                if (this.mode != EnumMode.BOOK_EDIT) {
//                    this.updateEnchantments();
//                    this.updateEnchantingStack();
//                    if (this.mode == EnumMode.NULL) {
//                        this.mode = EnumMode.ENCHANT;
//                    }
//
//                    if (this.size) {
//                        this.updateBookArrayStack();
//                    }
//
//                    this.size = false;
//                } else if (this.isUpdate()) {
//                    this.updateSimpleEnchantments();
//                    this.updateEnchantingStack();
//                    this.update = false;
//                }
//            }
//
//        }
//    }
//
//    public boolean isUpdate() {
//        return !this.update;
//    }
//
//    public void previous() {
//        if (this.enchantments.size() - 1 > PAGE_SIZE - 2) {
//            this.status = EnumBookStatus.CLOSE;
//            int i = this.index - 1;
//            if (i == 0) {
//                this.index = (int) Math.ceil(((float) this.enchantments.size() + 2.0F) / (float) PAGE_SIZE);
//            } else {
//                this.index = i;
//            }
//
//            this.updateBookArrayStack();
//            this.status = EnumBookStatus.OPEN;
//        }
//    }
//
//    public void next() {
//        if (this.enchantments.size() - 1 > PAGE_SIZE - 2) {
//            this.status = EnumBookStatus.CLOSE;
//            int last = (int) Math.ceil(((float) this.enchantments.size() + 2.0F) / (float) PAGE_SIZE);
//            int i = this.index + 1;
//            if (i == last + 1) {
//                this.index = 1;
//            } else {
//                this.index = i;
//            }
//
//            this.updateBookArrayStack();
//            this.status = EnumBookStatus.OPEN;
//        }
//    }
//
//    public void take() {
//        if (this.enchantments.size() != 0 && this.mode == EnumMode.ENCHANT) {
//            this.updateBookStack();
//            this.enchantments = Maps.newLinkedHashMap();
//            this.clearBookArrayStack();
//            this.updateEnchantingStack();
//            this.mode = EnumMode.NULL;
//        }
//
//    }
//
//    public void updateEnchantments() {
//        ItemStackHandlerBookArray inventory = this.bookArrayInventory;
//        int count = 1;
//        int start = this.index * PAGE_SIZE - PAGE_SIZE + 1;
//        int limit = this.index * PAGE_SIZE;
//        Map<Enchantment, Integer> map1 = Maps.newLinkedHashMap();
//        Map<Enchantment, Integer> map2 = Maps.newLinkedHashMap();
//        Map<Enchantment, Integer> map3 = Maps.newLinkedHashMap();
//        int length = inventory.getSlots();
//
//        for (int i = 0; i < length; ++i) {
//            if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
//                ItemStack stack = inventory.getStackInSlot(i);
//                Map<Enchantment, Integer> e = EnchantmentHelper.getEnchantments(stack);
//                if (e.size() > 1) {
//                    this.size = true;
//                } else {
//                    Enchantment enchantment = e.keySet().iterator().next();
//                    if (map2.containsKey(enchantment)) {
//                        map2.replace(enchantment, map2.get(enchantment) + e.get(enchantment));
//                        this.size = true;
//                        continue;
//                    }
//                }
//
//                map2.putAll(e);
//            }
//        }
//
//        for (Iterator<Map.Entry<Enchantment, Integer>> var13 = this.enchantments.entrySet().iterator(); var13.hasNext(); ++count) {
//            Map.Entry<Enchantment, Integer> entry = var13.next();
//            Enchantment key = entry.getKey();
//            Integer value = entry.getValue();
//            if (count < start) {
//                map1.put(key, value);
//            } else if (count > limit) {
//                map3.put(key, value);
//            }
//        }
//
//        Map<Enchantment, Integer> enchantments = Maps.newLinkedHashMap();
//        enchantments.putAll(map1);
//        enchantments.putAll(map2);
//        enchantments.putAll(map3);
//        enchantments.forEach((k, v) -> {
//            System.out.println(k.getDescriptionId() + "\t" + v);
//        });
//        this.enchantments = enchantments;
//    }
//
//    public void updateSimpleEnchantments() {
//        if (this.enchantments.size() != 0 || this.mode == EnumMode.BOOK_EDIT) {
//            this.update = true;
//            List<Integer> integers = new ArrayList<>();
//            List<Integer> slots = new ArrayList<>();
//            ItemStackHandlerBookArray inventory = this.bookArrayInventory;
//            int length = inventory.getSlots();
//
//            for (int i = 0; i < length; ++i) {
//                if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
//                    integers.add((this.getEnchantments(inventory.getStackInSlot(i)).entrySet().iterator().next()).getValue());
//                    slots.add(i);
//                }
//            }
//
//            if (this.enchantments.size() != 0) {
//                Map.Entry<Enchantment, Integer> entry = this.enchantments.entrySet().iterator().next();
//                Enchantment key = entry.getKey();
//                int level = entry.getValue();
//                if (this.getFirstEnchantments(this.tableInventory.getEnchantingStack()).getValue() == level) {
//                    int num;
//                    int i;
//                    if (level > integers.size()) {
//                        num = 0;
//
//                        for (i = 1; i <= level; ++i) {
//                            if (!integers.contains(i)) {
//                                num = i;
//                            }
//                        }
//
//                        if (level - num != 0) {
//                            this.enchantments.replace(key, level - num);
//                            this.updateBookArrayStack(true);
//                        } else {
//                            this.enchantments.remove(key);
//                            this.clearBookArrayStack();
//                        }
//
//                        this.updateBookArrayStack(true);
//                    } else if (level <= integers.size()) {
//                        for (num = 0; num < length; ++num) {
//                            if (inventory.getStackInSlot(num) != ItemStack.EMPTY) {
//                                Map.Entry<Enchantment, Integer> e = this.getEnchantments(inventory.getStackInSlot(num)).entrySet().iterator().next();
//                                if (!e.getKey().getDescriptionId().equals(key.getDescriptionId())) {
//                                    this.enchantments.put(e.getKey(), e.getValue());
//                                }
//                            }
//                        }
//
//                        num = 0;
//                        int abs;
//                        if (level == integers.size()) {
//                            for (i = 1; i <= level; ++i) {
//                                if (!integers.contains(i)) {
//
//                                    for (Integer slot : slots) {
//                                        abs = slot;
//                                        Map.Entry<Enchantment, Integer> e = this.getEnchantments(inventory.getStackInSlot(abs)).entrySet().iterator().next();
//                                        if (e.getValue() == i && key.getDescriptionId().equals(e.getKey().getDescriptionId())) {
//                                            num = i;
//                                            break;
//                                        }
//                                    }
//
//                                    if (num != 0) {
//                                        break;
//                                    }
//                                }
//                            }
//
//                            this.enchantments.replace(key, level - num);
//                        }
//
//                        if (num == 0 && this.enchantments.size() == 1) {
//                            i = 0;
//                            int count = 0;
//
//                            for (abs = 1; abs <= level; ++abs) {
//                                count += abs;
//                            }
//
//                            for (Iterator<Integer> var16 = integers.iterator(); var16.hasNext(); i += i) {
//                                i = var16.next();
//                            }
//
//                            abs = level + i - count;
//                            if (key.getMaxLevel() >= abs) {
//                                this.enchantments.replace(key, abs);
//                                this.edit = true;
//                                this.updateBookArrayStack(true);
//                                this.edit = false;
//                            }
//
//                            return;
//                        }
//
//                        this.edit = true;
//                        this.updateEnchantingStack();
//                        if (this.enchantments.size() > 1) {
//                            this.updateBookArrayStack();
//                            this.mode = EnumMode.BOOK;
//                        }
//
//                        this.edit = false;
//                    }
//
//                }
//            }
//        }
//    }
//
//    public void updateEnchantingStack() {
//        ItemStack itemStack = this.getEnchantingStack();
//        ServerPlayer mp = (ServerPlayer) this.container.getPlayer();
//        boolean flag = true;
//        if (itemStack.getItem() instanceof EnchantedBookItem && this.enchantments.size() == 0) {
//            flag = false;
//            this.tableInventory.setStackInSlot(0, ItemStack.EMPTY);
//            mp.connection.send(new ClientboundContainerSetSlotPacket(this.container.containerId, this.container.incrementStateId(), 0, ItemStack.EMPTY));
//        }
//
//        if (flag) {
//            EnchantmentUtil.setEnchantedItemStack(this.enchantments, itemStack);
//            mp.connection.send(new ClientboundContainerSetSlotPacket(this.container.containerId, this.container.incrementStateId(), 0, itemStack));
//        }
//
//    }
//
//    public void updateBookStack() {
//        ItemStack itemStack = new ItemStack(Items.ENCHANTED_BOOK);
//        ServerPlayer mp = (ServerPlayer) this.container.getPlayer();
//        EnchantmentUtil.setEnchantedItemStack(this.enchantments, itemStack);
//        mp.connection.send(new ClientboundContainerSetSlotPacket(this.container.containerId, this.container.incrementStateId(), 1, itemStack));
//    }
//
//    public void updateBookArrayStack() {
//        this.updateBookArrayStack(false);
//    }
//
//    public void updateBookArrayStack(boolean flag) {
//        ServerPlayer mp = (ServerPlayer) this.container.getPlayer();
//        int windowId = this.container.containerId;
//        List<ItemStack> stacks;
//        int length;
//        int slotId;
//        if (!flag) {
//            stacks = this.getBookStack();
//            length = this.bookArrayInventory.getSlots();
//        } else {
//            if (this.enchantments.size() == 0) {
//                return;
//            }
//
//            Enchantment key = this.enchantments.entrySet().iterator().next().getKey();
//            int level = this.enchantments.get(key);
//            stacks = new ArrayList<>();
//
//            for (slotId = 1; slotId <= level; ++slotId) {
//                stacks.add(EnchantmentUtil.getEnchantedItemStack(key, slotId));
//            }
//
//            length = this.bookArrayInventory.getSlots();
//        }
//
//        if (length != 0) {
//            for (int i = 0; i < length; ++i) {
//                ItemStack itemStack = ItemStack.EMPTY;
//                if (i < stacks.size()) {
//                    itemStack = stacks.get(i);
//                }
//
//                this.bookArrayInventory.setStackInSlot(i, itemStack);
//                slotId = i + 2;
//                mp.connection.send(new ClientboundContainerSetSlotPacket(windowId, this.container.incrementStateId(), slotId, itemStack));
//            }
//        }
//
//    }
//
//    public void clearBookArrayStack() {
//        ItemStackHandlerBookArray inventory = this.bookArrayInventory;
//        ServerPlayer mp = (ServerPlayer) this.container.getPlayer();
//        int length = inventory.getSlots();
//        int windowId = this.container.containerId;
//
//        for (int i = 0; i < length; ++i) {
//            if (!inventory.getStackInSlot(i).isEmpty() || i == length - 1) {
//                inventory.setStackInSlot(i, ItemStack.EMPTY);
//                int slotId = i + 2;
//                mp.connection.send(new ClientboundContainerSetSlotPacket(windowId, this.container.incrementStateId(), slotId, ItemStack.EMPTY));
//            }
//        }
//
//    }
//
//    public List<ItemStack> getBookStack() {
//        Map<Enchantment, Integer> map = Maps.newLinkedHashMap();
//        int count = 1;
//        int start = this.index * PAGE_SIZE - PAGE_SIZE + 1;
//        int limit = this.index * PAGE_SIZE;
//
//        for (Iterator<Map.Entry<Enchantment, Integer>> var5 = this.enchantments.entrySet().iterator(); var5.hasNext(); ++count) {
//            Map.Entry<Enchantment, Integer> entry = var5.next();
//            if (count >= start && count <= limit) {
//                Enchantment key = entry.getKey();
//                Integer value = entry.getValue();
//                map.put(key, value);
//                System.out.println(key.getDescriptionId() + "\t" + value);
//            }
//        }
//
//        return EnchantmentUtil.getEnchantedItemStackList(map);
//    }
//
//    public ItemStack getEnchantingStack() {
//        return this.tableInventory.getEnchantingStack();
//    }
//
//    public Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {
//        return EnchantmentHelper.getEnchantments(itemStack);
//    }
//
//    public Map.Entry<Enchantment, Integer> getFirstEnchantments() {
//        return this.enchantments.entrySet().iterator().next();
//    }
//
//    public Map.Entry<Enchantment, Integer> getFirstEnchantments(ItemStack itemStack) {
//        return EnchantmentHelper.getEnchantments(itemStack).entrySet().iterator().next();
//    }
//}
