package org.leralix.tan.listeners.chat.events;

import org.bukkit.entity.Player;
import org.leralix.tan.TownsAndNations;
import org.leralix.tan.data.player.ITanPlayer;
import org.leralix.tan.data.territory.Nation;
import org.leralix.tan.data.territory.Town;
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

        if (!player.hasPermission("tan.base.nation.create")) {
            TanChatUtils.message(player, Lang.PLAYER_NO_PERMISSION.get(tanPlayer));
            return false;
        }
        if (!tanPlayer.hasTown()) {
            TanChatUtils.message(player, Lang.PLAYER_NO_TOWN.get(tanPlayer));
            return false;
        }
        Town capital = tanPlayer.getTown();
        if (!capital.isLeader(tanPlayer)) {
            TanChatUtils.message(player, Lang.PLAYER_ONLY_LEADER_CAN_PERFORM_ACTION.get(tanPlayer));
            return false;
        }
        if (capital.haveOverlord()) {
            TanChatUtils.message(player, Lang.TOWN_ALREADY_HAVE_OVERLORD.get(tanPlayer));
            return false;
        }
        if (capital.getBalance() < cost) {
            TanChatUtils.message(player, Lang.TERRITORY_NOT_ENOUGH_MONEY.get(
                    tanPlayer,
                    capital.getColoredName(),
                    Double.toString(cost - capital.getBalance())
            ));
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

        createNation(player, tanPlayer, nationName, capital);
        return true;
    }

    public void createNation(Player player, ITanPlayer playerData, String nationName, Town capital) {
        capital.removeFromBalance(cost);
        Nation nation = TownsAndNations.getPlugin().getNationStorage().newNation(nationName, capital);

        EventManager.getInstance().callEvent(new NationCreatedInternalEvent(nation, playerData));
        FileUtil.addLineToHistory(Lang.NATION_CREATED_NEWSLETTER.get(player.getName(), nation.getName()));

        openGui(p -> PlayerGUI.dispatchPlayerNation(player, playerData), player);
    }
}
