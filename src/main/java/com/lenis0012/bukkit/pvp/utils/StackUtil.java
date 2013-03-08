package com.lenis0012.bukkit.pvp.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.lenis0012.bukkit.pvp.PvpLevels;

public class StackUtil {
	public static final String NAME;
	public static final String VERSION;
	
	public static final PvpLevels plugin;
	public static final BukkitScheduler scheduler;
	public static final Logger logger;
	
	static {
		plugin = PvpLevels.instance;
		scheduler = plugin.getServer().getScheduler();
		logger = plugin.getLogger();
		
		NAME = plugin.getDescription().getName();
		VERSION = plugin.getDescription().getVersion();
	}
	
	public static void dumpStack(Throwable t) {
		ChatColor a = ChatColor.GREEN;
		ChatColor b = ChatColor.GRAY;
		String prefix = "[" + NAME + "] ";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String[] sts = sw.toString().replace("\r", "").split("\n");
		StackTraceElement[] elements = t.getStackTrace();
		String[] out = new String[sts.length + (elements.length * 4) + 9];
		out[0] = (prefix + ChatColor.RED + "Internal error!");
		out[1] = (prefix + "If this bug has not been reported please open a ticket at BukkitDev");
		out[2] = (prefix + "Include the following into your bug report:");
		out[3] = (prefix + "          ====== "+a+"STACK TRACE"+b+" ======");
		int j = 0;
		for(int i = 4; i - 4 < sts.length; i++)
			out[i] = (prefix + sts[(j = i - 4)]);
		j += 4;
		out[j++] = (prefix + "          ====== "+a+"DUMP"+b+" ======");
		out[j++] = (prefix + "plugin name: " + NAME);
		out[j++] = (prefix + "plugin version: " + VERSION);
		out[j++] = (prefix + "bukkit version: " + Bukkit.getBukkitVersion());
		out[j++] = (prefix + "description: " + t.getMessage());
		int k = 1;
		for(StackTraceElement e : elements) {
			out[j++] = (prefix + "          ====== "+a+"Element #"+k+b+" ======");
			out[j++] = (prefix + "class: " + e.getClassName());
			out[j++] = (prefix + "at line: " + e.getLineNumber());
			out[j++] = (prefix + "method: " + e.getMethodName());
			k++;
		}
		Runnable task = new SyncMessagePair(null, out, null);
		scheduler.scheduleSyncDelayedTask(plugin, task);
	}
	
	public static class SyncMessagePair implements Runnable {
		private CommandSender receiver;
		private String[] messages;
		private Level level;
		
		public SyncMessagePair(Player player, String[] messages, Level level) {
			this.receiver = player != null ? player: Bukkit.getServer().getConsoleSender();
			this.messages = messages;
			this.level = level;
		}
		
		@Override
		public void run() {
			if(this.receiver instanceof Player && !((Player) this.receiver).isOnline())
				return;
			
			boolean toPlayer = this.receiver instanceof Player;
			
			for(String msg : this.messages) {
				if(msg != null) {
					if(!toPlayer && level != null)
						msg = "[" + level.toString() + "] " + msg;
					this.receiver.sendMessage(msg);
				}
			}
		}
	}
}
