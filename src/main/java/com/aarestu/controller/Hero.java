package com.aarestu.controller;

import javax.servlet.http.Cookie;

public class Hero {
	int hp=110;
	int attackMin=10;
	int attackMax=15;
	int armor=5;
	int magicResist=5;
	int gold=5;
	int critChance=5;
	int maxHp=110;
	public Hero()
	{
		
	}
	public Hero(int hp,int maxHp, int attackMin,int attackMax, int armor, int magicResist, int gold, int critChance)
	{
		this.hp=hp;
		this.attackMin=attackMin;
		this.attackMax=attackMax;
		this.armor=armor;
		this.magicResist=magicResist;
		this.gold=gold;
		this.critChance=critChance;
		this.maxHp=maxHp;
	}
	
	public static Hero fromCookie(Cookie cookie) {
		return null;
	}

}
