package org.leralix.tan.listeners.chat.events;

import org.bukkit.entity.Player;
import org.leralix.tan.TownsAndNations;
import org.leralix.tan.data.player.ITanPlayer;
import org.leralix.tan.data.territory.Nation;
import org.leralix.tan.events.EventManager;
import org.leralix.tan.events.events.NationCreatedInternalEvent;
import org.leralix.tan.gui.common.PlayerGUI;
import org.leralix.tan.lang.Lang;
import org.leralix.tan.listeners.chat.ChatListenerEvent;
import org.leralix.tan.utils.constants.Constants;
import org.leralix.tan.utils.file.FileUtil;
import org.leralix.tan.utils.text.NameFilter;
import org.leralix.tan.utils.text.TanChatUtils;

public class CreateNation extends ChatListenerEvent {

    private final int cost;

    public CreateNation(int cost) {
        super();
        this.cost = cost;
    }

    @Override
    public boolean execute(Player player, ITanPlayer tanPlayer, String message) {

        if (!player.hasPermission("tan.admin.commands")) {
            TanChatUtils.message(player, Lang.PLAYER_NO_PERMISSION.get(tanPlayer));
            return false;
        }

        String nationName = message == null ? "" : message.trim();

        if (!NameFilter.validateOrWarn(player, nationName, NameFilter.Scope.NATION)) {
            return false;
        }

        int maxSize = Constants.getNationMaxNameSize();
        if (nationName.length() > maxSize) {
            TanChatUtils.message(player, Lang.MESSAGE_TOO_LONG.get(tanPlayer, Integer.toString(maxSize)));
            return false;
        }

        if (TownsAndNations.getPlugin().getNationStorage().isNameUsed(nationName)) {
            TanChatUtils.message(player, Lang.NAME_ALREADY_USED.get(tanPlayer));
            return false;
        }

        createNation(player, tanPlayer, nationName);
        return true;
    }

    private void createNation(Player player, ITanPlayer playerData, String nationName) {
        Nation nation = TownsAndNations.getPlugin().getNationStorage().newNation(nationName, null);

        EventManager.getInstance().callEvent(new NationCreatedInternalEvent(nation, playerData));
        FileUtil.addLineToHistory(Lang.NATION_CREATED_NEWSLETTER.get(player.getName(), nation.getName()));

        openGui(p -> PlayerGUI.dispatchPlayerNation(player, playerData), player);
    }
}
