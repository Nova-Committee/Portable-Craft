package committee.nova.portablecraft.client.screen;

import committee.nova.portablecraft.client.screen.base.AbstractFurnaceScreen;
import committee.nova.portablecraft.common.menus.BlastFurnaceContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.recipebook.BlastFurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/13 9:04
 * Version: 1.0
 */
@Environment(value = EnvType.CLIENT)
public class BlastFurnaceScreen extends AbstractFurnaceScreen<BlastFurnaceContainer> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/blast_furnace.png");

    public BlastFurnaceScreen(BlastFurnaceContainer container, PlayerInventory inventory, Text title) {
        super(container, new BlastFurnaceRecipeBookScreen(), inventory, title, TEXTURE);
    }
}
