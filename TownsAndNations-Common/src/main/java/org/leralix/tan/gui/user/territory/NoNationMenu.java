package org.leralix.tan.gui.user.territory;

import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.entity.Player;
import org.leralix.tan.TownsAndNations;
import org.leralix.tan.data.territory.Region;
import org.leralix.tan.gui.BasicGui;
import org.leralix.tan.gui.cosmetic.IconKey;
import org.leralix.tan.gui.scope.BrowseScope;
import org.leralix.tan.gui.user.MainMenu;
import org.leralix.tan.lang.Lang;
import org.leralix.tan.listeners.chat.PlayerChatListenerStorage;
import org.leralix.tan.listeners.chat.events.CreateNation;
import org.leralix.tan.utils.constants.Constants;
import org.leralix.tan.utils.deprecated.GuiUtil;
import org.leralix.tan.utils.text.TanChatUtils;

import static org.leralix.lib.data.SoundEnum.NOT_ALLOWED;

public class NoNationMenu extends BasicGui {

    public NoNationMenu(Player player) {
        super(player, Lang.HEADER_NO_NATION, 3);
        open();
    }

    @Override
    public void open() {
        gui.setItem(2, 7, getBrowseNationsButton());
        gui.setItem(3, 1, GuiUtil.createBackArrow(player, p -> new MainMenu(player), langType));
        gui.open(player);
    }

    private GuiItem getBrowseNationsButton() {
        return iconManager.get(IconKey.BROWSE_NATION_ICON)
                .setName(Lang.GUI_NATION_BROWSE.get(tanPlayer))
                .setDescription(
                        Lang.GUI_NATION_BROWSE_DESC1.get(Integer.toString(TownsAndNations.getPlugin().getNationStorage().getAll().size())),
                        Lang.GUI_NATION_BROWSE_DESC2.get()
                )
                .setAction(action -> new BrowseTerritoryMenu(player, null, BrowseScope.NATIONS, p -> open()))
                .asGuiItem(player, langType);
    }
}
