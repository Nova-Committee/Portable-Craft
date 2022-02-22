package committee.nova.portablecraft.common.enchants;

import committee.nova.portablecraft.common.configs.ModConfig;
import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import committee.nova.portablecraft.common.items.BrewingStandItem;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:04
 * Version: 1.0
 */
public class BrewingStandSpeedEnchant extends BaseEnchant {
    public BrewingStandSpeedEnchant(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return ModConfig.COMMON.enchantBrewingStandSpeedMaxLevel.get();
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return  this.getMinCost(pEnchantmentLevel) + 20;
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return  1 + (pEnchantmentLevel - 1) * 11;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof BrewingStandItem;
    }
}
