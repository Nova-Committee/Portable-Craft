package committee.nova.portablecraft.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import committee.nova.portablecraft.client.screen.base.BaseScreen;
import committee.nova.portablecraft.common.containers.CraftingContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 20:08
 * Version: 1.0
 */
public class CraftingScreen extends BaseScreen<CraftingContainer> {

    public CraftingScreen(CraftingContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);

    }

    @Override
    protected void init() {
        super.init();
        int x = getGuiLeft() + 108;
        int y = getGuiTop() + 62;
        final int size = 14;
    }

    @Override
    protected void renderLabels(MatrixStack pMatrixStack, int pX, int pY) {
        super.renderLabels(pMatrixStack, pX, pY);
    }

    @Override
    protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        this.drawBackground(pMatrixStack, new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png"));
    }
}
