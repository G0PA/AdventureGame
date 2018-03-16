package com.aarestu.controller;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SettlementController {
	int health;
	int attackMin;
	int armor;
	int magicResist;
	int gold;
	String[] attacks;
	int attackMax;
	int critChance;
	String theBadCookie="";
	String resource="";
	int maxHealth;
	int count;
	Item tempItem;
	Item tempItem2;
	Item tempItem3;
	Item tempItem4;
	ArrayList<Item> items=new ArrayList<Item>();
	ArrayList<Item> shop;
	ArrayList<Item> getFourItems(int min, int max,ArrayList<Item> theItems)
	{
		for(int i=1; i<=4; i++)
		{
	   int range = (max - min) + 1;
	   int theIndex=(int)(Math.random() * range) + min;
	   if(theItems.contains(items.get(theIndex)))
	   {
		   i--;
		   continue;
	   }
	   theItems.add(items.get(theIndex));
	   //items.remove(theIndex);
		}
	   return theItems;
	}
	@RequestMapping(value="/skipSettlement",method=RequestMethod.GET)
	String hello(ModelMap model,HttpServletResponse response,@CookieValue("leftEnemies") String fooCookie,@CookieValue("resource") String resourceCookie,@CookieValue("bossState")String bossState)
	{
		if(!bossState.equals("dead"))
		{
		int count=Integer.parseInt(fooCookie)+1;
		
		Cookie c=new Cookie("leftEnemies",String.valueOf(count));
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		model.addAttribute("enemiesLeft",String.valueOf(count));
		model.addAttribute("resource",resourceCookie);
		
		Cookie d=new Cookie("bossState","dead");
		d.setPath("/");
		d.setMaxAge(60*60*24*2);
		response.addCookie(d);
		}
		return "skippedSettlement";
	}
	
	@RequestMapping(value="/sleep",method=RequestMethod.GET)
	String sleep(ModelMap model,HttpServletResponse response,@CookieValue("leftEnemies") String fooCookie,@CookieValue("resource") String resourceCookie,@CookieValue("bossState")String bossState,@CookieValue("hero")String heroCookie)
	{
		if(!bossState.equals("dead"))
		{
		int count=Integer.parseInt(fooCookie)-1;
		
		Cookie c=new Cookie("leftEnemies",String.valueOf(count));
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		model.addAttribute("enemiesLeft",String.valueOf(count));
		model.addAttribute("resource",resourceCookie);
		if(health+15<=maxHealth) {
		health+=15;
		}else {
			health=maxHealth;
		}
		String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
				+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
		Cookie d=new Cookie("bossState","dead");
		Cookie cc = new Cookie("hero", leCookie);
		cc.setPath("/");
		cc.setMaxAge(60*60*24*2);
		response.addCookie(cc);
		d.setPath("/");
		d.setMaxAge(60*60*24*2);
		model.addAttribute("sleep","You decide to sleep Restoring 15 Health");
		response.addCookie(d);
		}
		return "leaveSettlement";
	}
	
	@RequestMapping(value="/leaveSettlement",method=RequestMethod.GET)
	String bye(ModelMap model,HttpServletResponse response,@CookieValue("leftEnemies") String fooCookie,@CookieValue("resource") String resourceCookie,@CookieValue("bossState")String bossState)
	{
		

		model.addAttribute("resource",resourceCookie);
		
		Cookie d=new Cookie("bossState","dead");
		d.setPath("/");
		d.setMaxAge(60*60*24*2);
		response.addCookie(d);
		
		return "leaveSettlement";
	}
	
	@RequestMapping(value="/settlement", method=RequestMethod.GET)
	String insideSettlement(ModelMap model,HttpServletResponse response,@CookieValue("hero")String heroCookie,@CookieValue("resource") String resourceCookie,@CookieValue("bossState")String bossStateCookie)
	{
		
		model.addAttribute("resource",resourceCookie);
		String theCookie = heroCookie.replaceAll("[A-Za-z=]+", "");
		String[] heroAttributes = theCookie.split(",");
		String currentHealth=heroAttributes[0].trim();
		String[] healthArr =currentHealth.split("/");
		maxHealth=Integer.parseInt(healthArr[1]);
		health=Integer.parseInt(healthArr[0]);
		attacks=heroAttributes[1].trim().split("-");
		attackMin = Integer.parseInt(attacks[0]);
		attackMax = Integer.parseInt(attacks[1]);
		armor = Integer.parseInt(heroAttributes[2].trim());
		magicResist = Integer.parseInt(heroAttributes[3].trim());
		gold = Integer.parseInt(heroAttributes[4].trim());
		critChance=Integer.parseInt(heroAttributes[5].trim());
		Item smallPotion=new Item("Small Potion",25,0,0,0,0,0,0,9);
		Item magicBoots=new Item("Magic Boots",0,0,0,0,0,2,0,17);
		Item woodenShield=new Item("Wooden Shield",0,0,0,0,2,0,0,17);
		Item woodenSword=new Item("Wooden Sword",0,0,2,2,0,0,0,19);
		Item smallVest=new Item("Small Vest",15,15,0,0,0,0,0,14);
		Item swordSharpener=new Item("Sword Sharpener",0,0,0,0,0,0,3,14);
		Item woodenBow=new Item("Woden Bow",0,0,0,4,0,0,0,20);
		Item strawHat=new Item("Straw Hat",15,0,0,0,1,0,0,14);
		Item ironSword=new Item("Iron Sword",0,0,4,4,0,0,0,30);
		Item mediumPotion=new Item("Medium Potion",50,0,0,0,0,0,0,16);
		Item energyBoost=new Item("Energy Boost",10,0,1,1,1,1,2,24);
		Item leatherArmor=new Item("Leather Armor",0,0,0,0,2,2,0,26);
		Item vitalityBoost=new Item("Vitality Boost",25,0,0,2,0,0,0,17);
		Item handMadeArrows=new Item("Hand-made Arrows",0,0,1,1,0,0,1,14);
		Item kunai=new Item("Kunai",0,0,0,2,0,0,0,12);
		model.addAttribute("message",heroCookie+"%");
		items.add(smallPotion);
		items.add(magicBoots);
		items.add(woodenShield);
		items.add(woodenSword);
		items.add(smallVest);
		items.add(swordSharpener);
		items.add(woodenBow);
		items.add(strawHat);
		items.add(ironSword);
		items.add(mediumPotion);
		items.add(energyBoost);
		items.add(leatherArmor);
		items.add(kunai);
		items.add(handMadeArrows);
		items.add(vitalityBoost);
		if(!bossStateCookie.equals("shopping")) {
		 shop=new ArrayList<Item>();
		getFourItems(0,items.size()-1,shop);
		}
		
			 tempItem=shop.get(0);
			model.addAttribute("name",tempItem.name+": ");
			if(tempItem.currentHealth!=0)
			{
				model.addAttribute("currentHealth","Current Health +"+String.valueOf(tempItem.currentHealth)+" ");
			}
			if(tempItem.healthLimit!=0)
			{
				model.addAttribute("maxHealth","Max Health +"+String.valueOf(tempItem.healthLimit)+" ");
			}
			if(tempItem.attackMin!=0)
			{
				model.addAttribute("attackMin","Attack Minimum +"+String.valueOf(tempItem.attackMin)+" ");
			}
			if(tempItem.attackMax!=0)
			{
				model.addAttribute("attackMax","Attack Maximum +"+String.valueOf(tempItem.attackMax)+" ");
			}
			if(tempItem.armor!=0)
			{
				model.addAttribute("armor","Armor +"+String.valueOf(tempItem.armor)+" ");
			}
			if(tempItem.magicResist!=0)
			{
				model.addAttribute("magicResist","Magic Resist +"+String.valueOf(tempItem.magicResist)+" ");
			}
			if(tempItem.critChance!=0)
			{
				model.addAttribute("critChance","Critical Chance +"+String.valueOf(tempItem.critChance)+" ");
			}
			model.addAttribute("costsGold","Gold -"+String.valueOf(tempItem.costsGold));
		
			tempItem2=shop.get(1);
			model.addAttribute("secondName",tempItem2.name+": ");
			if(tempItem2.currentHealth!=0)
			{
				model.addAttribute("secondCurrentHealth","Current Health +"+String.valueOf(tempItem2.currentHealth)+" ");
			}
			if(tempItem2.healthLimit!=0)
			{
				model.addAttribute("secondMaxHealth","Max Health +"+String.valueOf(tempItem2.healthLimit)+" ");
			}
			if(tempItem2.attackMin!=0)
			{
				model.addAttribute("secondAttackMin","Attack Minimum +"+String.valueOf(tempItem2.attackMin)+" ");
			}
			if(tempItem2.attackMax!=0)
			{
				model.addAttribute("secondAttackMax","Attack Maximum +"+String.valueOf(tempItem2.attackMax)+" ");
			}
			if(tempItem2.armor!=0)
			{
				model.addAttribute("secondArmor","Armor +"+String.valueOf(tempItem2.armor)+" ");
			}
			if(tempItem2.magicResist!=0)
			{
				model.addAttribute("secondMagicResist","Magic Resist +"+String.valueOf(tempItem2.magicResist)+" ");
			}
			if(tempItem2.critChance!=0)
			{
				model.addAttribute("secondCritChance","Critical Chance +"+String.valueOf(tempItem2.critChance)+" ");
			}
			model.addAttribute("secondCostsGold","Gold -"+String.valueOf(tempItem2.costsGold)+" ");
			
			tempItem3=shop.get(2);
			model.addAttribute("thirdName",tempItem3.name+": ");
			if(tempItem3.currentHealth!=0)
			{
				model.addAttribute("thirdCurrentHealth","Current Health +"+String.valueOf(tempItem3.currentHealth)+" ");
			}
			if(tempItem3.healthLimit!=0)
			{
				model.addAttribute("thirdMaxHealth","Max Health +"+String.valueOf(tempItem3.healthLimit)+" ");
			}
			if(tempItem3.attackMin!=0)
			{
				model.addAttribute("thirdAttackMin","Attack Minimum +"+String.valueOf(tempItem3.attackMin)+" ");
			}
			if(tempItem3.attackMax!=0)
			{
				model.addAttribute("thirdAttackMax","Attack Maximum +"+String.valueOf(tempItem3.attackMax)+" ");
			}
			if(tempItem3.armor!=0)
			{
				model.addAttribute("thirdArmor","Armor +"+String.valueOf(tempItem3.armor)+" ");
			}
			if(tempItem3.magicResist!=0)
			{
				model.addAttribute("thirdMagicResist","Magic Resist +"+String.valueOf(tempItem3.magicResist)+" ");
			}
			if(tempItem3.critChance!=0)
			{
				model.addAttribute("thirdCritChance","Critical Chance +"+String.valueOf(tempItem3.critChance)+" ");
			}
			model.addAttribute("thirdCostsGold","Gold -"+String.valueOf(tempItem3.costsGold)+" ");
			
			tempItem4=shop.get(3);
			model.addAttribute("fourthName",tempItem4.name+": ");
			if(tempItem4.currentHealth!=0)
			{
				model.addAttribute("fourthCurrentHealth","Current Health +"+String.valueOf(tempItem4.currentHealth)+" ");
			}
			if(tempItem4.healthLimit!=0)
			{
				model.addAttribute("fourthMaxHealth","Max Health +"+String.valueOf(tempItem4.healthLimit)+" ");
			}
			if(tempItem4.attackMin!=0)
			{
				model.addAttribute("fourthAttackMin","Attack Minimum +"+String.valueOf(tempItem4.attackMin)+" ");
			}
			if(tempItem4.attackMax!=0)
			{
				model.addAttribute("fourthAttackMax","Attack Maximum +"+String.valueOf(tempItem4.attackMax)+" ");
			}
			if(tempItem4.armor!=0)
			{
				model.addAttribute("fourthArmor","Armor +"+String.valueOf(tempItem4.armor)+" ");
			}
			if(tempItem4.magicResist!=0)
			{
				model.addAttribute("fourthMagicResist","Magic Resist +"+String.valueOf(tempItem4.magicResist)+" ");
			}
			if(tempItem4.critChance!=0)
			{
				model.addAttribute("fourthCritChance","Critical Chance +"+String.valueOf(tempItem4.critChance)+" ");
			}
			model.addAttribute("fourthCostsGold","Gold -"+String.valueOf(tempItem4.costsGold)+" ");
		items.clear();
		Cookie boss=new Cookie("bossState","shopping");
		boss.setPath("/");
		boss.setMaxAge(60*60*24*2);
		response.addCookie(boss);
		return "settlement";
	}
	@RequestMapping(value="/item1",method=RequestMethod.GET)
	String theItem(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(gold-tempItem.costsGold>=0) {
		gold-=tempItem.costsGold;
		}else {
			String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
					+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
			Cookie c = new Cookie("hero", leCookie);
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",leCookie+"%");
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		if(tempItem.currentHealth!=0)
		{
			if(health+tempItem.currentHealth<maxHealth) {
			health+=tempItem.currentHealth;
			}else {
				health=maxHealth;
			}
		}
		if(tempItem.healthLimit!=0)
		{
			maxHealth+=tempItem.healthLimit;
		}
		if(tempItem.attackMin!=0)
		{
			attackMin+=tempItem.attackMin;
		}
		if(tempItem.attackMax!=0)
		{
			attackMax+=tempItem.attackMax;
		}
		if(tempItem.armor!=0)
		{
			armor+=tempItem.armor;
		}
		if(tempItem.magicResist!=0)
		{
			magicResist+=tempItem.magicResist;
		}
		if(tempItem.critChance!=0)
		{
			critChance+=tempItem.critChance;
		}
		
		String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
				+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
		Cookie c = new Cookie("hero", leCookie);
		model.addAttribute("resource",resourceCookie);
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		model.addAttribute("message",leCookie+"%");
		return "shopped";
	}
	
	@RequestMapping(value="/item2",method=RequestMethod.GET)
	String theItem2(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(gold-tempItem2.costsGold>=0) {
		gold-=tempItem2.costsGold;
		}else {
			String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
					+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
			Cookie c = new Cookie("hero", leCookie);
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",leCookie+"%");
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		if(tempItem2.currentHealth!=0)
		{
			if(health+tempItem2.currentHealth<maxHealth) {
			health+=tempItem2.currentHealth;
			}else {
				health=maxHealth;
			}
		}
		if(tempItem2.healthLimit!=0)
		{
			maxHealth+=tempItem2.healthLimit;
		}
		if(tempItem2.attackMin!=0)
		{
			attackMin+=tempItem2.attackMin;
		}
		if(tempItem2.attackMax!=0)
		{
			attackMax+=tempItem2.attackMax;
		}
		if(tempItem2.armor!=0)
		{
			armor+=tempItem2.armor;
		}
		if(tempItem2.magicResist!=0)
		{
			magicResist+=tempItem2.magicResist;
		}
		if(tempItem2.critChance!=0)
		{
			critChance+=tempItem2.critChance;
		}
		
		String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
				+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
		Cookie c = new Cookie("hero", leCookie);
		model.addAttribute("resource",resourceCookie);
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		model.addAttribute("message",leCookie+"%");
		return "shopped";
	}
	
	@RequestMapping(value="/item3",method=RequestMethod.GET)
	String theItem3(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(gold-tempItem3.costsGold>=0) {
		gold-=tempItem3.costsGold;
		}else {
			String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
					+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
			Cookie c = new Cookie("hero", leCookie);
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",leCookie+"%");
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		if(tempItem3.currentHealth!=0)
		{
			if(health+tempItem3.currentHealth<maxHealth) {
			health+=tempItem3.currentHealth;
			}else {
				health=maxHealth;
			}
		}
		if(tempItem3.healthLimit!=0)
		{
			maxHealth+=tempItem3.healthLimit;
		}
		if(tempItem3.attackMin!=0)
		{
			attackMin+=tempItem3.attackMin;
		}
		if(tempItem3.attackMax!=0)
		{
			attackMax+=tempItem3.attackMax;
		}
		if(tempItem3.armor!=0)
		{
			armor+=tempItem3.armor;
		}
		if(tempItem3.magicResist!=0)
		{
			magicResist+=tempItem3.magicResist;
		}
		if(tempItem3.critChance!=0)
		{
			critChance+=tempItem3.critChance;
		}
		
		String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
				+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
		Cookie c = new Cookie("hero", leCookie);
		model.addAttribute("resource",resourceCookie);
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		model.addAttribute("message",leCookie+"%");
		return "shopped";
	}
	
	@RequestMapping(value="/item4",method=RequestMethod.GET)
	String theItem4(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(gold-tempItem4.costsGold>=0) {
		gold-=tempItem4.costsGold;
		}else {
			String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
					+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
			Cookie c = new Cookie("hero", leCookie);
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",leCookie+"%");
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		if(tempItem4.currentHealth!=0)
		{
			if(health+tempItem4.currentHealth<maxHealth) {
			health+=tempItem4.currentHealth;
			}else {
				health=maxHealth;
			}
		}
		if(tempItem4.healthLimit!=0)
		{
			maxHealth+=tempItem4.healthLimit;
		}
		if(tempItem4.attackMin!=0)
		{
			attackMin+=tempItem4.attackMin;
		}
		if(tempItem4.attackMax!=0)
		{
			attackMax+=tempItem4.attackMax;
		}
		if(tempItem4.armor!=0)
		{
			armor+=tempItem4.armor;
		}
		if(tempItem4.magicResist!=0)
		{
			magicResist+=tempItem4.magicResist;
		}
		if(tempItem4.critChance!=0)
		{
			critChance+=tempItem4.critChance;
		}
		
		String leCookie = "health = " + health +"/"+maxHealth+ ",   attack = " + attackMin +"-"+attackMax+",   armor = " + armor
				+ ",   magic resist = " + magicResist + ",   gold = " + gold+",   critical chance = "+critChance;
		Cookie c = new Cookie("hero", leCookie);
		model.addAttribute("resource",resourceCookie);
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		model.addAttribute("message",leCookie+"%");
		return "shopped";
	}
	
	
}