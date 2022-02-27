package committee.nova.portablecraft.client.widget.mixs;

import committee.nova.portablecraft.init.ModTranslate;

import javax.annotation.Nonnull;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 14:07
 * Version: 1.0
 */
public enum EnumNavigationTab {

    TAB_CRAFT(ModTranslate.CRAFT);

    private final ModTranslate tabName;
    private final int id;

    EnumNavigationTab(ModTranslate tabName) {
        this.tabName = tabName;
        id = ordinal() + 1;
    }

    @Nonnull
    public String getTranslatedName() {
        return tabName.t();
    }

    public int getId() {
        return id;
    }
}
