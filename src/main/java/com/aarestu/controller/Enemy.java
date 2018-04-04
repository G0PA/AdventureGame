package com.aarestu.controller;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

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
	int defense;
	public Hero enemyAttack(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response) {
		int tempHealth=hero.hp;
		if(enemy.attackType==1) {
			defense=hero.armor;
		}else {
			defense=hero.magicResist;
		}
		boolean crit=Utils.critical(enemy.critChance);
		double multiply=1;
		if(crit==true) {
			model.addAttribute("enemyCritically","CRITICALLY ");
			multiply=1.8;
		} else {
			model.addAttribute("enemyCritically","");
		}
		int theEnemyDamage;
		theEnemyDamage=(int)(Utils.attack(enemy.damageMin,enemy.damageMax)*multiply) - defense; 
		if(theEnemyDamage<=0) {
			hero.hp = hero.hp - 1;
		}
		 else {
			hero.hp-=theEnemyDamage;
		}
		int enemyDamage=tempHealth-hero.hp;
		if(hero.heroClass.equals("Giant")) {
			hero.rage+=enemyDamage;
		}
		model.addAttribute("enemy", String.valueOf(enemy.health));
		model.addAttribute("enemyName",enemy.name);
		model.addAttribute("enemyDamage",String.valueOf(enemyDamage));
		model.addAttribute("message2",hero.createDisplayText());
		Cookie heroCookie=hero.createCookie();
		heroCookie.setPath("/");
		heroCookie.setMaxAge(60*60*24*2);
		response.addCookie(heroCookie);
		return hero;
	}

}
