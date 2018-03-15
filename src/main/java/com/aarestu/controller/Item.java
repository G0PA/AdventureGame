package com.aarestu.controller;

public class Item {
	String name;
	int currentHealth;
	int healthLimit;
	int attackMin;
	int attackMax;
	int armor;
	int magicResist;
	int critChance;
	int costsGold;
	public Item(String name,int currentHealth,int healthLimit, int attackMin, int attackMax, int armor, int magicResist, int critChance, int costsGold)
	{
		this.name=name;
		this.currentHealth=currentHealth;
		this.healthLimit=healthLimit;
		this.attackMin=attackMin;
		this.attackMax=attackMax;
		this.armor=armor;
		this.magicResist=magicResist;
		this.critChance=critChance;
		this.costsGold=costsGold;
	}
}
