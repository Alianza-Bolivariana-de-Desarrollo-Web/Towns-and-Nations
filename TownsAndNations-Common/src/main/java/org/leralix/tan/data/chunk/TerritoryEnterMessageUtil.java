package org.leralix.tan.data.chunk;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.leralix.tan.data.territory.Nation;
import org.leralix.tan.data.territory.Territory;
import org.leralix.tan.data.territory.Town;
import org.leralix.tan.lang.Lang;
import org.leralix.tan.lang.LangType;

public final class TerritoryEnterMessageUtil {

    private TerritoryEnterMessageUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void sendEnterTerritoryMessage(Player player, Territory territoryData, boolean displayTerritoryColor, LangType langType) {
        TextComponent name = displayTerritoryColor
                ? territoryData.getCustomColoredName()
                : new TextComponent(territoryData.getColoredName());

        String subtitle = Lang.PLAYER_ENTER_TERRITORY_CHUNK.get(langType, name.toLegacyText());
        String title = "";
        if (territoryData instanceof Town town) {
            var optOverlord = town.getOverlordInternal();
            if (optOverlord.isPresent() && optOverlord.get() instanceof Nation nation) {
                title = nation.getColoredName();
            }
        }
        player.sendTitle(title, subtitle, 5, 40, 20);
        player.sendMessage(subtitle);

        String description = territoryData.getDescription();
        if (description == null || description.isBlank()) {
            description = subtitle;
        }
        TextComponent textComponent = new TextComponent(description);
        textComponent.setColor(ChatColor.GRAY);
        textComponent.setItalic(true);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }

    public static void sendLeaveTerritoryMessage(Player player, Territory territoryData, boolean displayTerritoryColor) {
        TextComponent name = displayTerritoryColor
                ? territoryData.getCustomColoredName()
                : new TextComponent(territoryData.getColoredName());

        String subtitle = "§fLeaving : " + name.toLegacyText();
        String title = "";
        if (territoryData instanceof Town town) {
            var optOverlord = town.getOverlordInternal();
            if (optOverlord.isPresent() && optOverlord.get() instanceof Nation nation) {
                title = nation.getColoredName();
            }
        }
        player.sendTitle(title, subtitle, 5, 35, 15);
        player.sendMessage(subtitle);
    }
}
