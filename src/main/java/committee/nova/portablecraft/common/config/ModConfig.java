package committee.nova.portablecraft.common.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 20:52
 * Version: 1.0
 */
public class ModConfig implements Config {

    @Comment(value = "熔炉速度附魔的加速百分比")
    public int enchantFurnaceSpeedLevelPercent = 720;
    @Comment(value = "熔炉速度附魔的默认最高等级")
    public int enchantFurnaceSpeedMaxLevel = 5;
    @Comment(value = "是否开启熔炉手持时热量附魔")
    public boolean enchantHeatHoldRegistry = true;
    @Comment(value = "是否开启熔炉熔炼速度附魔")
    public boolean enchantFurnaceSpeedLevelRegistry = true;

    @Comment(value = "炼药台炼药速度附魔的加速百分比")
    public int enchantBrewingStandSpeedLevelPercent = 720;
    @Comment(value = "炼药台炼药速度附魔默认最高等级")
    public int enchantBrewingStandSpeedMaxLevel = 5;
    @Comment(value = "是否开启炼药台炼药速度附魔")
    public boolean enchantBrewingStandSpeedLevelRegistry = true;

    public boolean craft_workbench = true;
    public boolean craft_anvil = true;
    public boolean craft_enderchest = true;
    public boolean craft_enchantment_table = true;
    public boolean craft_chest = true;
    public boolean craft_furnace = true;
    public boolean craft_brewing_stand = true;
    public boolean craft_smoker = true;
    public boolean craft_blast_furnace = true;
    public boolean craft_smithing_table = true;
    public boolean craft_stone_cutter = true;
    public boolean craft_enchantment_edit = true;

    @Override
    public String getName() {
        return "portablecraft";
    }
}
