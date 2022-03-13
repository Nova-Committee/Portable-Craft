package committee.nova.portablecraft.common.enchants;

import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import committee.nova.portablecraft.common.items.FurnaceItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:04
 * Version: 1.0
 */
public class HeatHoldEnchant extends BaseEnchant {


    public HeatHoldEnchant() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }


    @Override
    public int getMaxPower(int pEnchantmentLevel) {
        return 60;
    }

    @Override
    public int getMinPower(int pEnchantmentLevel) {
        return 10;
    }


    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof FurnaceItem;
    }
}
