package com.lenis0012.bukkit.pvp.utils;

public class MathUtil {
	
	/**
	 * Floor a double
	 * 
	 * @param value Value
	 * @return Floored double
	 */
	public static int floor(double value) {
		int i = (int) value;
		return value < i ? i - 1 : i;
	}
	
	/**
	 * Round a double
	 * 
	 * @param value Value
	 * @param decimals Decimals
	 * @return Rounded double
	 */
	public double round(double value, int decimals) {
		double p = Math.pow(10, decimals);
		return Math.round(value * p) / p;
	}
}
