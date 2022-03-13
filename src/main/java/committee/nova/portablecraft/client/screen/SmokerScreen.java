package committee.nova.portablecraft.client.screen;

import committee.nova.portablecraft.client.screen.base.AbstractFurnaceScreen;
import committee.nova.portablecraft.common.menus.SmokerContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.recipebook.SmokerRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/13 9:03
 * Version: 1.0
 */
@Environment(value = EnvType.CLIENT)
public class SmokerScreen extends AbstractFurnaceScreen<SmokerContainer> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/smoker.png");

    public SmokerScreen(SmokerContainer handler, PlayerInventory inventory, Text title) {
        super(handler, new SmokerRecipeBookScreen(), inventory, title, TEXTURE);
    }
}
