package committee.nova.portablecraft.common.enchants.base;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:01
 * Version: 1.0
 */
public abstract class BaseEnchant extends Enchantment {
    protected BaseEnchant(Rarity rarityIn, EnchantmentTarget typeIn, EquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    public abstract boolean isEnabled();

    public int getCurrentLevelTool(ItemStack stack) {
        if (stack.isEmpty() == false && EnchantmentHelper.get(stack).containsKey(this)
                && stack.getItem() != Items.ENCHANTED_BOOK) {
            return EnchantmentHelper.get(stack).get(this);
        }
        return -1;
    }

    protected int getCurrentArmorLevelSlot(LivingEntity player, EquipmentSlot type) {
        ItemStack armor = player.getEquippedStack(type);
        int level = 0;
        if (armor.isEmpty() == false && EnchantmentHelper.get(armor) != null
                && EnchantmentHelper.get(armor).containsKey(this)) {
            level = EnchantmentHelper.getLevel(this, armor);
        }
        return level;
    }

    protected int getCurrentArmorLevel(LivingEntity player) {
        EquipmentSlot[] armors = new EquipmentSlot[]{
                EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS
        };
        int level = 0;
        for (EquipmentSlot slot : armors) {
            ItemStack armor = player.getEquippedStack(slot);
            if (armor.isEmpty() == false
                    && EnchantmentHelper.get(armor) != null
                    && EnchantmentHelper.get(armor).containsKey(this)) {
                int newlevel = EnchantmentHelper.getLevel(this, armor);
                if (newlevel > level) {
                    level = newlevel;
                }
            }
        }
        return level;
    }

    protected int getLevelAll(LivingEntity p) {
        return Math.max(getCurrentArmorLevel(p), getCurrentLevelTool(p));
    }

    protected ItemStack getFirstArmorStackWithEnchant(LivingEntity player) {
        if (player == null) {
            return ItemStack.EMPTY;
        }
        for (ItemStack main : player.getArmorItems()) {
            if ((main.isEmpty() == false) && EnchantmentHelper.get(main).containsKey(this)) {
                return main;
            }
        }
        return ItemStack.EMPTY;
    }

    protected int getCurrentLevelTool(LivingEntity player) {
        if (player == null) {
            return -1;
        }
        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();
        return Math.max(getCurrentLevelTool(main), getCurrentLevelTool(off));
    }
}
