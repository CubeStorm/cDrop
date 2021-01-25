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
}
