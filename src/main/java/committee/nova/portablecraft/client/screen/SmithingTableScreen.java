package committee.nova.portablecraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import committee.nova.portablecraft.client.screen.base.AbstractRepairScreen;
import committee.nova.portablecraft.common.menus.SmithingTableContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/13 9:12
 * Version: 1.0
 */
@Environment(value = EnvType.CLIENT)
public class SmithingTableScreen extends AbstractRepairScreen<SmithingTableContainer> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/smithing.png");

    public SmithingTableScreen(SmithingTableContainer handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, TEXTURE);
        this.titleX = 60;
        this.titleY = 18;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.drawForeground(matrices, mouseX, mouseY);
    }
}
