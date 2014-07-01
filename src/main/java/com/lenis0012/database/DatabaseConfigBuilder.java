package com.lenis0012.database;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;

public class DatabaseConfigBuilder {
	private String driver;
	private String url;
	private String database;
	private String user;
	private String password;
	private String file;
	
	/**
	 * Default constructor, no settings.
	 */
	public DatabaseConfigBuilder() {}
	
	/**
	 * Construct a database based on a config section with a driver and custom url.
	 * This is quite advanced.
	 * 
	 * @param section Configuration section.
	 */
	public DatabaseConfigBuilder(ConfigurationSection section) {
		driver(section.getString("driver")).url(section.getString("url")).database(section.getString("database")).user(section.getString("user")).password(section.getString("password"));
	}
	
	/**
	 * Construct a database based on a config section with sqlite backup, drivers auto-generated.
	 * 
	 * @param section Configuration section.
	 * @param backup SQLIte file backup.
	 */
	public DatabaseConfigBuilder(ConfigurationSection section, File backup) {
		if(section.getBoolean("enabled")) {
			String url = String.format("%s:%d", section.getString("host"), section.getInt("port"));
			driver("com.mysql.jdbc.Driver").url(url).database(section.getString("database")).user(section.getString("user")).password(section.getString("password"));
		} else {
			driver("org.sqlite.JDBC").sqlite(backup);
		}
	}
	
	public DatabaseConfigBuilder driver(String driver) {
		this.driver = driver;
		return this;
	}
	
	public DatabaseConfigBuilder url(String url) {
		this.url = url;
		return this;
	}
	
	public DatabaseConfigBuilder database(String database) {
		this.database = database;
		return this;
	}
	
	public DatabaseConfigBuilder user(String user) {
		this.user = user;
		return this;
	}
	
	public DatabaseConfigBuilder password(String password) {
		this.password = password;
		return this;
	}
	
	public DatabaseConfigBuilder sqlite(File file) {
		this.file = file.getPath();
		return this;
	}

	public String getFile() {
		return file;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getDatabase() {
		return database;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
