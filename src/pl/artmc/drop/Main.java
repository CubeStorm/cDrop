package pl.artmc.drop;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.artmc.drop.Minerals.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    private static Plugin plugin;
    private final static Mineral[] MINERALS = new Mineral[8];
    public static Map<String, boolean[]> players;

    public static void Main() {
        MINERALS[0] = new Diamond();
        MINERALS[1] = new Emerald();
        MINERALS[2] = new Redstone();
        MINERALS[3] = new Lapis();
        MINERALS[4] = new Gold();
        MINERALS[5] = new Iron();
        MINERALS[6] = new Coal();
        MINERALS[7] = new Cobblestone();
    }

    @Override
    public void onEnable() {
        plugin = this;
        Main();
        players = new HashMap<>();
        this.createConfig();
        this.getServer().getPluginManager().registerEvents(new DropEvent(), this);
        this.getServer().getPluginManager().registerEvents(new GuiCommand(), this);
        this.getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("drop")).setExecutor(new GuiCommand());
    }

    /**
     * Sets all drops to true when player join to server
     * @param e Join event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String name = e.getPlayer().getName();
        boolean[] drops = { true, true, true, true, true, true, true, true };
        players.put(name, drops);
    }

    public void createConfig() {
        getConfig().addDefault("Drop.Diamond", 0.5);
        getConfig().addDefault("Drop.Emerald", 1);
        getConfig().addDefault("Drop.Redstone", 2);
        getConfig().addDefault("Drop.Lapis", 2);
        getConfig().addDefault("Drop.Gold", 3);
        getConfig().addDefault("Drop.Iron", 3);
        getConfig().addDefault("Drop.Coal", 4);
        getConfig().addDefault("VipChance", 3);
        getConfig().addDefault("TurboDropChance", 5);
        getConfig().addDefault("Exp.Diamond", 5);
        getConfig().addDefault("Exp.Emerald", 4);
        getConfig().addDefault("Exp.Redstone", 3);
        getConfig().addDefault("Exp.Lapis", 3);
        getConfig().addDefault("Exp.Gold", 2);
        getConfig().addDefault("Exp.Iron", 2);
        getConfig().addDefault("Exp.Coal", 1);

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    /**
     * Only used in Mineral Abstract class
     * @return instance of this plugin
     */
    public static Plugin getPlugin() { return plugin; }

    /**
     * SIMPLY STEP TO D.R.Y.
     * @return List of Minerals
     */
    public static Mineral[] getMinerals() { return MINERALS; }

    /**
     * @param s Message
     * @return Colorful message
     */
    public static String msg (String s) { return ChatColor.translateAlternateColorCodes('&', s); }
}
