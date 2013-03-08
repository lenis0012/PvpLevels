package com.lenis0012.bukkit.pvp.data;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.lenis0012.bukkit.pvp.utils.StackUtil;

public class MySQL extends SQLite implements DataManager {
	private FileConfiguration config;
	
	private MySQL(String fileDir, String fileName) {
		super(fileDir, fileName);
	}
	
	public MySQL(FileConfiguration config) {
		super(config);
		this.config = config;
		
		this.open();
	}
	
	@Override
	public void open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			Bukkit.getLogger().severe("[PvpLevels] Failed to init MySQL driver:");
			StackUtil.dumpStack(e);
			return;
		}
		
		String host = config.getString("MySQL.host", "localhost");
		String port = String.valueOf(config.getInt("MySQL.port", 3306));
		String database = config.getString("MySQL.database", "bukkit");
		String user = config.getString("MySQL.username", "root");
		String pass = config.getString("MySQL.password", "");
		
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://"+host+':'+port+
					'/'+database+'?'+"user="+user+"&password="+pass);
			this.st = con.createStatement();
			
			st.setQueryTimeout(30);
		} catch(SQLException e) {
			Bukkit.getLogger().severe("[PvpLevels] Failed to init MySQL connection:");
			StackUtil.dumpStack(e);
		}
	}
}
