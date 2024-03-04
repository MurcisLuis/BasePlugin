package com.gmail.murcisluis.base.spigot.api.emotes.inventory;

import com.gmail.murcisluis.base.spigot.api.Lang;
import com.gmail.murcisluis.base.spigot.api.Settings;
import com.gmail.murcisluis.base.spigot.api.emotes.Data;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemConfig;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemEmote;
import com.gmail.murcisluis.base.spigot.api.emotes.manager.MenuManager;
import com.gmail.murcisluis.base.spigot.api.utils.item.ItemStackUtils;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public final class MenuAdmin extends PaginatedMenu {
    private Map<MythicMob, Boolean> selectedMobs = new HashMap<>();
    private boolean previewMode = false;
    private int savedPage = 0;
    private final boolean close;

    public MenuAdmin(Player player) {
        super(player, Lang.MENU_ADMIN_TITLE.getValue());
        MythicBukkit.inst().getMobManager().getMobTypes()
                .forEach(am -> selectedMobs.put(am, false));
        close = false;
        List<ItemEmote> entityConfigs = Data.ITEM_DATA.getValue();
        for (ItemEmote entityConfig : entityConfigs) {
            String entityName = entityConfig.getName();
            MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(entityName).orElse(null);
            if (mythicMob != null) {
                selectedMobs.put(mythicMob, true);
            }
        }

        selectedMobs.forEach((am, bo) ->
                this.items.add(createItemStackFromActiveMob(am))
        );
        drawMenu();
    }

    private ItemStack createItemStackFromActiveMob(MythicMob activeMob) {
        Material material = Material.valueOf(Settings.MENU_FILL_MATERIAL);
        AtomicReference<ItemStack> item = new AtomicReference<>(new ItemStack(material, 1));
        AtomicReference<List<String>> lore = new AtomicReference<>(new ArrayList<>());

        String internalName = activeMob.getInternalName();

        // Buscar el ItemConfig correspondiente en Data.ITEM_DATA
        Data.ITEM_DATA.getValue().stream()
                .filter(itemConfig -> itemConfig.getName().equals(internalName))
                .findFirst()
                .ifPresentOrElse(itemConfig -> {
                    item.set(itemConfig.asItemStack());
                    lore.set(itemConfig.getLore());
                    ItemStackUtils.addEnchantment(item.get(), Enchantment.DURABILITY, 10, true);
                }, () -> {
                    // Si no se encuentra la información en Data.ITEM_DATA, utilizar valores predeterminados
                    ItemStackUtils.setDisplayName(item.get(), internalName);
                    lore.get().addAll(List.of(
                            activeMob.getEggDisplay(),
                            Lang.MENU_ADMIN_LORE_FACTION.getValue() + activeMob.getFaction())
                    );
                });

        ItemStackUtils.addItemFlags(item.get(), ItemFlag.HIDE_ENCHANTS);
        ItemStackUtils.setDisplayName(item.get(), activeMob.getInternalName());

        lore.get().addAll(List.of("",
                Lang.MENU_ADMIN_LORE_LEFT_CLICK.getValue(),
                Lang.MENU_ADMIN_LORE_RIGHT_CLICK.getValue()));

        ItemStackUtils.setLore(item.get(), lore.get());
        return item.get();
    }

    @Override
    public void drawMenu() {
        inventory.clear();
        List<ItemStack> displayItems = previewMode ? getSelectedItems() : items;
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, displayItems.size());

        for (int i = 0; i < end - start; i++) {
            inventory.setItem(i, displayItems.get(start + i));
        }

        drawNavigationButtons();
    }

    @Override
    void drawNavigationButtons() {
        MenuManager.MENU_ADMIN.forEach((integer, itemConfig) ->
                inventory.setItem(integer, itemConfig.asItemStack()));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getItemMeta() == null) return;

        if (event.getSlot() >= 45) {
            handleAdminMenuAction(event.getSlot(), clickedItem);
        } else if (event.getClick().isRightClick()) {
            List<String> lore = clickedItem.getItemMeta().getLore();
            lore.removeAll(List.of(
                    "",
                    Lang.MENU_ADMIN_LORE_LEFT_CLICK.getValue(),
                    Lang.MENU_ADMIN_LORE_RIGHT_CLICK.getValue()));
            ItemStackUtils.setLore(clickedItem, lore);
            handleRightClick(clickedItem);
        } else if (event.getClick().isLeftClick()) {
            handleLeftClick(clickedItem, event.getSlot());
        }
    }

    private void handleRightClick(ItemStack clickedItem) {
        saveInfo();
        new MenuItemConfig(this.player, clickedItem);
    }

    private void handleLeftClick(ItemStack clickedItem, int slot) {
        String clickedDisplayName = clickedItem.getItemMeta().getDisplayName();
        selectedMobs.entrySet().stream()
                .filter(entry -> entry.getKey().getInternalName().equals(clickedDisplayName))
                .findFirst()
                .ifPresent(entry -> {
                    ItemStackUtils.toggleEnchantment(clickedItem, Enchantment.DURABILITY, 10);
                    items.set(slot, clickedItem);
                    entry.setValue(!entry.getValue());
                });
    }

    private void handleAdminMenuAction(int slot, ItemStack current) {
        ItemConfig item = MenuManager.MENU_ADMIN.get(slot);

        if (!item.equalItemStack(current)) return;

        if (Settings.MENU_ADMIN_CLOSE.equalItemStack(current)) {
            this.player.closeInventory();
        } else if (Settings.MENU_ADMIN_ARROW_LEFT.equalItemStack(current)) {
            previousPage();
        } else if (Settings.MENU_ADMIN_ARROW_RIGHT.equalItemStack(current)) {
            nextPage();
        } else if (Settings.MENU_ADMIN_PREVIEW.equalItemStack(current)) {
            togglePreviewMode();
        }
    }

    private void togglePreviewMode() {
        if (previewMode) {
            previewMode = false;
            page = savedPage; // Volver a la página guardada
        } else {
            savedPage = page; // Guardar la página actual
            previewMode = true;
            page = 0; // Volver a la primera página
        }

        drawMenu();
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        saveInfo();
    }

    private void saveInfo() {
        List<ItemEmote> results = new ArrayList<>();
        for (ItemStack selectedItem : getSelectedItems()) {
            List<String> preservedLore = new ArrayList<>(ItemStackUtils.getLore(selectedItem));
            preservedLore.removeIf(loreLine -> loreLine.equals(Lang.MENU_ADMIN_LORE_LEFT_CLICK.getValue()) ||
                    loreLine.equals(Lang.MENU_ADMIN_LORE_RIGHT_CLICK.getValue()) ||
                    loreLine.isBlank());

            ItemStackUtils.setLore(selectedItem, preservedLore);

            ItemEmote newConfig = new ItemEmote(
                    ItemStackUtils.getDisplayName(selectedItem),
                    preservedLore,
                    selectedItem.getType().name(),
                    ItemStackUtils.getCustomModelData(selectedItem),
                    0,
                    preservedLore.isEmpty() ? "" : preservedLore.get(0)
            );
            results.add(newConfig);
        }

        Data.ITEM_DATA.getValue().clear();
        Data.ITEM_DATA.setValue(results);
        Data.ITEM_DATA.forceUpdateValue();
        Data.reload();
    }

    private List<ItemStack> getSelectedItems() {
        List<ItemStack> selectedItems = new ArrayList<>();
        selectedMobs.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> createItemStackFromActiveMob(entry.getKey()))
                .forEach(selectedItems::add);
        return selectedItems;
    }
}
