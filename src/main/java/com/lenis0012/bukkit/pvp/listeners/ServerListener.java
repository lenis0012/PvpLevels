package com.lenis0012.bukkit.pvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.lenis0012.bukkit.pvp.PvpLevels;
import com.lenis0012.bukkit.pvp.hooks.Hook;

public class ServerListener implements Listener {
	private PvpLevels plugin;
	
	public ServerListener(PvpLevels plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler  (priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		Plugin enabled = event.getPlugin();
		for(Hook hook : plugin.getHooks()) {
			hook.enable(enabled);
		}
	}
	
	@EventHandler  (priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event) {
		Plugin disabled  = event.getPlugin();
		for(Hook hook : plugin.getHooks()) {
			hook.disable(disabled);
		}
	}
}
