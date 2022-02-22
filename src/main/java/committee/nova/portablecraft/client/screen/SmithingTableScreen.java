package committee.nova.portablecraft.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.portablecraft.client.screen.base.AbstractRepairScreen;
import committee.nova.portablecraft.common.containers.SmithingTableContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:19
 * Version: 1.0
 */
public class SmithingTableScreen extends AbstractRepairScreen<SmithingTableContainer> {

    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation("textures/gui/container/smithing.png");

    public SmithingTableScreen(SmithingTableContainer pSmithingMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pSmithingMenu, pPlayerInventory, pTitle, SMITHING_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void renderLabels(MatrixStack pMatrixStack, int pX, int pY) {
        RenderSystem.disableBlend();
        super.renderLabels(pMatrixStack, pX, pY);
    }
}
