package committee.nova.portablecraft.client.screen.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.portablecraft.common.containers.base.AbstractRepairedContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:20
 * Version: 1.0
 */
public abstract class AbstractRepairScreen<T extends AbstractRepairedContainer> extends ContainerScreen<T> implements IContainerListener {
    private ResourceLocation menuResource;

    public AbstractRepairScreen(T pItemCombinerMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle, ResourceLocation pMenuTexture) {
        super(pItemCombinerMenu, pPlayerInventory, pTitle);
        this.menuResource = pMenuTexture;
    }

    protected void subInit() {
    }

    protected void init() {
        super.init();
        this.subInit();
        this.menu.addSlotListener(this);
    }

    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        RenderSystem.disableBlend();
        this.renderFg(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    protected void renderFg(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
    }

    protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(this.menuResource);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(pMatrixStack, i + 59, j + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(2).hasItem()) {
            this.blit(pMatrixStack, i + 99, j + 45, this.imageWidth, 0, 28, 21);
        }

    }

    public void refreshContainer(Container pContainerToSend, NonNullList<ItemStack> pItemsList) {
        this.slotChanged(pContainerToSend, 0, pContainerToSend.getSlot(0).getItem());
    }

    public void setContainerData(Container pContainer, int pVarToUpdate, int pNewValue) {
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot.
     */
    public void slotChanged(Container pContainerToSend, int pSlotInd, ItemStack pStack) {
    }
}
