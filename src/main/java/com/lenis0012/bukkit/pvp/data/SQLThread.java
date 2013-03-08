package com.lenis0012.bukkit.pvp.data;

import com.lenis0012.bukkit.pvp.utils.StackUtil;

public class SQLThread extends Thread {
	private boolean running = false;
	private DataManager sql;
	private long delay;
	
	public SQLThread(DataManager sql, long delay) {
		this.sql = sql;
		this.delay = delay;
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				this.sql.close();
				this.sql.open();
			} catch(Throwable t) {
				StackUtil.dumpStack(t);
			}
			
			try {
				Thread.sleep(this.delay * 1000);
			} catch (InterruptedException e) {
				//Thread interupted
			}
		}
	}
	
	public void start(long delay) {
		this.delay = delay;
		
		if(this.running)
			this.interrupt();
		
		this.start();
	}
	
	@Override
	public void start() {
		if(this.running)
			return;
		
		this.running = true;
		super.start();
	}
	
	@Override
	public void interrupt() {
		if(!this.running)
			return;
		
		this.running = false;
		super.interrupt();
	}
	
	public boolean isRunning() {
		return this.running;
	}
}
