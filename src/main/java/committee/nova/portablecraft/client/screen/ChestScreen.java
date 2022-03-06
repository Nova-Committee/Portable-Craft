package committee.nova.portablecraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import committee.nova.portablecraft.common.menus.ChestContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/25 13:16
 * Version: 1.0
 */
public class ChestScreen extends AbstractContainerScreen<ChestContainer> implements MenuAccess<ChestContainer> {
    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
    private final int containerRows;

    public ChestScreen(ChestContainer pChestMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pChestMenu, pPlayerInventory, pTitle);
        this.passEvents = false;
        int i = 222;
        int j = 114;
        this.containerRows = pChestMenu.getRowCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    protected void renderBg(PoseStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
        this.blit(pMatrixStack, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}
