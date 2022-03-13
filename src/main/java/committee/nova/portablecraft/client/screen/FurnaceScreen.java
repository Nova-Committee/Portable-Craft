package committee.nova.portablecraft.client.screen;

import committee.nova.portablecraft.client.screen.base.AbstractFurnaceScreen;
import committee.nova.portablecraft.common.menus.FurnaceContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/13 8:59
 * Version: 1.0
 */
@Environment(value = EnvType.CLIENT)
public class FurnaceScreen extends AbstractFurnaceScreen<FurnaceContainer> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/furnace.png");

    public FurnaceScreen(FurnaceContainer handler, PlayerInventory inventory, Text title) {
        super(handler, new FurnaceRecipeBookScreen(), inventory, title, TEXTURE);
    }

}
