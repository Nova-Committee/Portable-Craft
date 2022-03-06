package committee.nova.portablecraft.client.screen.base;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 20:08
 * Version: 1.0
 */
public abstract class BaseScreen<CONTAINER extends AbstractContainerMenu> extends AbstractContainerScreen<CONTAINER> {
    final CONTAINER container;

    public BaseScreen(CONTAINER pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }


    protected void drawBackground(PoseStack ms, ResourceLocation gui) {
        RenderSystem.setShaderTexture(0, gui);
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(ms, relX, relY, 0, 0, this.getXSize(), this.getYSize());
    }

    protected void drawSlot(PoseStack ms, int x, int y, ResourceLocation texture) {
        drawSlot(ms, x, y, texture, 18);
    }

    protected void drawSlot(PoseStack ms, int x, int y, ResourceLocation texture, int size) {
        RenderSystem.setShaderTexture(0, texture);
        blit(ms, getGuiLeft() + x, getGuiTop() + y, 0, 0, size, size, size, size);
    }


}
