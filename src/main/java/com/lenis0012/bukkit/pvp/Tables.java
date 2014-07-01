package com.lenis0012.bukkit.pvp;

import com.lenis0012.database.Table;

public class Tables {
	/**
	 * Accounts table
	 */
	public static final Table ACCOUNTS = new Table("accounts",
			"uuid VARCHAR(50) NOT NULL UNIQUE," +
			"level INT," +
			"kills INT," +
			"deaths INT," +
			"lastlogin INT");
}
