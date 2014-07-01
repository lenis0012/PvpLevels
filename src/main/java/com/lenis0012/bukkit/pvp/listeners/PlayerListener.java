package com.lenis0012.bukkit.pvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lenis0012.bukkit.pvp.PvpLevels;
import com.lenis0012.bukkit.pvp.PvpPlayer;

public class PlayerListener implements Listener {
	private PvpLevels plugin;
	
	public PlayerListener(PvpLevels plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String format = event.getFormat();
		PvpPlayer pp = plugin.getPlayer(player);
		
		String lvl = String.valueOf(pp.getLevel());
		String toReplace = plugin.getConfig().getString("replace-string", "[LEVEL]");
		if(format.contains(toReplace))
			format = format.replace(toReplace, lvl);
		else
			format = "["+ChatColor.GREEN+"Lvl "+lvl+ChatColor.WHITE+"] "+format;
		if(format.contains("[KDR]"))
			format = format.replace("[KDR]", String.valueOf(PvpLevels.getKdr(pp)));
		
		event.setFormat(format);
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		plugin.loadPlayer(player);
		
		if(PvpLevels.UNSAFE_CONFIG && player.isOp()) {
			Bukkit.getScheduler().runTask(plugin, new Runnable() {
	
				@Override
				public void run() {
					player.sendMessage("\247e\247lOne of your config files was changed!");
					player.sendMessage("\247e\247lPlease read you \247a\247lREADME.txt \247e\247lin the pvplevels folder.");
					player.sendMessage("\247cAfter editing your configs, remove README.txt");
				}
			});
		}
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		plugin.unloadPlayer(player);
	}
}
