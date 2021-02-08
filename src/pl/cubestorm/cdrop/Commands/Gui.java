package pl.cubestorm.cdrop.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.cubestorm.cdrop.Config;
import pl.cubestorm.cdrop.Minerals.Mineral;

import java.util.Arrays;
import java.util.UUID;

import static pl.cubestorm.cdrop.Main.getMineralList;
import static pl.cubestorm.cdrop.Main.msg;
import static pl.cubestorm.cdrop.Player.changeDropState;
import static pl.cubestorm.cdrop.Player.getDropState;

public class Gui implements CommandExecutor, Listener {
    private final Mineral[] MINERALS;
    private final Inventory INV;

    public Gui() {
        this.MINERALS = getMineralList();
        this.INV = Bukkit.createInventory(null, 9*4, "Drop");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;

            player.openInventory(this.INV);

            // Put the items into the inventory
            initItems(player.getUniqueId());

            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1.0f, 1.0f);

        } else
            sender.sendMessage("Nie mozesz wykonac tej komendy z poziomu konsoli");

        return true;
    }

    private void initItems(UUID uuid) {
        int[] indexes = {10, 20, 12, 22, 14, 24, 16, 35};
        int i = 0;

        for (Mineral object : this.MINERALS)
            this.INV.setItem(indexes[i++], item(object, uuid));
    }

    private ItemStack item(Mineral object, UUID uuid) {
        ItemStack item = new ItemStack(object.getMaterial(), 1);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(msg("&3" + object.getName()));

        // Set the lore of the item
        meta.setLore(Arrays.asList(this.lore(object, uuid)));

        item.setItemMeta(meta);

        return item;
    }

    private String[] lore(Mineral object, UUID uuid) {
        Boolean state = getDropState(uuid, object.getItem());
        String mess;

        if (state) mess = "&aOn";
        else mess = "&cOff";

        String[] lore = new String[5];

        lore[0] = msg("&7Gracz: &a" + Math.min(100, Config.getDropChance(object.getItem()))  + "%");
        lore[1] = msg("&7Vip: &a" + Math.min(100, (Config.getDropChance(object.getItem()) * Config.getVipChance())) + "%");
        lore[2] = msg("&7TurboDrop: &a" + Math.min(100, (Config.getDropChance(object.getItem()) * Config.getTurboDropChance())) + "%");
        lore[3] = msg("&8----------------------------");
        lore[4] = msg("&7Stan: " + mess);

        return lore;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Drop")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                HumanEntity player = event.getWhoClicked();
                for (Mineral object: getMineralList()) {
                    if (event.getCurrentItem().getType().equals(object.getMaterial())) {
                        changeDropState(player.getUniqueId(), object.getItem());
                        player.closeInventory();
                        player.openInventory(this.INV);
                        // Put the items into the inventory
                        initItems(player.getUniqueId());
                    }
                }
            }
        }
    }
}
