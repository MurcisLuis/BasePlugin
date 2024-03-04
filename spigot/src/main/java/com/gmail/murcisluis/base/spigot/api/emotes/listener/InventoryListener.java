package com.gmail.murcisluis.base.spigot.api.emotes.listener;

import com.gmail.murcisluis.base.spigot.api.emotes.inventory.Menu;
import com.gmail.murcisluis.base.spigot.api.emotes.inventory.PaginatedMenu;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class InventoryListener implements Listener {

    private Map<Player, Menu> playerPaginatedMenuMap;

    public InventoryListener() {
        playerPaginatedMenuMap=new HashMap<>();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        Player player= (Player) event.getWhoClicked();
        Inventory inventory=event.getClickedInventory();
        if (!playerPaginatedMenuMap.containsKey(player)) return;
        Menu menu=playerPaginatedMenuMap.get(player);
        if (!menu.getInventory().equals(inventory)) return;
        menu.onClick(event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Player player= (Player) event.getWhoClicked();
        Inventory inventory=event.getView().getTopInventory();
        if (!playerPaginatedMenuMap.containsKey(player)) return;
        Menu menu=playerPaginatedMenuMap.get(player);
        if (!menu.getInventory().equals(inventory)) return;
        menu.onDrag(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player= (Player) event.getPlayer();
        Inventory inventory=event.getView().getTopInventory();
        if (!playerPaginatedMenuMap.containsKey(player)) return;
        playerPaginatedMenuMap.get(player).onClose(event);
        if (!playerPaginatedMenuMap.get(player).getInventory().equals(inventory) || playerPaginatedMenuMap.get(player).isClose()) return;
        playerPaginatedMenuMap.remove(player);
    }

}
