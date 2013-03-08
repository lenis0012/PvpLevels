package com.lenis0012.bukkit.pvp.data;

public interface DataManager {
	
	/**
	 * Change the database table
	 * 
	 * @param table Table
	 */
	public void setTable(Table table);
	
	/**
	 * Insert data to the database
	 * 
	 * @param values Values
	 */
	public void set(Object... values);
	
	/**
	 * Get data from the database
	 * 
	 * @param index Index
	 * @param value Value
	 */
	public Object get(String index, String toGet, Object value);
	
	/**
	 * Check if a volumn exists
	 * 
	 * @param index Column index
	 * @param value Value
	 * @return Column exists?
	 */
	public boolean contains(String index, Object value);
	
	/**
	 * Update a value in the database
	 * 
	 * @param index Column index
	 * @param toUpdate Value to udate
	 * @param indexValue Index value
	 * @param updateValue Update value
	 */
	public void update(String index, String toUpdate, Object indexValue, Object updateValue);
	
	/**
	 * Remove a value in the database
	 * 
	 * @param index Column index
	 * @param value Column value
	 */
	public void remove(String index, Object value);
	
	/**
	 * Check fi the database connection is open
	 * 
	 * @return Connection open?
	 */
	public boolean isOpen();
	
	/**
	 * Open the database connection
	 */
	public void open();
	
	/**
	 * Close the database connection
	 */
	public void close();
}
