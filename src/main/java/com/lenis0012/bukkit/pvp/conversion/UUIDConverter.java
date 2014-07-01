package com.lenis0012.bukkit.pvp.conversion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import com.lenis0012.bukkit.pvp.PvpLevels;
import com.lenis0012.bukkit.pvp.Tables;
import com.lenis0012.database.Converter;
import com.lenis0012.database.Database;
import com.lenis0012.database.Table;

public class UUIDConverter extends Converter {

	@Override
	public boolean needsConversion(Database database) throws SQLException {
		return database.columnExists("accounts", "username");
	}

	@Override
	public Table getOldTable() {
		return new Table("accounts",
				"username VARCHAR(50) NOT NULL UNIQUE," +
				"level INT," +
				"kills INT," +
				"deaths INT," +
				"lastlogin INT");
	}

	@Override
	public Table getNewTable() {
		return Tables.ACCOUNTS;
	}

	@Override
	public void convertAll(Database database, ResultSet results) throws SQLException {
		PvpLevels.instance.getLogger().log(Level.INFO, "Starting UUID conversion, this can take some time.");
		long startTime = System.currentTimeMillis();
		
		//STARTING
		List<String> usernames = new ArrayList<String>();
		List<Object[]> entries = new ArrayList<Object[]>();
		while(results.next()) {
			Object[] info = new Object[] {
					results.getString("username"),
					results.getInt("level"),
					results.getInt("kills"),
					results.getInt("deaths"),
					results.getInt("lastlogin")
			};
			
			usernames.add(results.getString("username"));
			entries.add(info);
		}
		
		database.close(); //Safe disconnect
		
		//Fetch UUIDS
		PvpLevels.instance.getLogger().log(Level.INFO, "Obtaining UUID from " + entries.size() + " entries.");
		UUIDFetcher fetcher = new UUIDFetcher(usernames);
		Map<String, UUID> uuids;
		
		try {
			uuids = fetcher.call();
		} catch(Exception e) {
			throw new SQLException("Failed to convert uuids!");
		}
		
		database.connect(); //Safe reconnect
		
		//Reset table
		database.deleteTable(getOldTable().getName());
		database.registerTable(getNewTable());
		
		//Insert new data
		for(Object[] info : entries) {
			String username = (String) info[0];
			int level = (Integer) info[1];
			int kills = (Integer) info[2];
			int deaths = (Integer) info[3];
			int lastlogin = (Integer) info[4];
			UUID uuid = uuids.get(username);
			database.set(Tables.ACCOUNTS, uuid.toString(), level, kills, deaths, lastlogin);
		}
		//DONE
		
		long duration = System.currentTimeMillis() - startTime;
		PvpLevels.instance.getLogger().log(Level.INFO, "Conversion completed, took " + (duration / 1000) + "s");
	}

}
