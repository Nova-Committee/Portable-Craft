package committee.nova.portablecraft.client.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.portablecraft.client.widget.base.GuiButtonCore;
import committee.nova.portablecraft.client.widget.mixs.EnumNavigationTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 14:06
 * Version: 1.0
 */
public class NavigationButton extends GuiButtonCore {
    public EnumNavigationTab tab;
    public boolean isCurrentTab = false;

    public NavigationButton(int x, int y, EnumNavigationTab tab) {
        super(x, y, 16, 16, 0);
        this.tab = tab;
    }

    @Override
    public void drawButton(Minecraft mc, MatrixStack matrixStack, int mouseX, int mouseY, int guiLeft, int guiTop) {
        GlStateManager._pushMatrix();
        GlStateManager._enableAlphaTest();
        GlStateManager._enableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0f);
        //mc.getTextureManager().bind(ScreenUtils.BUTTONS);
        blit(matrixStack, x, y, 16 * tab.ordinal(), 16 * getHoverState(isCurrentTab || isMouseHovered(mc, mouseX, mouseY)), 16, 16);

        if (isMouseHovered(mc, mouseX - guiLeft, mouseY - guiTop)) {
            FontRenderer fontRenderer = mc.font;
            String text = tab.getTranslatedName();
            fontRenderer.draw(matrixStack, text, x - fontRenderer.width(text) / 2f + 8, y - 10, 0xFFFFFF);
        }
        GlStateManager._popMatrix();
    }

    public void setMain() {
        isCurrentTab = true;
    }
}
