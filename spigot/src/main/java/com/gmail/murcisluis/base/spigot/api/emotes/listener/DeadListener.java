package com.gmail.murcisluis.base.spigot.api.emotes.listener;

import com.gmail.murcisluis.base.common.api.utils.scheduler.S;
import com.gmail.murcisluis.base.spigot.api.emotes.Data;
import com.gmail.murcisluis.base.spigot.api.emotes.data.PlayerData;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.NoSuchElementException;

public class DeadListener implements Listener {

    public DeadListener() {
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        Player killer = deceased.getKiller();


        String mob = null;
        try {
            PlayerData killerData = killer != null ?
                    Data.PLAYER_DATA.getValue().stream()
                            .filter(playerData -> playerData.getPlayer().equals(killer))
                            .findFirst()
                            .orElse(null) :
                    null;

            PlayerData deceasedData = mob == null ?
                    Data.PLAYER_DATA.getValue().stream()
                            .filter(playerData -> playerData.getPlayer().equals(deceased))
                            .findFirst()
                            .orElse(null) :
                    null;

            mob = killerData != null ? killerData.getEmoteKill() : (deceasedData != null ? deceasedData.getEmoteDeath() : null);

        } catch (NoSuchElementException e) {
            // Manejar la excepción si es necesario
        }


        if (mob != null) {
            BukkitAPIHelper api = MythicBukkit.inst().getAPIHelper();
            try {
                Entity entity = api.spawnMythicMob(mob, deceased.getLocation());
                if (entity instanceof LivingEntity le) {
                    le.setCollidable(false);
                    le.setAI(false);
                    S.sync(()->{
                        if (!le.isDead())
                            le.remove();
                    },200);
                }

            } catch (InvalidMobTypeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}




