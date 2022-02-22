package committee.nova.portablecraft.common.enchants;

import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import committee.nova.portablecraft.common.items.FurnaceItem;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:04
 * Version: 1.0
 */
public class HeatHoldEnchant extends BaseEnchant {
    public HeatHoldEnchant(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
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
    public int getMaxCost(int pEnchantmentLevel) {
        return  60;
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return  10;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof FurnaceItem;
    }
}
