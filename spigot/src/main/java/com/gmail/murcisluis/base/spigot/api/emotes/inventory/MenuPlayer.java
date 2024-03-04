package com.gmail.murcisluis.base.spigot.api.emotes.inventory;

import com.gmail.murcisluis.base.spigot.api.Lang;
import com.gmail.murcisluis.base.spigot.api.Settings;
import com.gmail.murcisluis.base.spigot.api.emotes.Data;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemConfig;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemEmote;
import com.gmail.murcisluis.base.spigot.api.emotes.data.PlayerData;
import com.gmail.murcisluis.base.spigot.api.emotes.manager.MenuManager;
import com.gmail.murcisluis.base.spigot.api.utils.item.ItemStackUtils;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class MenuPlayer extends PaginatedMenu{

    private PlayerData playerInfo;
    @Getter
    private final boolean close=true;
    public MenuPlayer(Player player) {
        super(player, Lang.MENU_PLAYER_TITLE.getValue());
        playerInfo=Data.PLAYER_DATA.getValue().stream().filter(playerData -> playerData.getPlayer().equals(player)).findAny().orElse(null);
        if (playerInfo == null) {
            playerInfo=new PlayerData(player,null,null);
        }
        items.addAll(Data.ITEM_DATA.getValue().stream().map(item->{

            ItemStack is=item.asItemStack();
            ItemMeta im=is.getItemMeta();
            if (im != null) {
                List<String> lore=im.getLore();
                lore.addAll(List.of(
                        Lang.MENU_PLAYER_LORE_LEFT_CLICK.getValue(),
                        Lang.MENU_PLAYER_LORE_RIGHT_CLICK.getValue())
                );

                im.setLore(lore);
                is.setItemMeta(im);
                if (player.hasPermission("lujoemotes.emote."+item.getMobName())) {
                    ItemStackUtils.addEnchantment(is, Enchantment.DURABILITY,10 ,true);
                }
            }
            return is;
            }).toList());
        drawMenu();
    }

    @Override
    void drawNavigationButtons() {
        MenuManager.MENU_PLAYER.forEach((integer,itemConfig)->inventory.setItem(integer,itemConfig.asItemStack()));
        if (playerInfo.getEmoteKill() != null) {
            ItemStack killItem = Data.ITEM_DATA.getValue().stream()
                    .filter(item -> item.getMobName().equals(playerInfo.getEmoteKill()))
                    .findFirst().map(ItemConfig::asItemStack)
                    .orElse(null);

            if (killItem != null) {
                killItem=ItemStackUtils.addLore(killItem,Lang.SELECT_EMOTE_LORE.getValue());
                inventory.setItem(Settings.MENU_PLAYER_SELECT_KILL.getSlot(), killItem);
            }
        }

        if (playerInfo.getEmoteDeath() != null) {
            ItemStack deathItem = Data.ITEM_DATA.getValue().stream()
                    .filter(item -> item.getMobName().equals(playerInfo.getEmoteDeath()))
                    .findFirst().map(ItemConfig::asItemStack)
                    .orElse(null);

            if (deathItem != null) {
                deathItem=ItemStackUtils.addLore(deathItem,Lang.SELECT_EMOTE_LORE.getValue());
                inventory.setItem(Settings.MENU_PLAYER_SELECT_DEATH.getSlot(), deathItem);
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getItemMeta() == null) return;
        if (event.getSlot() > 44) {
            handleMenuAction(event.getSlot(), clickedItem);
        } else {
            ItemEmote iemote=Data.ITEM_DATA.getValue().stream().filter(item->item.isSimilarItemStack(event.getCurrentItem())).findFirst().orElse(null);
            String emote=iemote.getMobName();
            if (event.getClick().isRightClick() && player.hasPermission("lujoemotes.emote."+emote)) {
                playerInfo.setEmoteDeath(emote);
                Lang.SELECT_EMOTE_MESSAGE.send(player, Placeholder.parsed("emote",emote));
                drawNavigationButtons();
            } else if (event.getClick().isLeftClick() && player.hasPermission("lujoemotes.emote."+emote)) {
                playerInfo.setEmoteKill(emote);
                Lang.SELECT_EMOTE_MESSAGE.send(player, Placeholder.parsed("emote",emote));
                drawNavigationButtons();
            }
        }
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        List<PlayerData> playerDataList = Data.PLAYER_DATA.getValue();
        PlayerData existingPlayerInfo = getPlayerDataByPlayer(player);

        if (existingPlayerInfo != null) {
            // El jugador ya existe en PLAYER_DATA, actualiza la información
            existingPlayerInfo.setEmoteDeath(playerInfo.getEmoteDeath());
            existingPlayerInfo.setEmoteKill(playerInfo.getEmoteKill());
        } else {
            // El jugador no existe en PLAYER_DATA, añádelo
            playerDataList.add(playerInfo);
        }

        Data.PLAYER_DATA.forceUpdateValue();
    }

    private PlayerData getPlayerDataByPlayer(Player player) {
        // Busca el PlayerData correspondiente al jugador en PLAYER_DATA
        return Data.PLAYER_DATA.getValue().stream()
                .filter(playerData -> playerData.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
    }

    private void handleMenuAction(int slot,ItemStack current) {



        if (Settings.MENU_PLAYER_SELECT_KILL.getSlot() == slot) {
            playerInfo.setEmoteKill(null);
        }else if (Settings.MENU_PLAYER_SELECT_DEATH.getSlot() == slot) {
            playerInfo.setEmoteDeath(null);
        }

        drawNavigationButtons();
        ItemConfig item=MenuManager.MENU_PLAYER.get(slot);

        if (current == null ||!item.equalItemStack(current)) return;

        if (Settings.MENU_PLAYER_CLOSE.equalItemStack(current)) {
            this.player.closeInventory();
        } else if (Settings.MENU_PLAYER_ARROW_LEFT.equalItemStack(current)) {
            previousPage();
        } else if (Settings.MENU_PLAYER_ARROW_RIGHT.equalItemStack(current)) {
            nextPage();
        }
    }
}
