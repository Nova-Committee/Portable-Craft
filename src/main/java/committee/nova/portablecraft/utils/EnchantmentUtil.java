package committee.nova.portablecraft.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:16
 * Version: 1.0
 */
public class EnchantmentUtil {
    public static ItemStack getEnchantedItemStack(Enchantment k, Integer v) {
        ItemStack itemstack = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(itemstack, new EnchantmentData(k, v));
        return itemstack;
    }

    public static ItemStack getEnchantedItemStack(Map<Enchantment, Integer> map, ItemStack itemStack) {
        map.forEach((k, v) -> {
            EnchantedBookItem.addEnchantment(itemStack, new EnchantmentData(k, v));
        });
        return itemStack;
    }

    public static ItemStack getEnchantedItemStack(Map map) {
        ItemStack itemstack = new ItemStack(Items.ENCHANTED_BOOK);
        return getEnchantedItemStack(map, itemstack);
    }

    public static List<ItemStack> getEnchantedItemStackList(Map<Enchantment, Integer> map) {
        List<ItemStack> list = new ArrayList<>();
        map.forEach((k, v) -> {
            ItemStack itemstack = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(itemstack, new EnchantmentData(k, v));
            list.add(itemstack);
        });
        return list;
    }

    public static ItemStack setEnchantedItemStack(Map<Enchantment, Integer> map, ItemStack itemStack) {
        if (itemStack.getItem() != Items.ENCHANTED_BOOK) {
            EnchantmentHelper.setEnchantments(map, itemStack);
        } else {
            itemStack.setTag(new CompoundNBT());
            if (map.size() >= 1) {
                getEnchantedItemStack(map, itemStack);
            }
        }

        return itemStack;
    }
}
