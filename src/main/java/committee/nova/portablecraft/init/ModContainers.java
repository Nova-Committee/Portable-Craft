package committee.nova.portablecraft.init;

import committee.nova.portablecraft.Portablecraft;
import committee.nova.portablecraft.common.menus.*;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 19:31
 * Version: 1.0
 */
public class ModContainers {
    public static ScreenHandlerType<CraftingContainer> CRAFT;
    public static ScreenHandlerType<FurnaceContainer> FURNACE;
    public static ScreenHandlerType<SmokerContainer> SMOKER;
    public static ScreenHandlerType<BlastFurnaceContainer> BLAST_FURNACE;
    public static ScreenHandlerType<SmithingTableContainer> SMITHING;
    public static ScreenHandlerType<AnvilContainer> ANVIL;
    public static ScreenHandlerType<EnchantmentContainer> ENCHANTMENT;
    public static ScreenHandlerType<StonecutterContainer> STONECUTTER;
    public static ScreenHandlerType<ChestContainer> GENERIC_9x6;
    //public static ScreenHandlerType<EnchantmentEditContainer> ENCHANTMENT_EDIT;

    public static void init() {
        FURNACE = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "furnace1"), FurnaceContainer::new);
        SMOKER = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "smoker1"), SmokerContainer::new);
        BLAST_FURNACE = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "blast_furnace1"), BlastFurnaceContainer::new);
        SMITHING = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "smithing_table1"), SmithingTableContainer::new);
        ANVIL = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "anvil1"), AnvilContainer::new);
        ENCHANTMENT = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "enchantment_table1"), EnchantmentContainer::new);
        STONECUTTER = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "stone_cutter1"), StonecutterContainer::new);
        GENERIC_9x6 = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "generic1_9x6"), ChestContainer::createGeneric9x6);
        //ENCHANTMENT_EDIT = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "enchantment_edit_table"), SmokerContainer::new);
        CRAFT = ScreenHandlerRegistry.registerSimple(new Identifier(Portablecraft.MOD_ID, "craft1"), CraftingContainer::new);

    }

}
