package com.aarestu.controller;

import javax.servlet.http.Cookie;

public class Hero {
	String heroClass="";
	int hp=100;
	int maxHp = 100;
	int mana=50;
	int maxMana=50;
	int attackMin=12;
	int attackMax=16;
	int armor=4;
	int magicResist=4;
	int gold = 10;
	int critChance = 5;
	int enemyEncountersLeft=25;
	boolean isValid = true;

	public Hero() {

	}

	public Hero(int hp, int maxHp,int mana,int maxMana, int attackMin, int attackMax, int armor, int magicResist, int gold, int critChance) {
		this(hp, maxHp,mana,maxMana, attackMin, attackMax, armor, magicResist, gold, critChance, 0);
	}
	
	public Hero(int hp, int maxHp,int mana,int maxMana, int attackMin, int attackMax, int armor, int magicResist, int gold, int critChance,
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
			hero.heroClass=heroArr[0];
			hero.hp=Integer.parseInt(heroArr[1]);
			hero.maxHp=Integer.parseInt(heroArr[2]);
			hero.mana=Integer.parseInt(heroArr[3]);
			hero.maxMana=Integer.parseInt(heroArr[4]);
			hero.attackMin=Integer.parseInt(heroArr[5]);
			hero.attackMax=Integer.parseInt(heroArr[6]);
			hero.armor=Integer.parseInt(heroArr[7]);
			hero.magicResist=Integer.parseInt(heroArr[8]);
			hero.gold=Integer.parseInt(heroArr[9]);
			hero.critChance=Integer.parseInt(heroArr[10]);
			hero.enemyEncountersLeft=Integer.parseInt(heroArr[11]);
			if (hero.hp > hero.maxHp) {
				return null;
			}
			
			return hero;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Cookie createCookie() {
		return new Cookie("hero", String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",heroClass, hp, maxHp,mana,maxMana, attackMin, attackMax, armor, magicResist, gold, critChance,enemyEncountersLeft));
	}
	
	public String createDisplayText() {
		if(heroClass.equals("Mage")) {
		return  "class = " +heroClass+
				",   health = " + hp +"/" + maxHp + 
				",   mana = "+mana+"/"+ maxMana+
				",   attack = " + (attackMin+(magicResist/2)) + "-" + (attackMax+(magicResist/2))+
				",   armor = " + armor +
				",   magic resist = " + magicResist + 
				",   gold = " + gold +
				",   critical chance = " + critChance + "%" +
				",   Enemy encounters left until Boss: " + enemyEncountersLeft;
		}
		return  "class = " +heroClass+
				",   health = " + hp +"/" + maxHp + 
				",   mana = "+mana+"/"+"maxmana"+
				",   attack = " + attackMin+ "-" + attackMax+
				",   armor = " + armor +
				",   magic resist = " + magicResist + 
				",   gold = " + gold +
				",   critical chance = " + critChance + "%" +
				",   Enemy encounters left until Boss: " + enemyEncountersLeft;
	}
}
