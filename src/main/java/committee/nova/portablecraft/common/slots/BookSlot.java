package committee.nova.portablecraft.common.slots;

import committee.nova.portablecraft.common.menus.EnchantmentEditContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:39
 * Version: 1.0
 */
public class BookSlot extends SlotItemHandler {
    protected EnchantmentEditContainer container;

    public BookSlot(IItemHandler itemStackHandler, EnchantmentEditContainer container, int index, int xPosition, int yPosition) {
        super(itemStackHandler, index, xPosition, yPosition);
        this.container = container;
    }

    public void setChanged() {
        super.setChanged();
    }

    public int getMaxStackSize() {
        return 1;
    }

    public boolean mayPlace(ItemStack stack) {
        ItemStack itemStack = this.container.getTableInventory().getEnchantingStack();
        if (!itemStack.isEmpty() && stack.getItem() == Items.ENCHANTED_BOOK) {
            List<Map<Enchantment, Integer>> list = new ArrayList<>();
            list.add(EnchantmentHelper.getEnchantments(itemStack));
            list.add(EnchantmentHelper.getEnchantments(stack));
            if ((list.get(1)).size() <= 0) {
                return false;
            } else if ((list.get(0)).size() == 1 && (list.get(1)).size() > 1) {
                return false;
            } else {

                for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : (list.get(1)).entrySet()) {
                    Map.Entry<Enchantment, Integer> entry = enchantmentIntegerEntry;
                    Enchantment k = entry.getKey();
                    Integer v = entry.getValue();
                    if ((list.get(0)).containsKey(k)) {
                        int max = k.getMaxLevel();
                        int level = v;
                        int value = (Integer) ((Map) list.get(0)).get(k);
                        if (level + value > max) {
                            return false;
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public boolean mayPickup(Player player) {
        ItemStack itemStack = this.container.getTableInventory().getEnchantingStack();
        if (itemStack.getItem() == Items.ENCHANTED_BOOK) {
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
            return map.size() != 1 || map.entrySet().iterator().next().getValue() != 1;
        }

        return true;
    }
}
