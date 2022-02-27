package committee.nova.portablecraft.init;

import committee.nova.portablecraft.PortableCraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 14:09
 * Version: 1.0
 */
public enum ModTranslate {
    CRAFT(false, "item.portablecraft.craft1");

    final String key;

    ModTranslate(boolean prefix, String key) {
        this.key = prefix ? "info." + PortableCraft.MOD_ID + "." + key : key;
    }

    @Nonnull
    public String t() {
        return LanguageMap.getInstance().getOrDefault(key);
    }

    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public String format(Object... args) {
        return I18n.get(key, args);
    }

    @Nonnull
    public TranslationTextComponent getTextComponent() {
        return new TranslationTextComponent(key);
    }

    @Nonnull
    public TranslationTextComponent getTextComponent(Object... args) {
        return new TranslationTextComponent(key, args);
    }

    @Nonnull
    @Override
    public String toString() {
        // work on server side which uses en_us
        return t();
    }
}
