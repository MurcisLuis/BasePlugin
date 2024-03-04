package com.gmail.murcisluis.base.spigot.api.emotes.inventory;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.Lang;
import com.gmail.murcisluis.base.spigot.api.Settings;
import com.gmail.murcisluis.base.spigot.api.emotes.Data;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemEmote;
import com.gmail.murcisluis.base.spigot.api.emotes.manager.MenuManager;
import com.gmail.murcisluis.base.spigot.plugin.BaseSpigotPlugin;
import lombok.Getter;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
@Getter
public class MenuLoreConfig extends PaginatedMenu{
    private final ItemEmote itemEmote;
    private final int slot;
    private boolean close;
    public MenuLoreConfig(Player player, ItemEmote itemEmote,int slot) {
        super(player, Lang.MENU_CONFIG_LORE_TITLE.getValue());
        this.itemEmote = itemEmote;
        this.slot=slot;
        updateLore();
        close=true;
    }

    private void updateLore() {
        this.items.clear();
        this.items.addAll(itemEmote.getLore().stream().map(line->{
            ItemStack item= new ItemStack(Material.PAPER);
            ItemMeta meta=item.getItemMeta();
            if (meta!=null) {
                meta.setDisplayName(line);
                meta.setLore(List.of(Lang.MENU_LORE_LEFT_CLICK.getValue(),Lang.MENU_LORE_RIGHT_CLICK.getValue()));
            }
            item.setItemMeta(meta);
            return item;
        }).toList());
        drawMenu();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack currentItem=event.getCurrentItem();
        int clickslot=event.getSlot();
        if (currentItem == null) return;

        if (Settings.MENU_LORE_CLEAR.getSlot() == clickslot) {
            itemEmote.getLore().clear();
            updateLore();
        }else if (Settings.MENU_LORE_ADD.getSlot()== clickslot) {
            String defString="";
            new AnvilGUI.Builder()
                    .plugin((BaseSpigotPlugin) BaseAPIFactory.getPlugin())
                    .title(Lang.MENU_CONFIG_LORE_TITLE.getValue())
                    .text(defString)
                    .itemLeft(Settings.MENU_CONFIG_LORE_ACCEPT.asItemStack())
                    .itemRight(Settings.MENU_CONFIG_LORE_CANCEL.asItemStack())
                    .onClick((slot, stateSnapshot) -> {
                        if (slot == AnvilGUI.Slot.OUTPUT || slot == AnvilGUI.Slot.INPUT_LEFT) {
                            String inputText = stateSnapshot.getText();
                            itemEmote.getLore().add(inputText);
                            updateLore();
                        }
                        return List.of(
                                AnvilGUI.ResponseAction.close()
                        );
                    }).onClose((playerClosing) -> {
                        playerClosing.getPlayer().openInventory(inventory);
                        updateLore();
                    })
                    .open(player);

        }else if (event.getClick() == ClickType.LEFT){
            String defString="";
            if (currentItem.getItemMeta() != null && !Settings.MENU_LORE_ADD.equalItemStack(currentItem))
                defString=currentItem.getItemMeta().getDisplayName();
            int position= itemEmote.getLore().indexOf(defString);
            new AnvilGUI.Builder()
                    .plugin((BaseSpigotPlugin) BaseAPIFactory.getPlugin())
                    .title(Lang.MENU_CONFIG_LORE_TITLE.getValue())
                    .text(defString)
                    .itemLeft(Settings.MENU_CONFIG_LORE_ACCEPT.asItemStack())
                    .itemRight(Settings.MENU_CONFIG_LORE_CANCEL.asItemStack())
                    .onClick((slot, stateSnapshot) -> {
                        if (slot == AnvilGUI.Slot.OUTPUT || slot == AnvilGUI.Slot.INPUT_LEFT) {
                            String inputText = stateSnapshot.getText();
                            itemEmote.getLore().set(position,inputText);
                        }
                        return List.of(
                                AnvilGUI.ResponseAction.close()
                        );
                    }).onClose((playerClosing) -> {
                        updateLore();
                        playerClosing.getPlayer().openInventory(inventory);
                    })
                    .open(player);
        }else if (event.getClick() == ClickType.RIGHT) {
            if (currentItem.getItemMeta() == null) return;
            itemEmote.getLore().removeIf(text->text.equals(currentItem.getItemMeta().getDisplayName()));
            updateLore();

        }

    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }
    @Override
    public void onClose(InventoryCloseEvent event) {
        if (inventory == null|| event.getInventory().equals(inventory))return;
        new MenuItemConfig(player,itemEmote,slot);
    }

    @Override
    void drawNavigationButtons() {
        MenuManager.MENU_LORE.forEach((integer, itemConfig) ->
                inventory.setItem(integer,itemConfig.asItemStack()));;
    }
}
