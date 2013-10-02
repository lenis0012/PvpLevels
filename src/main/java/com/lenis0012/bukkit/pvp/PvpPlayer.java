package com.lenis0012.bukkit.pvp;

import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lenis0012.bukkit.pvp.data.DataManager;
import com.lenis0012.bukkit.pvp.hooks.VaultHook;
import com.lenis0012.bukkit.pvp.utils.MathUtil;

public class PvpPlayer {
	private String name;
	private DataManager sql;
	
	public PvpPlayer(String playerName) {
		this.name = playerName;
		this.sql = PvpLevels.instance.getSqlControler();
	}
	
	public boolean isCreated() {
		return sql.contains("username", name);
	}
	
	public void create() {
		int lvl = 0;
		int kills = 0;
		int deaths = 0;
		int lastLogin = days(Calendar.getInstance());
		
		sql.set(name, lvl, kills, deaths, lastLogin);
	}
	
	public void set(String index, int value) {
		sql.update("username", index, name, value);
	}
	
	public int get(String index) {
		return (Integer) sql.get("username", index, name);
	}
	
	public void update() {
		this.set("lastlogin", days(Calendar.getInstance()));
	}
	
	@SuppressWarnings("unchecked")
	public void reward(Player player) {
		PvpLevels plugin = PvpLevels.instance;
		String lvl = String.valueOf(this.get("level"));
		if((Boolean) plugin.getReward(lvl, "item.use")) {
			String itemName = (String) plugin.getReward(lvl, "item.name");
			int amount = (Integer) plugin.getReward(lvl, "item.amount");
			Material type = Material.getMaterial(itemName);
			ItemStack it = new ItemStack(type, amount);
			player.getInventory().addItem(it);
			player.sendMessage(ChatColor.GREEN+"You gained "+amount+" of "+
					type.toString().toLowerCase());
		}
			
		if((Boolean) plugin.getReward(lvl, "money.use")) {
			double amount = (Double) plugin.getReward(lvl, "money.amount");
			plugin.getHook("Vault").invoke(player.getName(), amount, VaultHook.DEPOSIT);
			player.sendMessage(ChatColor.GREEN+"You gained "+amount+"$");
		}
			
		if((Boolean) plugin.getReward(lvl, "commands.use")) {
			List<String> cmds = (List<String>) plugin.getReward(lvl, "commands.commands");
			for(String cmd : cmds) {
				cmd = cmd.replace("{User}", player.getName());
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				Bukkit.getServer().dispatchCommand(console, cmd);
			}
		}
	}
	
	private int days(Calendar cal) {
		int years = (cal.get(Calendar.YEAR) - 2000) * 365;
		int days = cal.get(Calendar.DAY_OF_YEAR);
		int longYear = MathUtil.floor(cal.get(Calendar.YEAR) / 4);
		
		return years + days + longYear;
	}
}
