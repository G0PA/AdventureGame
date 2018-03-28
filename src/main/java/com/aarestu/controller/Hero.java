package com.aarestu.controller;

import javax.servlet.http.Cookie;

public class Hero {
	String heroClass="";
	int hp=100;
	int maxHp = 100;
	int mana=50;
	int maxMana=50;
	int attackMin=13;
	int attackMax=16;
	int armor=4;
	int magicResist=4;
	int gold = 15;
	int critChance = 5;
	int enemyEncountersLeft=26;
	int hpRegen=2;
	int manaRegen=1;
	String zone="Green Woods";
	boolean isValid = true;

	public Hero() {

	}

	public Hero(int hp, int maxHp,int mana,int maxMana, int attackMin, int attackMax, int armor, int magicResist, int gold, int critChance) {
		this(hp, maxHp,mana,maxMana, attackMin, attackMax, armor, magicResist, gold, critChance, 0,0,0,"");
	}
	
	public Hero(int hp, int maxHp,int mana,int maxMana, int attackMin, int attackMax, int armor, int magicResist, int gold, int critChance,
			int enemyEncountersLeft, int hpRegen, int manaRegen,String zone) {
		this.hp = hp;
		this.mana=mana;
		this.maxMana=maxMana;
		this.attackMin = attackMin;
		this.attackMax = attackMax;
		this.armor = armor;
		this.magicResist = magicResist;
		this.gold = gold;
		this.critChance = critChance;
		this.maxHp = maxHp;
		this.enemyEncountersLeft = enemyEncountersLeft;
		this.hpRegen=hpRegen;
		this.manaRegen=manaRegen;
		this.zone=zone;
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
			hero.hpRegen=Integer.parseInt(heroArr[12]);
			hero.manaRegen=Integer.parseInt(heroArr[13]);
			hero.zone=heroArr[14];
			if (hero.hp > hero.maxHp) {
				return null;
			}
			
			return hero;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Cookie createCookie() {
		return new Cookie("hero", String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s",heroClass, hp, maxHp,mana,maxMana, attackMin, attackMax, armor, magicResist, gold, critChance,enemyEncountersLeft,hpRegen,manaRegen,zone));
	}
	
	public String createDisplayText() {
		if(heroClass.equals("Mage")) {
		return  "class = " +heroClass+
				",   health = " + hp +"/" + maxHp +" (+"+hpRegen+" regen)"+ 
				",   mana = "+mana+"/"+ maxMana+" (+"+manaRegen+" regen)"+ 
				",   attack = " + (attackMin+(magicResist/2)) + "-" + (attackMax+(magicResist/2))+
				",   armor = " + armor +
				",   magic resist = " + magicResist + 
				",   gold = " + gold +
				",   critical chance = " + critChance + "%" +
				",   Enemy encounters left until Boss: " + enemyEncountersLeft+
				",   Current Zone: "+zone;
		}else if(heroClass.equals("Warrior")) {
			return  "class = " +heroClass+
					",   health = " + hp +"/" + maxHp +" (+"+hpRegen+" regen)"+  
					",   mana = "+mana+"/"+ maxMana+" (+"+manaRegen+" regen)"+ 
					",   attack = " + (attackMin+(armor/2)) + "-" + (attackMax+(armor/2))+
					",   armor = " + armor +
					",   magic resist = " + magicResist + 
					",   gold = " + gold +
					",   critical chance = " + critChance + "%" +
					",   Enemy encounters left until Boss: " + enemyEncountersLeft+
					",   Current Zone: "+zone;
		}else if(heroClass.equals("Ranger")) {
		return  "class = " +heroClass+
				",   health = " + hp +"/" + maxHp +" (+"+hpRegen+" regen)"+  
				",   mana = "+mana+"/"+maxMana+" (+"+manaRegen+" regen)"+ 
				",   attack = " + attackMin+ "-" + attackMax+
				",   armor = " + armor +
				",   magic resist = " + magicResist + 
				",   gold = " + gold +
				",   critical chance = " + critChance + "%" +
				",   Enemy encounters left until Boss: " + enemyEncountersLeft+
				",   Current Zone: "+zone;
		}else {
			return  "class = " +heroClass+
					",   health = " + hp +"/" + maxHp +" (+"+hpRegen+" regen)"+  
					",   mana = "+mana+"/"+maxMana+" (+"+manaRegen+" regen)"+ 
					",   attack = " + (attackMin+((maxHp-hp)/25))+ "-" + (attackMax+((maxHp-hp)/25))+
					",   armor = " + armor +
					",   magic resist = " + magicResist + 
					",   gold = " + gold +
					",   critical chance = " + critChance + "%" +
					",   Enemy encounters left until Boss: " + enemyEncountersLeft+
					",   Current Zone: "+zone;
		}
	}
}
