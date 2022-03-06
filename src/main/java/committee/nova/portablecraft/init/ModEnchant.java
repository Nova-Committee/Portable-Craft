package committee.nova.portablecraft.init;

import committee.nova.portablecraft.common.configs.ModConfig;
import committee.nova.portablecraft.common.enchants.BrewingStandSpeedEnchant;
import committee.nova.portablecraft.common.enchants.FurnaceSpeedEnchant;
import committee.nova.portablecraft.common.enchants.HeatHoldEnchant;
import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import committee.nova.portablecraft.common.items.BrewingStandItem;
import committee.nova.portablecraft.common.items.FurnaceItem;
import committee.nova.portablecraft.utils.RegistryUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:00
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEnchant {
    public static final EnchantmentCategory Furnace = EnchantmentCategory.create("furnace", (item) -> {
        return item instanceof FurnaceItem;
    });

    public static final EnchantmentCategory BrewingStand = EnchantmentCategory.create("brewingstand", (item) -> {
        return item instanceof BrewingStandItem;
    });


    public static final BaseEnchant FurnaceSpeed = (BaseEnchant) new FurnaceSpeedEnchant(Enchantment.Rarity.UNCOMMON, Furnace, EquipmentSlot.values()).setRegistryName("furnace_speed");

    public static final BaseEnchant BrewingStandSpeed = (BaseEnchant) new BrewingStandSpeedEnchant(Enchantment.Rarity.UNCOMMON, BrewingStand, EquipmentSlot.values()).setRegistryName("brewing_stand_speed");

    public static final BaseEnchant HeadHold = (BaseEnchant) new HeatHoldEnchant(Enchantment.Rarity.UNCOMMON, Furnace, EquipmentSlot.values()).setRegistryName("heat_hold");


    @SubscribeEvent
    public static void onEnchantRegister(final RegistryEvent.Register<Enchantment> event) {
        IForgeRegistry<Enchantment> r = event.getRegistry();

        if (ModConfig.COMMON.enchantHeatHoldRegistry.get()) {
            RegistryUtil.registerEnchant(r, HeadHold);
        }
        if (ModConfig.COMMON.enchantFurnaceSpeedLevelRegistry.get()) {
            RegistryUtil.registerEnchant(r, FurnaceSpeed);
        }
        if (ModConfig.COMMON.enchantBrewingStandSpeedLevelRegistry.get()) {
            RegistryUtil.registerEnchant(r, BrewingStandSpeed);
        }

    }
}
