package pl.cubestorm.cdrop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.cubestorm.cdrop.Minerals.Mineral;

import java.util.HashMap;
import java.util.UUID;

import static pl.cubestorm.cdrop.Main.getMineralList;


public class Player implements Listener {
    public static HashMap<UUID, HashMap<String, Boolean>> playerDropStateList;
    private final Mineral[] mineralList;

    public Player() {
        this.mineralList = getMineralList();
    }

    public static Boolean getDropState(UUID uuid, String item) {
        return playerDropStateList.get(uuid).get(item);
    }

    public static void changeDropState(UUID uuid, String item) {
        playerDropStateList.get(uuid).replace(item, !playerDropStateList.get(uuid).get(item));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        HashMap<String, Boolean> dropStateList = new HashMap<>();

        for (Mineral mineral: this.mineralList)
            dropStateList.put(mineral.getName(), true);

        playerDropStateList.put(event.getPlayer().getUniqueId(), dropStateList);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerDropStateList.remove(event.getPlayer().getUniqueId());
    }
}
