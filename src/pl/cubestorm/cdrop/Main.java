package pl.cubestorm.cdrop;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.cubestorm.cdrop.Commands.Gui;
import pl.cubestorm.cdrop.Events.Drop;
import pl.cubestorm.cdrop.Minerals.*;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    public static Main instance;
    public static Mineral[] mineralList;

    @Override
    public void onEnable() {
        instance = this;

        Config.createConfig();

        mineralList[0] = new Diamond();
        mineralList[1] = new Emerald();
        mineralList[2] = new Redstone();
        mineralList[3] = new Lapis();
        mineralList[4] = new Gold();
        mineralList[5] = new Iron();
        mineralList[6] = new Coal();
        mineralList[7] = new Cobblestone();

        this.getServer().getPluginManager().registerEvents(new Drop(), this);
        this.getServer().getPluginManager().registerEvents(new Gui(), this);
        this.getServer().getPluginManager().registerEvents(new Player(), this);
        Objects.requireNonNull(getCommand("drop")).setExecutor(new Gui());
    }

    public static Main getInstance() { return instance; }

    public static Mineral[] getMineralList() { return mineralList; }

    public static String msg(String message) { return ChatColor.translateAlternateColorCodes('&', message); }
}
