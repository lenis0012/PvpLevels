package com.lenis0012.bukkit.pvp.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class VaultHook extends Hook {
	public static final int WITHRAW = 1;
	public static final int DEPOSIT = 2;
	public static final int CHECK = 3;
	private Economy economy;
	
	public VaultHook(String plugin) {
		super(plugin);
	}

	@Override
	public void onEnable() {
		if(this.setupEconomy())
			Bukkit.getLogger().info("[PvpLevels] Hooked with "+this.economy.getName()+" using Vault.");
	}

	@Override
	public void onDisable() {
		this.economy = null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T invoke(Object... params) {
		String user = (String) params[0];
		double amount = (Double) params[1];
		int type = (Integer) params[2];
		Object value = Void.TYPE;
		
		switch(type) {
			case WITHRAW:
				if(this.economy != null)
					this.economy.withdrawPlayer(user, amount);
			case DEPOSIT:
				if(this.economy != null)
					this.economy.depositPlayer(user, amount);
			case CHECK:
				if(this.economy != null)
					value = this.economy.getBalance(user);
				else
					value = amount;
		}
		
		return (T) value;
	}
	

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            this.economy = economyProvider.getProvider();
        }

        return (this.economy != null);
    }
}