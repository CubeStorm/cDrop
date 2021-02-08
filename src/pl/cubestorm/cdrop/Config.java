package pl.cubestorm.cdrop;

import static pl.cubestorm.cdrop.Main.getInstance;

public class Config {
    public static void createConfig() {
        getInstance().getConfig().options().copyDefaults();
        getInstance().saveConfig();
    }

    public static double getDropChance(String item) {
        return getInstance().getConfig().getDouble("dropChance." + item);
    }

    public static int getExp(String item) {
        return (int) getInstance().getConfig().getDouble("exp." + item);
    }

    public static String getName(String item) {
        return getInstance().getConfig().getString("name." + item);
    }

    public static double getVipChance() {
        return getInstance().getConfig().getDouble("vipChance");
    }

    public static double getTurboDropChance() {
        return getInstance().getConfig().getDouble("turboDropChance");
    }
}
