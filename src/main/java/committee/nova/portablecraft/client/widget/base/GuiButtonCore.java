package committee.nova.portablecraft.client.widget.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 14:12
 * Version: 1.0
 */
public abstract class GuiButtonCore extends AbstractGui {

    public boolean clickable = true;

    public int x;
    public int y;

    public int width;
    public int height;

    public int id;

    protected String text;

    public GuiButtonCore(int x, int y, int width, int height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        GlStateManager._enableBlend();
        GlStateManager._disableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.color4f(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.vertex(left, bottom, 0.0D).endVertex();
        bufferbuilder.vertex(right, bottom, 0.0D).endVertex();
        bufferbuilder.vertex(right, top, 0.0D).endVertex();
        bufferbuilder.vertex(left, top, 0.0D).endVertex();
        tessellator.end();
        GlStateManager._enableTexture();
        GlStateManager._disableBlend();
    }

    public static void accurateBlit(double left, double right, double bottom, double top, float uvLeft, float uvTop, float uvRight, float uvBottom) {
        double z = 0;
        BufferBuilder builder = Tessellator.getInstance().getBuilder();
        builder.begin(7, DefaultVertexFormats.POSITION_TEX);
        builder.vertex(left, top, z).uv(uvLeft, uvBottom).endVertex();
        builder.vertex(right, top, z).uv(uvTop, uvBottom).endVertex();
        builder.vertex(right, bottom, z).uv(uvTop, uvRight).endVertex();
        builder.vertex(left, bottom, z).uv(uvLeft, uvRight).endVertex();
        builder.end();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.end(builder);
    }

    public abstract void drawButton(Minecraft mc, MatrixStack matrixStack, int mouseX, int mouseY, int guiLeft, int guiTop);

    public void updateButton(float partialTicks, int mouseX, int mouseY) {
    }

    protected int getHoverState(boolean b) {
        return b ? 1 : 0;
    }

    public boolean isMouseHovered(Minecraft mc, int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.width && mouseY < this.y + this.height && mouseY >= this.y;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void drawTexturedRectangular(double x, double y, float textureX, float textureY, float width, float height) {
        float f = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        double zLevel = 0;
        bufferbuilder.vertex(x + 0, y + height, zLevel).uv(textureX * f, (textureY + height) * f).endVertex();
        bufferbuilder.vertex(x + width, y + height, zLevel).uv((textureX + width) * f, (textureY + height) * f).endVertex();
        bufferbuilder.vertex(x + width, y + 0, zLevel).uv((textureX + width) * f, textureY * f).endVertex();
        bufferbuilder.vertex(x + 0, y + 0, zLevel).uv(textureX * f, textureY * f).endVertex();
        tessellator.end();
    }

    public void accurateBlit(double left, double top, int uvLeft, int uvTop, double width, double height) {
        int texX = 256;
        int texY = 256;
        accurateBlit(left, left + width, top, top + height, (uvLeft + 0.0F) / (float) texX, (uvLeft + (float) width) / (float) texX, (uvTop + 0.0F) / (float) texY, (uvTop + (float) height) / (float) texY);
    }
}
