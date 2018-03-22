package com.aarestu.controller;

public class Enemy {
	String name;
	int attackType;
	int health;
	int damageMin;
	int damageMax;
	int armor;
	int dropsGold;
	String resourcePath;
	int critChance;
	public Enemy() {

	}

	public Enemy(String resourcePath, String name, int attackType, int health, int damageMin, int damageMax, int armor,
			int dropsGold, int critChance) {
		this.resourcePath = resourcePath;
		this.name = name;
		this.health = health;
		this.damageMin = damageMin;
		this.damageMax = damageMax;
		this.armor = armor;
		this.dropsGold = dropsGold;
		this.critChance = critChance;
		this.attackType = attackType;
	}

	String toCookie() {
		return String.format("%s,%s,%d,%d,%d,%d,%d,%d,%d", this.resourcePath, name, attackType, health, damageMin,
				damageMax, armor, dropsGold, critChance);
	}

	static Enemy fromCookie(String theCookie) {
		Enemy enemy = new Enemy();
		try {
			String[] c = theCookie.split(",");
			enemy.resourcePath = c[0];
			enemy.name = c[1];
			enemy.attackType = Integer.parseInt(c[2]);
			enemy.health = Integer.parseInt(c[3]);
			enemy.damageMin = Integer.parseInt(c[4]);
			enemy.damageMax = Integer.parseInt(c[5]);
			enemy.armor = Integer.parseInt(c[6]);
			enemy.dropsGold = Integer.parseInt(c[7]);
			enemy.critChance = Integer.parseInt(c[8]);
		} catch (Exception exception) {
			return null;
		}
		return enemy;

	}

	String displayText() {
		String attackTypeString;
		if (attackType == 1) {
			attackTypeString = "PHYSICAL";
		} else {
			attackTypeString = "MAGIC";
		}
		return "Name: " + name + 
				", health = " + health + 
				", Attack Type: " + attackTypeString + 
				", attack = " + damageMin + "-" + damageMax + 
				", armor = " + armor + 
				", Gold reward = " + dropsGold + 
				", critical chance = " + critChance+"%";
	}

}
