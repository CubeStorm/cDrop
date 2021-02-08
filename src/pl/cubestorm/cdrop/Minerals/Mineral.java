package pl.cubestorm.cdrop.Minerals;

import org.bukkit.Material;
import pl.cubestorm.cdrop.Config;

public abstract class Mineral {
    protected double dropChance;
    protected Material material;
    protected String name;

    protected int exp;

    public Mineral(String item, Material material) {
        this.dropChance = Config.getDropChance(item);
        this.exp = Config.getExp(item);
        this.material = material;
        this.name = Config.getName(item);
    }

    public Material getMaterial() {
        return this.material;
    }
    public String getName() {
        return this.name;
    }
}
