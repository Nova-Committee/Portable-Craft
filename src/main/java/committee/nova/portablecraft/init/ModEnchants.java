package committee.nova.portablecraft.init;

import committee.nova.portablecraft.Portablecraft;
import committee.nova.portablecraft.common.enchants.BrewingStandSpeedEnchant;
import committee.nova.portablecraft.common.enchants.FurnaceSpeedEnchant;
import committee.nova.portablecraft.common.enchants.HeatHoldEnchant;
import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:00
 * Version: 1.0
 */
public class ModEnchants {

//    public static final EnchantmentTarget Furnace = create("furnace", (item) -> {
//        return item instanceof FurnaceItem || item instanceof SmokerItem || item instanceof BlastFurnaceItem;
//    });
//
//    public static final EnchantmentTarget BrewingStand = create("brewingstand", (item) -> {
//        return item instanceof BrewingStandItem;
//    });

    public static BaseEnchant FurnaceSpeed = new FurnaceSpeedEnchant();

    public static BaseEnchant BrewingStandSpeed = new BrewingStandSpeedEnchant();

    public static BaseEnchant HeadHold = new HeatHoldEnchant();


//    public static EnchantmentTarget create(String name, java.util.function.Predicate<Item> delegate) {
//        throw new IllegalStateException("Enum not extended");
//    }


    public static void init() {
        FurnaceSpeed = Registry.register(
                Registry.ENCHANTMENT,
                new Identifier(Portablecraft.MOD_ID, "furnace_speed"),
                new FurnaceSpeedEnchant()
        );
        BrewingStandSpeed = Registry.register(
                Registry.ENCHANTMENT,
                new Identifier(Portablecraft.MOD_ID, "brewing_stand_speed"),
                new BrewingStandSpeedEnchant()
        );
        HeadHold = Registry.register(
                Registry.ENCHANTMENT,
                new Identifier(Portablecraft.MOD_ID, "heat_hold"),
                new HeatHoldEnchant()
        );
    }

}
