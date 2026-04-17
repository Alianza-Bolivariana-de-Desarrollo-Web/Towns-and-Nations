package org.leralix.tan.storage.stored;

import org.jetbrains.annotations.NotNull;
import org.leralix.tan.data.player.ITanPlayer;
import org.leralix.tan.data.territory.Nation;
import org.leralix.tan.data.territory.Region;
import org.leralix.tan.data.territory.Town;

import java.util.Map;

public interface NationStorage {

    default Nation get(Region regionData){
        if(regionData == null){
            return null;
        }
        return get(regionData.getNationID());
    }

    default Nation get(ITanPlayer playerData){
        if(playerData == null || !playerData.hasNation()){
            return null;
        }
        return playerData.getNation();
    }

    Nation newNation(String name, @NotNull Town capital);

    Nation get(String nationID);

    boolean isNameUsed(String name);

    void delete(String id);

    Map<String, Nation> getAll();

    void save();
}
