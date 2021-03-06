package com.aarestu.controller;

public class Item {
	String name;
	int currentHealth;
	int healthLimit;
	int mana;
	int maxMana;
	int attackMin;
	int attackMax;
	int armor;
	int magicResist;
	int critChance;
	int costsGold;
	int hpRegen;
	int manaRegen;
	
	public Item(String name, int price) {
		this(name, 0, 0, 0, 0, 0, 0, 0,0,0,0,0, price);
	}
	
	Item setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
		return this;
	}
	
	Item setHealthLimit(int healthLimit) {
		this.healthLimit = healthLimit;
		return this;
	}
	
	Item setMana(int mana) {
		this.mana = mana;
		return this;
	}
	
	Item setMaxMana(int maxMana) {
		this.maxMana = maxMana;
		return this;
	}
	
	Item setAttackMin(int attackMin) {
		this.attackMin = attackMin;
		return this;
	}
	
	Item setAttackMax(int attackMax) {
		this.attackMax = attackMax;
		return this;
	}
	
	Item setArmor(int armor) {
		this.armor = armor;
		return this;
	}
	
	Item setMagicResist(int magicResist) {
		this.magicResist = magicResist;
		return this;
	}
	
	Item setCritChance(int critChance) {
		this.critChance = critChance;
		return this;
	}
	Item setHpRegen(int hpRegen) {
		this.hpRegen = hpRegen;
		return this;
	}
	Item setManaRegen(int manaRegen) {
		this.manaRegen = manaRegen;
		return this;
	}

	public Item(String name, int currentHealth, int healthLimit,int mana, int maxMana, int attackMin, int attackMax, int armor,
			int magicResist, int critChance,int hpRegen, int manaRegen, int costsGold) {
		this.name = name;
		this.currentHealth = currentHealth;
		this.healthLimit = healthLimit;
		this.mana=mana;
		this.maxMana=maxMana;
		this.attackMin = attackMin;
		this.attackMax = attackMax;
		this.armor = armor;
		this.magicResist = magicResist;
		this.critChance = critChance;
		this.costsGold = costsGold;
		this.hpRegen=hpRegen;
		this.manaRegen=manaRegen;
	}
}
