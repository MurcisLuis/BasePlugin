package com.gmail.murcisluis.base.spigot.api.emotes.inventory;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import com.gmail.murcisluis.base.spigot.api.Lang;
import com.gmail.murcisluis.base.spigot.api.emotes.listener.InventoryListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu implements Menu{
    protected int page = 0;
    protected final List<ItemStack> items;
    protected static final int inventorySize = 54; // Tamaño del inventario (9, 18, 27, ... 54)
    protected static final int itemsPerPage = 45; // Espacio para 45 items + 9 para controles de navegación
    @Getter
    protected final Inventory inventory;
    protected final Player player;

    public PaginatedMenu(Player player,String title) {
        this(player,title ,new ArrayList<>());
    }

    public PaginatedMenu(Player player,String title,List<ItemStack> items) {
        this.items = items;
        this.inventory = Bukkit.createInventory(player, inventorySize, title);
        this.player=player;
        ((BaseSpigot) BaseAPIFactory.get()).getListenerManager().getListener(InventoryListener.class).getPlayerPaginatedMenuMap().put(player,this);
        this.player.openInventory(this.inventory);
    }

    @Override
    public void drawMenu() {
        inventory.clear();
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, items.size());

        for (int i = 0; i < end - start; i++) {
            inventory.setItem(i, items.get(start + i));
        }

        drawNavigationButtons();
    }

    abstract void drawNavigationButtons();

    public void nextPage() {
        if ((page + 1) * itemsPerPage < items.size()) {
            page++;
            drawMenu();
        }

    }

    public void previousPage() {
        if (page > 0) {
            page--;
            drawMenu();
        }

    }
}
