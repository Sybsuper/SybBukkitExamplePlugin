package com.sybsuper.SybBukkitExamplePlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class CustomListener implements Listener {// Create a separate listener class for better code management. Please do this. (I never do)
	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent e) { // Basically allows player to jump in air when they are allowed to fly. Just a small funny example.
		e.getPlayer().setFlying(false); // Turn off flight for the player.
		e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().normalize()); // Boost their velocity in the direction they are looking at. (This can be done way better, but it's just an example.)
	}
}
