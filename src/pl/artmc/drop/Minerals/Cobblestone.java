package pl.artmc.drop.Minerals;

import org.bukkit.Material;

public class Cobblestone extends Mineral {
    public Cobblestone() {
        this.dropChance = 100;
        this.dropChanceVip = 0;
        this.dropChanceTurboDrop = 0;
        this.material = Material.COBBLESTONE;
        this.name = null;
        this.exp = 0;
        this.id = 7;
    }

    @Override
    public double getChance() { return this.dropChance; }

    @Override
    public double getChanceVip() { return this.dropChanceVip; }

    @Override
    public double getChanceTurbo() {
        return this.dropChanceTurboDrop;
    }

    @Override
    public int getExp() { return this.exp; }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setChance(double value) {
        this.dropChance = value;
    }

    @Override
    public void setChanceVip(double value) {
        this.dropChanceVip = this.dropChance + value;
    }

    @Override
    public void setChanceTurboDrop(double value) { this.dropChanceTurboDrop = this.dropChance + value; }

    @Override
    public void setExp(int value) {
        this.exp = value;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
