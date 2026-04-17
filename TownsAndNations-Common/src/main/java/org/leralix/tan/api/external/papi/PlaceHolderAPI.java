package org.leralix.tan.api.external.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.leralix.tan.TownsAndNations;
import org.leralix.tan.api.external.papi.entries.*;
import org.leralix.tan.storage.LocalChatStorage;
import org.leralix.tan.storage.stored.NationStorage;
import org.leralix.tan.storage.stored.PlayerDataStorage;
import org.leralix.tan.storage.stored.TownStorage;

import java.util.HashMap;
import java.util.Map;

public class PlaceHolderAPI extends PlaceholderExpansion {


    static final String PLACEHOLDER_NOT_FOUND = "[TAN] Placeholder not found";

    private final Map<String, PapiEntry> entries;

    @Override
    @NotNull
    public String getAuthor() {
        return "Leralix";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "tan";
    }

    @Override
    @NotNull
    public String getVersion() {
        return TownsAndNations.getPlugin().getCurrentVersion().toString();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    public PlaceHolderAPI(
            PlayerDataStorage playerDataStorage,
            TownStorage townStorage,
            NationStorage nationDataStorage,
            LocalChatStorage localChatStorage
    ) {
        entries = new HashMap<>();

        registerEntry(new GetFirstTerritoryIdWithName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new OtherPlayerTownName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new OtherPlayerTownTag(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new OtherPlayerNationName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new OtherPlayerTownColoredName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new OtherPlayerChatMode(playerDataStorage, townStorage, nationDataStorage, localChatStorage));
        registerEntry(new PlayerBalance(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerBiggerOverlordName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerChatMode(playerDataStorage, townStorage, nationDataStorage, localChatStorage));
        registerEntry(new PlayerNameHaveTown(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerNameIsTownLeader(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerNationBalance(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerNationChunkActualQuantity(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerNationName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerNationRankColoredName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerNationRankName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownBalance(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownChunkActualQuantity(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownChunkMaxQuantity(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownColoredName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownRankColoredName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownRankName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownRemainingQuantity(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownResidentQuantity(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerTownTag(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerColoredTownTag(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerColoredTownTagOrEmpty(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerColoredTownTagOrCustomText(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new TerritoryWithIdExist(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new TerritoryWithIdLeaderName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new TerritoryWithNameExist(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new TerritoryWithNameLeaderName(playerDataStorage, townStorage, nationDataStorage));

        registerEntry(new PlayerLocationChunkName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerLocationChunkTypeName(playerDataStorage, townStorage, nationDataStorage));
        registerEntry(new PlayerLocationPropertyExist(playerDataStorage, townStorage, regionDataStorage, nationDataStorage));
        registerEntry(new PlayerLocationPropertyIsOwner(playerDataStorage, townStorage, regionDataStorage, nationDataStorage));
        registerEntry(new PlayerLocationPropertyName(playerDataStorage, townStorage, regionDataStorage, nationDataStorage));
        registerEntry(new PlayerLocationPvpEnabled(playerDataStorage, townStorage, regionDataStorage, nationDataStorage));
    }

    void registerEntry(PapiEntry playerBalance) {
        entries.put(playerBalance.getIdentifier(), playerBalance);
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {

        String paramIdentifier = removePlaceholder(params);

        if (entries.containsKey(paramIdentifier)) {
            return entries.get(paramIdentifier).getData(player, params);
        }

        return PLACEHOLDER_NOT_FOUND;
    }

    public String removePlaceholder(String params) {
        return params.replaceAll("\\{[^}]*}", "{}");
    }

}
