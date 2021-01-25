package pl.artmc.drop.Minerals;

import org.bukkit.Material;

import static pl.artmc.drop.Main.getPlugin;

/**
 * Abstract class representing every Mineral which drop from stone
 */
public abstract class Mineral {
    protected double dropChance;
    protected double dropChanceVip;
    protected double dropChanceTurboDrop;
    protected Material material;
    protected String name;

    protected int exp;
    protected int id;

    /**
     * Constructor class, setting basic variables
     * @param chance Chance to drop
     * @param chanceVip Chance to drop for Vip
     * @param chanceTurboDrop Chance to drop for player's with TurboDrop
     * @param exp Amount of exp to give player when several block is dropped
     * @param material Material from Material class
     * @param name Name to display
     */
    public Mineral (String chance, String chanceVip, String chanceTurboDrop, String exp, Material material, String name, int id) {
        this.setChance(getPlugin().getConfig().getDouble(chance));
        this.setChanceVip(getPlugin().getConfig().getDouble(chanceVip));
        this.setChanceTurboDrop(getPlugin().getConfig().getDouble(chanceTurboDrop));
        this.setExp(getPlugin().getConfig().getInt(exp));
        this.setMaterial(material);
        this.setName(name);
        this.setId(id);
    }

    public Mineral() {}

    // Getters
    public abstract double getChance();
    public abstract double getChanceVip();
    public abstract double getChanceTurbo();
    public abstract int getExp();
    public abstract Material getMaterial();
    public abstract String getName();
    public abstract int getId();

    // Setters
    public abstract void setChance(double value);
    public abstract void setChanceVip(double value);
    public abstract void setChanceTurboDrop(double value);
    public abstract void setExp(int value);
    public abstract void setMaterial(Material material);
    public abstract void setName(String name);
    public abstract void setId(int id);
}
