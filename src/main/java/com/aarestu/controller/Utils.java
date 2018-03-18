package com.aarestu.controller;

public class Utils {

	static int attack(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;

	}

	static boolean randomBool(double percentChance) {
		return Math.random() * 100 < percentChance;
	}
}
