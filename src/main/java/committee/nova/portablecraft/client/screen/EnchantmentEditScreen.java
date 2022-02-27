package committee.nova.portablecraft.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.common.containers.EnchantmentEditContainer;
import committee.nova.portablecraft.common.network.PacketHandler;
import committee.nova.portablecraft.common.network.packets.ClickButtonPacket;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 9:01
 * Version: 1.0
 */
public class EnchantmentEditScreen extends ContainerScreen<EnchantmentEditContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(PortableCraft.MOD_ID, "textures/gui/container/enchantment_edit_table.png");
    private static final int BUTTON_UP = 0;
    private static final int BUTTON_DOWN = 1;
    private static final int BUTTON_OK = 2;

    public EnchantmentEditScreen(EnchantmentEditContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageWidth = 194;
        this.imageHeight = 166;
    }

    public static void send(int type) {
        PacketHandler.INSTANCE.sendToServer(new ClickButtonPacket(type));
    }

    protected void init() {
        super.init();
        this.addButton(new ImageButton(this.leftPos + 80, this.topPos + 50, 18, 18, 0, this.imageHeight + 1, 18, TEXTURE, (button) -> {
            send(0);
        }));
        this.addButton(new ImageButton(this.leftPos + 151, this.topPos + 50, 18, 18, 18, this.imageHeight + 1, 18, TEXTURE, (button) -> {
            send(1);
        }));
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);
        this.blit(matrixStack, left, top, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        String title = I18n.get("container.disenchant");
        this.font.draw(matrixStack, title, (float) (this.imageWidth / 5 - this.font.width(title) / 2 + 3), 5.0F, 4210752);
        this.font.draw(matrixStack, I18n.get("key.categories.inventory"), 8.0F, (float) (this.imageHeight - 96 + 2), 4210752);
    }
}
