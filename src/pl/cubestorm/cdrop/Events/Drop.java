package pl.cubestorm.cdrop.Events;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.cubestorm.cdrop.Config;
import pl.cubestorm.cdrop.Minerals.*;

import static pl.cubestorm.cdrop.Main.getMineralList;
import static pl.cubestorm.cdrop.Main.msg;
import static pl.cubestorm.cdrop.Player.getDropState;

public class Drop implements Listener {
    private final Mineral[] MINERALS;

    public Drop() {
        this.MINERALS = getMineralList();
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Material block = event.getBlock().getType();
        if (block.equals(Material.STONE) || block.equals(Material.COBBLESTONE)) {
            // Block drop items
            event.setDropItems(false);

            Player player = event.getPlayer();

            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            for (Mineral object : this.MINERALS) {
                double extraChance = 0;

                if (player.hasPermission("drop.vip"))
                    extraChance += Config.getVipChance();
                if (player.hasPermission("drop.turbodrop"))
                    extraChance += Config.getTurboDropChance();

                if (Math.random() * 100 <= Math.min(100, Config.getDropChance(object.getItem()) * extraChance))
                    if (getDropState(player.getUniqueId(), object.getItem()))
                        this.giveItem(object, player, itemInHand);
            }
        } else if (block.equals(Material.DIAMOND_ORE) || block.equals(Material.EMERALD_ORE) || block.equals(Material.REDSTONE_ORE) || block.equals(Material.LAPIS_ORE) || block.equals(Material.GOLD_ORE) || block.equals(Material.IRON_ORE) || block.equals(Material.COAL_ORE))
            event.setDropItems(false);
    }

    public void giveItem(Mineral mineral, Player player, ItemStack itemInHand) {
        String mineralItem = mineral.getItem();

        // Check if player mine with FORTUNE (1/2/3) or not
        int amount = 1;
        if (!mineralItem.equals("cobblestone"))
            amount = this.setValueFortune(itemInHand);

        // Check if player mine with SILK TOUCH or not
        Material droppedBlock = this.setDrop(itemInHand, mineral.getMaterial());

        this.addToInv(player, droppedBlock, amount);
        player.giveExp(Config.getExp(mineralItem));

        if (!mineralItem.equals("cobblestone"))
            player.sendMessage(msg("&7Gratulacje! Trafiles na &a" + mineral.getName() + "&7 (&a" + amount + "&7)"));
    }

    public void addToInv(Player player, Material material, int amount) {
        if (material != null) {
            Inventory inv = player.getInventory();

            if (!this.isFullInv(inv)) {
                inv.addItem(new ItemStack(material, amount));
            } else {
                World world = player.getWorld();
                world.dropItem(player.getLocation(), new ItemStack(material, amount));
            }
        }
    }

    public boolean isFullInv(Inventory inv) {
        for (ItemStack item: inv.getContents()) {
            if (item == null)
                return false;
        }
        return true;
    }

    public int setValueFortune(ItemStack item) {
        int value = 1;
        if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
            value = (int) ((Math.random() * ((item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1) - 1)) + 1);

        return value;
    }

    public Material setDrop(ItemStack item, Material material) {
        if (!item.containsEnchantment(Enchantment.SILK_TOUCH)) {
            return material;
        } else {
            if (material.equals(Material.DIAMOND))
                return Material.DIAMOND_ORE;
            else if (material.equals(Material.EMERALD))
                return Material.EMERALD_ORE;
            else if (material.equals(Material.REDSTONE))
                return Material.REDSTONE_ORE;
            else if (material.equals(Material.LAPIS_LAZULI))
                return Material.LAPIS_ORE;
            else if (material.equals(Material.GOLD_INGOT))
                return Material.GOLD_ORE;
            else if (material.equals(Material.IRON_INGOT))
                return Material.IRON_ORE;
            else if (material.equals(Material.COAL))
                return Material.COAL_ORE;
            else
                return Material.STONE;
        }
    }

}
