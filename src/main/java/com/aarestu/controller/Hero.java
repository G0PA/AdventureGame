package com.aarestu.controller;

import javax.servlet.http.Cookie;

public class Hero {
	int hp=110;
	int attackMin=13;
	int attackMax=18;
	int armor=5;
	int magicResist=5;
	int gold = 5;
	int critChance = 5;
	int maxHp = 110;
	int enemyEncountersLeft=25;
	boolean isValid = true;

	public Hero() {

	}

	public Hero(int hp, int maxHp, int attackMin, int attackMax, int armor, int magicResist, int gold, int critChance) {
		this(hp, maxHp, attackMin, attackMax, armor, magicResist, gold, critChance, 0);
	}
	
	public Hero(int hp, int maxHp, int attackMin, int attackMax, int armor, int magicResist, int gold, int critChance,
			int enemyEncountersLeft) {
		this.hp = hp;
		this.attackMin = attackMin;
		this.attackMax = attackMax;
		this.armor = armor;
		this.magicResist = magicResist;
		this.gold = gold;
		this.critChance = critChance;
		this.maxHp = maxHp;
		this.enemyEncountersLeft = enemyEncountersLeft;
	}

	public static Hero fromCookie(String cookie) {
		Hero hero = new Hero();
		try {
			String[] heroArr=cookie.split(",");
			hero.hp=Integer.parseInt(heroArr[0]);
			hero.maxHp=Integer.parseInt(heroArr[1]);
			hero.attackMin=Integer.parseInt(heroArr[2]);
			hero.attackMax=Integer.parseInt(heroArr[3]);
			hero.armor=Integer.parseInt(heroArr[4]);
			hero.magicResist=Integer.parseInt(heroArr[5]);
			hero.gold=Integer.parseInt(heroArr[6]);
			hero.critChance=Integer.parseInt(heroArr[7]);
			hero.enemyEncountersLeft=Integer.parseInt(heroArr[8]);
			if (hero.hp > hero.maxHp) {
				return null;
			}
			
			return hero;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Cookie createCookie() {
		return new Cookie("hero", String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d", hp, maxHp, attackMin, attackMax, armor, magicResist, gold, critChance,enemyEncountersLeft));
	}
	
	public String createDisplayText() {
		return "health = " + hp +"/" + maxHp + 
				",   attack = " + attackMin + "-" + attackMax+
				",   armor = " + armor +
				",   magic resist = " + magicResist + 
				",   gold = " + gold +
				",   critical chance = " + critChance + "%" +
				",   Enemy encounters left until Boss: " + enemyEncountersLeft;
	}
}
