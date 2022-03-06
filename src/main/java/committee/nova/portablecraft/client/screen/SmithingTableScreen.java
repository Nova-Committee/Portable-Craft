package committee.nova.portablecraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import committee.nova.portablecraft.client.screen.base.AbstractRepairScreen;
import committee.nova.portablecraft.common.menus.SmithingTableContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:19
 * Version: 1.0
 */
public class SmithingTableScreen extends AbstractRepairScreen<SmithingTableContainer> {

    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation("textures/gui/container/smithing.png");

    public SmithingTableScreen(SmithingTableContainer pSmithingMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pSmithingMenu, pPlayerInventory, pTitle, SMITHING_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void renderLabels(PoseStack pMatrixStack, int pX, int pY) {
        RenderSystem.disableBlend();
        super.renderLabels(pMatrixStack, pX, pY);
    }
}
