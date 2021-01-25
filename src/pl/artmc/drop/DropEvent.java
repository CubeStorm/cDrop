package pl.artmc.drop;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.artmc.drop.Minerals.*;

import static pl.artmc.drop.Main.getMinerals;
import static pl.artmc.drop.Main.msg;

/**
 * Fully service of drop from stone class
 */
public class DropEvent implements Listener {
    private final Mineral[] MINERALS;

    /**
     * Constructor class, dependency injection
     */
    public DropEvent() {
        this.MINERALS = getMinerals();
    }

    /**
     * Event Handler relating to break stone by player
     * @param event - Break Stone event
     */
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Material block = event.getBlock().getType();
        if (block.equals(Material.STONE) || block.equals(Material.COBBLESTONE)) {
            // Block drop items
            event.setDropItems(false);

            Player p = event.getPlayer();

            ItemStack itemInHand = p.getInventory().getItemInMainHand();

            for (Mineral object : this.MINERALS) {
                if (Math.random() * 100 <= object.getChance()) {
                    String name = event.getPlayer().getName();
                    boolean[] drops = Main.players.get(name);

                    if(drops[object.getId()])
                        this.giveItem(object.getMaterial(), p, itemInHand, object.getName(), object.getExp());
                }
            }
        }
    }

    /**
     * Final method relating to give player dropped item, send message and give exp
     * @param drop Item, instance of Mineral class
     * @param p Player
     * @param itemInHand Item which player break block
     * @param msg Message to display
     * @param exp Exp from block
     */
    public void giveItem(Material drop, Player p, ItemStack itemInHand, String msg, int exp) {
        // Check if player mine with FORTUNE (1/2/3) or not
        int v = 1;
        if (msg != null)
            v = this.setValueFortune(itemInHand);

        // Check if player mine with SILK TOUCH or not
        Material droppedBlock = this.setDrop(itemInHand, drop);

        this.addToInv(p, droppedBlock, v);
        p.giveExp(exp);
        if (msg != null)
            p.sendMessage(msg("&7Gratulacje! Trafiles na &a" + msg + "&7 (&a" + v + "&7)"));
    }

    /**
     * Check player inventory and add or drop item (using in giveItem())
     * @param p Player
     * @param block Item, instance of Mineral class
     * @param v (value) Amount of dropped blocks
     */
    public void addToInv(Player p, Material block, int v) {
        if (block != null) {
            Inventory inv = p.getInventory();

            if (!this.isFullInv(inv)) {
                inv.addItem(new ItemStack(block, v));
            } else {
                World world = p.getWorld();
                world.dropItem(p.getLocation(), new ItemStack(block, v));
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
            } else if (material.equals(Material.COBBLESTONE)) {
                return Material.STONE;
            }
        }
        return Material.COBBLESTONE;
    }

}
