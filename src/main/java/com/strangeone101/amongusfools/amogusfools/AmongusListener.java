package com.strangeone101.amongusfools.amogusfools;

import com.projectkorra.projectkorra.BendingPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class AmongusListener implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(event.getPlayer());
        if (bendingPlayer.getBoundAbility() != null && bendingPlayer.getBoundAbility().getClass().equals(AmogusFools.class)) {
            new AmogusFools(event.getPlayer());
        }
    }
}
