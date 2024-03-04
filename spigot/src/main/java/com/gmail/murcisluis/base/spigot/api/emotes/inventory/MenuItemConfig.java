package com.gmail.murcisluis.base.spigot.api.emotes.inventory;

import com.gmail.murcisluis.base.common.api.BaseAPI;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import com.gmail.murcisluis.base.spigot.api.Lang;
import com.gmail.murcisluis.base.spigot.api.Settings;
import com.gmail.murcisluis.base.spigot.api.emotes.Data;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemConfig;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemEmote;
import com.gmail.murcisluis.base.spigot.api.emotes.data.PlayerData;
import com.gmail.murcisluis.base.spigot.api.emotes.listener.InventoryListener;
import com.gmail.murcisluis.base.spigot.api.emotes.manager.MenuManager;
import com.gmail.murcisluis.base.spigot.plugin.BaseSpigotPlugin;
import lombok.Getter;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.InvalidParameterException;
import java.util.List;

@Getter
public class MenuItemConfig implements Menu {

    protected static final int inventorySize = 27;

    private final Player player;
    private final Inventory inventory;
    private final int slot;
    private final ItemEmote itemEmote;
    private boolean close;


    /**
     *
     * @param player
     * @param clickedItem
     */
    public MenuItemConfig(Player player, ItemStack clickedItem) {
        ((BaseSpigot) BaseAPIFactory.get()).getListenerManager().getListener(InventoryListener.class).getPlayerPaginatedMenuMap().put(player,this);
        this.player=player;
        this.inventory=Bukkit.createInventory(null,inventorySize,Lang.MENU_CONFIG_TITLE.getValue());

        ItemMeta meta=clickedItem.getItemMeta();

        close=true;
        
        if (meta !=null) {
            this.itemEmote=Data.ITEM_DATA.getValue().stream().filter(item-> item.getLore().get(0).equals(meta.getLore().get(0))).findFirst().orElse(null);
            this.slot=Data.ITEM_DATA.getValue().indexOf(itemEmote);

            if (itemEmote == null) {
                player.closeInventory();
                return;
            }

        }else{
            player.closeInventory();
            throw new InvalidParameterException("I cant found the item no meta ");
        }


        drawMenu();
        player.openInventory(inventory);
    }

    public MenuItemConfig(Player player,ItemEmote itemEmote,int slot) {
        this.player=player;
        this.itemEmote=itemEmote;
        this.slot=slot;
        this.inventory=Bukkit.createInventory(null,inventorySize,Lang.MENU_CONFIG_TITLE.getValue());
        close=true;
        drawMenu();
        player.openInventory(inventory);
    }

    /**
     *
     * @param event
     */
    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int sloti=event.getSlot();

        if (Settings.MENU_CONFIG_NAME.getSlot() == sloti) {
            close=true;
            new AnvilGUI.Builder()
                    .plugin((BaseSpigotPlugin)BaseAPIFactory.getPlugin())
                    .title(Lang.MENU_CONFIG_NAME_TITLE.getValue())
                    .text(itemEmote.getName())
                    .itemLeft(Settings.MENU_CONFIG_NAME_ACCEPT.asItemStack())
                    .itemRight(Settings.MENU_CONFIG_NAME_CANCEL.asItemStack())
                    .onClick((slot, stateSnapshot) -> {
                        if (slot == AnvilGUI.Slot.OUTPUT) {
                            String inputText = stateSnapshot.getText();
                            itemEmote.setName(inputText);
                        }
                        return List.of(
                                AnvilGUI.ResponseAction.close()
                        );
                    }).onClose((playerClosing) -> {
                        playerClosing.getPlayer().openInventory(inventory);
                        close=false;
                    })
                    .open(player);
        }else if (Settings.MENU_CONFIG_LORE.getSlot() == sloti) {
            new MenuLoreConfig(player,itemEmote,slot);
        }else if (Settings.MENU_CONFIG_MATERIAL.getSlot() == sloti) {
            close=true;
            new AnvilGUI.Builder()
                    .plugin((BaseSpigotPlugin)BaseAPIFactory.getPlugin())
                    .title(Lang.MENU_CONFIG_MATERIAL_TITLE.getValue())
                    .text(Lang.MENU_CONFIG_MATERIAL_TITLE.getValue())
                    .itemLeft(new ItemStack(Material.valueOf(itemEmote.getMaterial())))
                    .interactableSlots(0)
                    .onClick((slot, stateSnapshot) -> {
                        if (slot != AnvilGUI.Slot.INPUT_LEFT) {
                            return List.of(AnvilGUI.ResponseAction.close());
                        }
                        return List.of();
                    })
                    .onClose((playerClosing) -> {
                        ItemStack result=playerClosing.getLeftItem();
                        if (result != null) {
                            itemEmote.setMaterial(result.getType().name());
                            if (result.getItemMeta() !=null) {
                                int modeldata= result.getItemMeta().hasCustomModelData() ? result.getItemMeta().getCustomModelData():0;
                                itemEmote.setCustomModelData(modeldata);
                            }
                        }
                        playerClosing.getPlayer().openInventory(inventory);
                        close=false;
                    })
                    .open(player);
        }

    }

    /**
     *
     * @param event
     */
    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);

    }

    /**
     *
     * @param event
     */
    @Override
    public void onClose(InventoryCloseEvent event) {
        Data.ITEM_DATA.getValue().set(slot,itemEmote);
        Data.ITEM_DATA.forceUpdateValue();
        Data.reload();

    }

    /**
     *
     */
    @Override
    public void drawMenu() {
        MenuManager.MENU_CONFIG.forEach((integer, itemConfig) ->
                inventory.setItem(integer,itemConfig.asItemStack()));;
    }

}
