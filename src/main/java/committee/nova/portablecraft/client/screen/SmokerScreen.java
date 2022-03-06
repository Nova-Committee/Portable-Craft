package committee.nova.portablecraft.client.screen;

import committee.nova.portablecraft.client.screen.base.AbstractFurnaceScreen;
import committee.nova.portablecraft.common.menus.base.AbstractFurnaceContainer;
import net.minecraft.client.gui.screens.recipebook.SmokingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 13:54
 * Version: 1.0
 */
public class SmokerScreen extends AbstractFurnaceScreen {

    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/smoker.png");


    public SmokerScreen(AbstractFurnaceContainer pAbstractFurnaceMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pAbstractFurnaceMenu, new SmokingRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);
    }


}
