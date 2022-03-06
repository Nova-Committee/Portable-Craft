package committee.nova.portablecraft.common.enchants;

import committee.nova.portablecraft.common.configs.ModConfig;
import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import committee.nova.portablecraft.common.items.FurnaceItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:04
 * Version: 1.0
 */
public class FurnaceSpeedEnchant extends BaseEnchant {
    public FurnaceSpeedEnchant(Enchantment.Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return ModConfig.COMMON.enchantFurnaceSpeedMaxLevel.get();
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 20;
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 1 + (pEnchantmentLevel - 1) * 11;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof FurnaceItem;
    }
}
