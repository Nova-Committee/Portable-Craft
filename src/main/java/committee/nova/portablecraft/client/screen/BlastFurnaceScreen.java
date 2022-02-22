package committee.nova.portablecraft.client.screen;

import committee.nova.portablecraft.client.screen.base.AbstractFurnaceScreen;
import committee.nova.portablecraft.common.containers.base.AbstractFurnaceContainer;
import net.minecraft.client.gui.recipebook.BlastFurnaceRecipeGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 13:54
 * Version: 1.0
 */
public class BlastFurnaceScreen extends AbstractFurnaceScreen {

    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft","textures/gui/container/blast_furnace.png");


    public BlastFurnaceScreen(AbstractFurnaceContainer pAbstractFurnaceMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pAbstractFurnaceMenu, new BlastFurnaceRecipeGui(), pPlayerInventory, pTitle, TEXTURE);
    }


}
