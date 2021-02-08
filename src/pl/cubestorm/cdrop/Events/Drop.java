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
                if (true)
                    extraChance += Config.getTurboDropChance();

                if (Math.random() * 100 <= Config.getDropChance(object.getName()) + extraChance)
                    if (getDropState(player.getUniqueId(), object.getName()))
                        this.giveItem(object, player, itemInHand);
            }
        }
    }

    /**
     * Final method relating to give player dropped item, send message and give exp
     */
    public void giveItem(Mineral mineral, Player player, ItemStack itemInHand) {
        String mineralName = mineral.getName();

        // Check if player mine with FORTUNE (1/2/3) or not
        int amount = 1;
        if (mineral.getName().equals("Cobblestone"))
            amount = this.setValueFortune(itemInHand);

        // Check if player mine with SILK TOUCH or not
        Material droppedBlock = this.setDrop(itemInHand, mineral.getMaterial());

        this.addToInv(player, droppedBlock, amount);
        player.giveExp(Config.getExp(mineralName));

        if (mineralName.equals("Cobblestone"))
            player.sendMessage(msg("&7Gratulacje! Trafiles na &a" + mineralName + "&7 (&a" + amount + "&7)"));
    }

    /**
     * Check player inventory and add or drop item (using in giveItem())
     * @param player Player
     * @param material Item, instance of Mineral class
     * @param amount (value) Amount of dropped blocks
     */
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

    /**
     * Check if is full player's inventory (using in addToInv())
     * @param inv Players inventory
     * @return Boolean
     */
    public boolean isFullInv(Inventory inv) {
        for (ItemStack item: inv.getContents()) {
            if (item == null)
                return false;
        }
        return true;
    }

    /**
     * Check if player's item have fortune enchant and return value of item
     * @param item Item in player's main hand
     * @return Value (amount)
     */
    public int setValueFortune(ItemStack item) {
        int value = 1;
        if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
            value = (int) ((Math.random() * ((item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1) - 1)) + 1);

        return value;
    }

    /**
     * Check if player's item have silk touch enchant and return valid block
     * @param item Player's item in main hand
     * @param material Dropped block
     * @return Block, instance of Material class
     */
    public Material setDrop(ItemStack item, Material material) {
        if (!item.containsEnchantment(Enchantment.SILK_TOUCH)) {
            return material;
        } else {
            if (material.equals(Material.DIAMOND)) {
                return Material.DIAMOND_ORE;
            } else if (material.equals(Material.EMERALD)) {
                return Material.EMERALD_ORE;
            } else if (material.equals(Material.REDSTONE)) {
                return Material.REDSTONE_ORE;
            } else if (material.equals(Material.LAPIS_LAZULI)) {
                return Material.LAPIS_ORE;
            } else if (material.equals(Material.GOLD_INGOT)) {
                return Material.GOLD_ORE;
            } else if (material.equals(Material.IRON_INGOT)) {
                return Material.IRON_ORE;
            } else if (material.equals(Material.COAL)) {
                return Material.COAL_ORE;
            } else {
                return Material.STONE;
            }
        }
    }

}
