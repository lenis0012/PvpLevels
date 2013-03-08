package com.lenis0012.bukkit.pvp.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class Hook {
	String pluginName;
	Plugin plugin;
	
	public Hook(String plugin) {
		this.pluginName = plugin;
		this.plugin = Bukkit.getServer().getPluginManager().getPlugin(plugin);
	}
	
	public abstract void onEnable();
	
	public abstract void onDisable();
	
	public abstract <T> T invoke(Object... params);
	
	public void disable(Plugin plugin) {
		if(this.pluginName.equals(plugin.getName())) {
			this.onDisable();
		}
	}
	
	public void enable(Plugin plugin) {
		if(this.pluginName.equals(plugin.getName())) {
			this.onEnable();
		}
	}
	
	public boolean isEnabled() {
		return this.plugin != null && this.plugin.isEnabled();
	}
}
