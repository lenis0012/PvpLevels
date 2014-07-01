package com.lenis0012.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public abstract class Converter {
	
	void onDatabaseLoad(Database database) {
		try {
			if(needsConversion(database)) {
				Table oldTable = getOldTable();
				Table newTable = getNewTable();
				
				//Rename/backup
				database.renameTable(oldTable.getName(), oldTable.getName() + "_backup");
				
				//Load antried
				PreparedStatement ps = database.getConnection().prepareStatement("SELECT * FROM " + oldTable.getName() + "_backup;");
				ResultSet entries = ps.executeQuery();
				
				//Convert
				database.registerTable(newTable);
				convertAll(database, entries);
				
				//Remove backup
				database.deleteTable(oldTable.getName() + "_backup");
				onComplete(); //DONE
			}
		} catch(SQLException e) {
			Bukkit.getLogger().log(Level.WARNING, "Failed to convert database", e);
		}
	}
	
	public void onComplete() {}

	/**
	 * Does this database need conversion from this converter?
	 * 
	 * @param database Selected database
	 * @return Needs conversion?
	 * @throws SQLException
	 */
	public abstract boolean needsConversion(Database database) throws SQLException;
	
	/**
	 * Old table format.
	 * 
	 * @return Table
	 */
	public abstract Table getOldTable();
	
	/**
	 * New table format.
	 * 
	 * @return Table
	 */
	public abstract Table getNewTable();
	
	/**
	 * Convert all loaded entries
	 * 
	 * @param database Selected database
	 * @param results Loaded entries
	 * @throws SQLException
	 */
	public abstract void convertAll(Database database, ResultSet results) throws SQLException;
}