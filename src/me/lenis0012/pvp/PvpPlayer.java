package me.lenis0012.pvp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PvpPlayer
{
	private String name = null;
	private int id = 0;
	private Main plugin;
	private DataHandler data;
	private int lvl = 0;
	private int kills = 0;
	
	public PvpPlayer(Player player)
	{
		plugin = (Main)Bukkit.getServer().getPluginManager().getPlugin("PvpLevels");
		data = plugin.getDB();
		name = player.getName();
		id = player.getEntityId();
		lvl = data.getValue(name, "level");
		kills = data.getValue(name, "kills");
	}
	
	//get the players level
	public int getLevel()
	{
		return lvl;
	}
	
	//get the players name
	public String getName()
	{
		return name;
	}
	
	//get the players Entity id
	public int getEntityId()
	{
		return id;
	}
	
	//get the players kills
	public int getKills()
	{
		return kills;
	}
	
	//change a players level
	public void setLevel(int lvl)
	{
		data.setValue(name, lvl, kills, 1);
	}
	
	//change a players amount of kills
	public void setKills(int kills)
	{
		data.setValue(name, lvl, kills, 1);
	}
}
