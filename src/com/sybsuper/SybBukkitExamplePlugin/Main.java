package com.sybsuper.SybBukkitExamplePlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
	public static Main plugin;
	public static FileConfiguration config;

	@Override
	// Method that is run when the plugin is first loaded by the server (before it is enabled), probably don't want to use this.
	public void onLoad() {
		// why would you use this? idk
	}

	@Override
	// Method that is run when the plugin enables. You can register listeners and load configs here.
	public void onEnable() {
		// Set this variable to our plugin, so we can reference our plugin using Main.plugin everywhere in our code.
		plugin = this;
		// Set up the config variable.
		config = getConfig();
		// Load default values from config.yml
		config.options().copyDefaults(true);
		// Save the config into the folder plugins/PLUGINNAME/config.yml so admins can edit it.
		saveConfig();
		// Load spam message and replace '&' color notation with Bukkit color notation using ChatColor.translateAlternateColorCodes method.
		String coloredSpamMessage = ChatColor.translateAlternateColorCodes('&', config.getString("spamMessage"));
		// Examples of loading stuff from config:
		String stringvalue = config.getString("stringValue", "this value is used if none can be found, which is really weird if this option is defined in your default config.");
		// 2 would be the default, but once again, no need to use it if it is defined in the default config, you could also do it like the second line below this comment.
		int intValue = config.getInt("intValue", 2);
		int intValueWithoutDefaultValue = config.getInt("intValue");
		// Load a double value.
		double doubleValue = config.getDouble("doubleValue");
		// Load a string list.
		List<String> numbers = config.getStringList("listMethodOne");
		// Exactly the same list.
		List<String> numbers2 = config.getStringList("listMethodTwo");
		// You should probably check if the list is a list of ConfigurationSections, but I don't care. (I never use this anyway)
		List<ConfigurationSection> list3 = (List<ConfigurationSection>) config.getList("complexList", new ArrayList<>());
		// Example of a value with a longer path:
		String farValue = config.getString("path1.path2.path3.farValue");
		// Examples to store a value to the config: (you don't care about variable types here)
		config.set("doubleValue", doubleValue);
		config.set("path1.path2.path3.farValue", farValue);

		// Bad but simple way of registering a listener. (I do this all the time anyway)
		Bukkit.getPluginManager().registerEvents(this, this);
		// Good way to register a listener. (Neater way to manage code)
		Bukkit.getPluginManager().registerEvents(new CustomListener(), this);
		BukkitRunnable annoyingLoop = new BukkitRunnable() {
			@Override
			public void run() {
				// This will be run every 5 seconds.
				// Loop over all the online players.
				for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					// If the player's name ends with "super"
					if (onlinePlayer.getName().endsWith("super")) {
						// Send them a message (don't do this every 5 seconds, it's obviously super annoying)
						onlinePlayer.sendMessage(coloredSpamMessage);
					}
				}
			}
		};
		// Schedule the function to be run every 5 seconds. Syntax: runTaskTimer(plugin, delay, period), plugin should reference to your plugin's Main class, delay is how many ticks (there should be 20 in a second if there's no lag) before the run method is run for the first time, period is how many ticks there should be between every time the run method is run. So 20 * 5 means the run method is run every 5 seconds.
		annoyingLoop.runTaskTimer(Main.plugin, 20 * 5, 20 * 5);
		// Cancel this super annoying loop from running. This can also be called from inside the run method, even using this.cancel(); for easier access.
		annoyingLoop.cancel();
	}

	// default event handler options, lower priority means it is run earlier, when ignoreCanceled is set to true it means canceled events won't call the method.
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	// Method name doesn't matter, you probably should call the event parameter "event" instead of "e", but I always use "e" anyway.
	public void onPlayerJoin(PlayerJoinEvent e) {
		// Store the player to do some cool stuff with it.
		Player p = e.getPlayer();
		// Example result, player Sybsuper receives the message in color gold "Hello there Sybsuper" in his chat.
		p.sendMessage(ChatColor.GOLD + "Hello there " + p.getName());
		// Play the ORB_PICKUP sound at the player's location.
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
		// Couple examples of getting data from the player:
		double health = p.getHealth();
		Location location = p.getLocation();
		boolean isSneaking = p.isSneaking();
		boolean isSprinting = p.isSprinting();
		boolean canFly = p.getAllowFlight();
		boolean isFlying = p.isFlying();
	}
}
