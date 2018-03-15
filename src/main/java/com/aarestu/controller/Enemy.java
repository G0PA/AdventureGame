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
	public Enemy(String resourcePath,String name,int attackType, int health, int damageMin, int damageMax, int armor, int dropsGold, int critChance)
	{
		this.resourcePath=resourcePath;
		this.name=name;
		this.health=health;
		this.damageMin=damageMin;
		this.damageMax=damageMax;
		this.armor=armor;
		this.dropsGold=dropsGold;
		this.critChance=critChance;
		this.attackType=attackType;
	}

}
