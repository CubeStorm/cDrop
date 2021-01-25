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
    public double getChance() {
        return this.dropChance;
    }
    public double getChanceVip() {
        return this.dropChanceVip;
    }
    public double getChanceTurbo() {
        return this.dropChanceTurboDrop;
    }
    public int getExp() {
        return this.exp;
    }
    public Material getMaterial() {
        return this.material;
    }
    public String getName() {
        return this.name;
    }
    public int getId() {
        return this.id;
    }

    // Setters
    public void setChance(double value) {
        this.dropChance = value;
    }
    public void setChanceVip(double value) {
        this.dropChanceVip = value;
    }
    public void setChanceTurboDrop(double value) {
        this.dropChanceTurboDrop = value;
    }
    public void setExp(int value) {
        this.exp = value;
    }
    public void setMaterial(Material material) {
        this.material = material;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
}
