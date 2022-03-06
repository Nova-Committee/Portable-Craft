package committee.nova.portablecraft.client.screen;

import committee.nova.portablecraft.client.screen.base.AbstractFurnaceScreen;
import committee.nova.portablecraft.common.menus.base.AbstractFurnaceContainer;
import net.minecraft.client.gui.screens.recipebook.BlastingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 13:54
 * Version: 1.0
 */
public class BlastFurnaceScreen extends AbstractFurnaceScreen {

    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/blast_furnace.png");


    public BlastFurnaceScreen(AbstractFurnaceContainer pAbstractFurnaceMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pAbstractFurnaceMenu, new BlastingRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);
    }


}
