package pl.artmc.drop;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.artmc.drop.Minerals.*;

import java.util.Arrays;

import static pl.artmc.drop.Main.getMinerals;
import static pl.artmc.drop.Main.msg;

public class GuiCommand implements CommandExecutor, Listener {
    private final Mineral[] MINERALS;
    private final Inventory INV;

    /**
     * Class constructor, dependency injection, creating new inventory without owner
     */
    public GuiCommand() {
        this.MINERALS = getMinerals();

        // Create a new inventory
        this.INV = Bukkit.createInventory(null, 9*4, "Drop");
    }

    /**
     * Gui command /drop
     * @param sender Command sender
     * @param cmd Command
     * @param label Command's label
     * @param args Command's arguments
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerName = player.getName();
            player.openInventory(this.INV);

            // Put the items into the inventory
            initializeItems(playerName);

            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1.0f, 1.0f);

            return true;
        }
        return false;
    }

    /**
     * Call where put some creating items
     */
    private void initializeItems(String player) {
        int[] indexes = {10, 20, 12, 22, 14, 24, 16, 35};
        int i = 0;

        for (Mineral object : this.MINERALS)
            this.INV.setItem(indexes[i++], item(object, player));
    }

    /**
     * Create item's metadata
     * @param object Object of Mineral class
     * @return Item with metadata
     */
    private ItemStack item(final Mineral object, final String player) {
        final ItemStack item = new ItemStack(object.getMaterial(), 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        if (object.getName() != null) {
            meta.setDisplayName(msg("&3" + object.getName()));
        } else {
            meta.setDisplayName(msg("&3Cobblestone"));
        }

        // Set the lore of the item
        meta.setLore(Arrays.asList(this.lore(object, player)));

        item.setItemMeta(meta);

        return item;
    }

    private String[] lore(Mineral object, String player) {
        String[] lore = new String[6];
        User user = new User();
        lore[0] = msg("&7Gracz: &a" + object.getChance() + "%");
        lore[1] = msg("&7Vip: &a" + (object.getChance() + object.getChanceVip()) + "%");
        lore[2] = msg("&7TurboDrop: &a" + (object.getChance() + object.getChanceTurbo()) + "%");
        lore[3] = msg("&8----------------------------");
        lore[4] = msg("&7Stan: " + user.getState(player, object.getId()));
        lore[5] = msg("&8----------------------------");

        return lore;
    }


    /**
     * Cancel grabbing and dragging item's from gui
     * @param e Drag event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        for (Mineral object : this.MINERALS) {
            if (e.getInventory().contains(object.getMaterial()))
                e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                HumanEntity player = e.getWhoClicked();
                if (e.getCurrentItem().getType().equals(object.getMaterial())) {
                    new User(player.getName(), object.getId());
                    player.closeInventory();
                    player.openInventory(this.INV);
                    // Put the items into the inventory
                    initializeItems(player.getName());
                }
            }
        }
    }
}
