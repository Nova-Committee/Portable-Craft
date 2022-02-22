package committee.nova.portablecraft.client.screen.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 20:08
 * Version: 1.0
 */
public abstract class BaseScreen<CONTAINER extends Container> extends ContainerScreen<CONTAINER> {
    final CONTAINER container;

    public BaseScreen(CONTAINER pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }


    protected void drawBackground(MatrixStack ms, ResourceLocation gui) {
        this.minecraft.getTextureManager().bind(gui);
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(ms, relX, relY, 0, 0, this.getXSize(), this.getYSize());
    }

    protected void drawSlot(MatrixStack ms, int x, int y, ResourceLocation texture) {
        drawSlot(ms, x, y, texture, 18);
    }

    protected void drawSlot(MatrixStack ms, int x, int y, ResourceLocation texture, int size) {
        this.minecraft.getTextureManager().bind(texture);
        blit(ms, getGuiLeft() + x, getGuiTop() + y, 0, 0, size, size, size, size);
    }



}
