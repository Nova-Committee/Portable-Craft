package committee.nova.portablecraft.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.portablecraft.client.screen.base.AbstractRepairScreen;
import committee.nova.portablecraft.common.containers.RepairContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:24
 * Version: 1.0
 */
public class AnvilScreen extends AbstractRepairScreen<RepairContainer> {
    private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");
    private static final ITextComponent TOO_EXPENSIVE_TEXT = new TranslationTextComponent("container.repair.expensive");
    private TextFieldWidget name;

    public AnvilScreen(RepairContainer pAnvilMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pAnvilMenu, pPlayerInventory, pTitle, ANVIL_LOCATION);
        this.titleLabelX = 60;
    }

    public void tick() {
        super.tick();
        this.name.tick();
    }

    protected void subInit() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.name = new TextFieldWidget(this.font, i + 62, j + 24, 103, 12, new TranslationTextComponent("container.repair"));
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(35);
        this.name.setResponder(this::onNameChanged);
        this.children.add(this.name);
        this.setInitialFocus(this.name);
    }

    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.name.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.name.setValue(s);
    }

    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return !this.name.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.name.canConsumeInput() ? super.keyPressed(pKeyCode, pScanCode, pModifiers) : true;
    }

    private void onNameChanged(String p_214075_1_) {
        if (!p_214075_1_.isEmpty()) {
            String s = p_214075_1_;
            Slot slot = this.menu.getSlot(0);
            if (slot != null && slot.hasItem() && !slot.getItem().hasCustomHoverName() && p_214075_1_.equals(slot.getItem().getHoverName().getString())) {
                s = "";
            }

            this.menu.setItemName(s);
            this.minecraft.player.connection.send(new CRenameItemPacket(s));
        }
    }

    protected void renderLabels(MatrixStack pMatrixStack, int pX, int pY) {
        RenderSystem.disableBlend();
        super.renderLabels(pMatrixStack, pX, pY);
        int i = this.menu.getCost();
        if (i > 0) {
            int j = 8453920;
            ITextComponent itextcomponent;
            if (i >= 40 && !this.minecraft.player.abilities.instabuild) {
                itextcomponent = TOO_EXPENSIVE_TEXT;
                j = 16736352;
            } else if (!this.menu.getSlot(2).hasItem()) {
                itextcomponent = null;
            } else {
                itextcomponent = new TranslationTextComponent("container.repair.cost", i);
                if (!this.menu.getSlot(2).mayPickup(this.inventory.player)) {
                    j = 16736352;
                }
            }

            if (itextcomponent != null) {
                int k = this.imageWidth - 8 - this.font.width(itextcomponent) - 2;
                int l = 69;
                fill(pMatrixStack, k - 2, 67, this.imageWidth - 8, 79, 1325400064);
                this.font.drawShadow(pMatrixStack, itextcomponent, (float)k, 69.0F, j);
            }
        }

    }

    public void renderFg(MatrixStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.name.render(pPoseStack, pMouseX, pMouseY, pPartialTicks);
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot.
     */
    public void slotChanged(Container pContainerToSend, int pSlotInd, ItemStack pStack) {
        if (pSlotInd == 0) {
            this.name.setValue(pStack.isEmpty() ? "" : pStack.getHoverName().getString());
            this.name.setEditable(!pStack.isEmpty());
            this.setFocused(this.name);
        }

    }
}