package org.leralix.tan.api.internal.managers;

import org.leralix.tan.storage.stored.NationStorage;
import org.leralix.tan.storage.stored.TownStorage;
import org.tan.api.getters.TanTerritoryManager;
import org.tan.api.interfaces.territory.TanNation;
import org.tan.api.interfaces.territory.TanTown;

import java.util.Collection;
import java.util.Optional;

/**
 * Placeholder for TanTerritoryManager <br>
 * This allows a single entry point for all territory related operations, It
 * stores the instance of both the {@link TownStorage}
 * and {@link NationStorage}
 */
public class TerritoryManager implements TanTerritoryManager {
    private final TownStorage townStorageInstance;
    private final NationStorage nationStorageInstance;

    public TerritoryManager(TownStorage townStorage, NationStorage nationStorage) {
        townStorageInstance = townStorage;
        nationStorageInstance = nationStorage;
    }


    @Override
    public Optional<TanTown> getTown(String uuid) {
        return Optional.ofNullable(townStorageInstance.get(uuid));
    }

    @Override
    public Optional<TanTown> getTownByName(String s) {
        return Optional.empty();
    }

    @Override
    public Collection<TanTown> getTowns() {
        return townStorageInstance.getAll().values().stream()
                .map(t -> (TanTown) t)
                .toList();
    }

    @Override
    public Optional<TanNation> getNation(String nationID) {
        return Optional.ofNullable(nationStorageInstance.get(nationID));
    }

    @Override
    public Optional<TanNation> getNationByName(String nationName) {
        return Optional.empty();
    }

    @Override
    public Collection<TanNation> getNations() {
        return nationStorageInstance.getAll().values().stream()
                .map(TanNation.class::cast)
                .toList();
    }
}
