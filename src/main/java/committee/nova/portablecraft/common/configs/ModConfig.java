package committee.nova.portablecraft.common.configs;

import committee.nova.portablecraft.PortableCraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 13:23
 * Version: 1.0
 */
@Mod.EventBusSubscriber(modid = PortableCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModConfig {

    public static final Common COMMON;
    public static final ForgeConfigSpec CONFIG_SPEC;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = specPair.getLeft();
        CONFIG_SPEC = specPair.getRight();
    }

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> enchantFurnaceSpeedLevelPercent, enchantFurnaceSpeedMaxLevel
                , enchantBrewingStandSpeedMaxLevel, enchantBrewingStandSpeedLevelPercent
                ;
        public final ForgeConfigSpec.ConfigValue<Boolean> enchantHeatHoldRegistry, enchantFurnaceSpeedLevelRegistry
                , enchantBrewingStandSpeedLevelRegistry
                ,
                craft_workbench, craft_anvil, craft_enderchest, craft_enchantment_table, craft_chest, craft_furnace, craft_brewing_stand, craft_smoker,
                craft_blast_furnace, craft_smithing_table, craft_stone_cutter;


        public Common(ForgeConfigSpec.Builder builder) {

            builder.push("furnace_setting").comment("熔炉，高炉，烟熏炉有关设置");
            enchantFurnaceSpeedLevelPercent = builder.comment("熔炉速度附魔的加速百分比").defineInRange("enchantFurnaceSpeedLevelPercent", 720, 0, Integer.MAX_VALUE);
            enchantFurnaceSpeedMaxLevel = builder.comment("熔炉速度附魔的默认最高等级").defineInRange("enchantFurnaceSpeedMaxLevel", 5, 0,Integer.MAX_VALUE);
            enchantHeatHoldRegistry = builder.comment("是否开启熔炉手持时热量附魔").define("enchantHeatHoldRegistry", true);
            enchantFurnaceSpeedLevelRegistry = builder.comment("是否开启熔炉熔炼速度附魔").define("enchantHeatHoldRegistry", true);
            builder.pop();

            builder.push("brewing_stand_setting").comment("炼药台有关设置");
            enchantBrewingStandSpeedMaxLevel = builder.comment("炼药台炼药速度附魔默认最高等级").defineInRange("enchantBrewingStandSpeedMaxLevel", 5, 0, Integer.MAX_VALUE);
            enchantBrewingStandSpeedLevelPercent = builder.comment("炼药台炼药速度附魔的加速百分比").defineInRange("enchantBrewingStandSpeedLevelPercent", 720, 0, Integer.MAX_VALUE);
            enchantBrewingStandSpeedLevelRegistry = builder.comment("是否开启炼药台炼药速度附魔").define("enchantHeatHoldRegistry", true);
            builder.pop();

            builder.push("item_setting").comment("便携物品启用设置");
            craft_workbench = builder.comment("是否开启便携工作台").define("craft_workbench", true);
            craft_anvil = builder.comment("是否开启便携铁砧").define("craft_anvil", true);
            craft_enderchest = builder.comment("是否开启便携末影箱").define("craft_enderchest", true);
            craft_enchantment_table = builder.comment("是否开启便携附魔台").define("craft_enchantment_table", true);
            craft_chest = builder.comment("是否开启便携箱子").define("craft_chest", true);
            craft_furnace = builder.comment("是否开启便携熔炉").define("craft_furnace", true);
            craft_brewing_stand = builder.comment("是否开启便携炼药台").define("craft_brewing_stand", true);
            craft_smoker = builder.comment("是否开启便携烟熏炉").define("craft_smoker", true);
            craft_blast_furnace = builder.comment("是否开启便携高炉").define("craft_blast_furnace", true);
            craft_smithing_table = builder.comment("是否开启便携锻造台").define("craft_smithing_table", true);
            craft_stone_cutter = builder.comment("是否开启便携切石机").define("craft_stone_cutter", true);
            builder.pop();

        }
    }
}
