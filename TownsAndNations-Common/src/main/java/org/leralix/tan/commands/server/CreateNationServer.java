package org.leralix.tan.commands.server;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.leralix.lib.commands.SubCommand;
import org.leralix.tan.TownsAndNations;
import org.leralix.tan.lang.Lang;
import org.leralix.tan.listeners.chat.events.CreateNation;
import org.leralix.tan.data.player.ITanPlayer;
import org.leralix.tan.storage.stored.PlayerDataStorage;
import org.leralix.tan.utils.text.NameFilter;
import org.leralix.tan.utils.text.TanChatUtils;

import java.util.Collections;
import java.util.List;

class CreateNationServer extends SubCommand {

    private final PlayerDataStorage playerDataStorage;

    public CreateNationServer(PlayerDataStorage playerDataStorage) {
        this.playerDataStorage = playerDataStorage;
    }

    @Override
    public String getName() {
        return "createnation";
    }

    @Override
    public String getDescription() {
        return "Create a nation";
    }

    @Override
    public int getArguments() {
        return 2;
    }

    @Override
    public String getSyntax() {
        return "/tanserver createnation <player_username> <nation name>";
    }

    @Override
    public List<String> getTabCompleteSuggestions(CommandSender player, String currentMessage, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {
        if(args.length < 3){
            TanChatUtils.message(commandSender, Lang.INVALID_ARGUMENTS);
            return;
        }

        StringBuilder nationNameBuilder = new StringBuilder();
        for(int i = 2; i < args.length; i++){
            nationNameBuilder.append(args[i]).append(" ");
        }
        String nationName = nationNameBuilder.toString().trim();

        if (!NameFilter.validateOrWarn(commandSender, nationName, NameFilter.Scope.NATION)) {
            return;
        }

        Player leader = commandSender.getServer().getPlayer(args[1]);
        if(leader == null){
            TanChatUtils.message(commandSender, Lang.PLAYER_NOT_FOUND);
            return;
        }
        if(TownsAndNations.getPlugin().getNationStorage().isNameUsed(nationName)){
            TanChatUtils.message(commandSender, Lang.NAME_ALREADY_USED);
            return;
        }
        ITanPlayer tanLeader = playerDataStorage.get(leader);
        if (!tanLeader.hasTown()) {
            TanChatUtils.message(commandSender, Lang.PLAYER_NO_TOWN);
            return;
        }
        new CreateNation(0).createNation(leader, tanLeader, nationName, tanLeader.getTown());
    }
}
