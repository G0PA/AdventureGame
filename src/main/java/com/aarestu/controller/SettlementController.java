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
	Hero hero;
	String theBadCookie="";
	String resource="";
	int count;
	Item tempItem;
	Item tempItem2;
	Item tempItem3;
	Item tempItem4;
	Item tempItem5;
	ArrayList<Item> items=new ArrayList<Item>();
	ArrayList<Item> shop;
	ArrayList<Item> getFiveItems(int min, int max,ArrayList<Item> theItems) {
		for(int i=1; i<=5; i++)
		{
	    int range = (max - min) + 1;
	    int theIndex=(int)(Math.random() * range) + min;
	    if(theItems.contains(items.get(theIndex))) {
		    i--;
		    continue;
	    }
	    theItems.add(items.get(theIndex));
	    //items.remove(theIndex);
		}
	    return theItems;
	}

	@RequestMapping(value = "/skipSettlement", method = RequestMethod.GET)
	String hello(ModelMap model, HttpServletResponse response, @CookieValue("hero") String heroCookie,
			@CookieValue("resource") String resourceCookie, @CookieValue("bossState") String bossState) {
		if (!bossState.equals("dead")) {
			hero = Hero.fromCookie(heroCookie);
			hero.enemyEncountersLeft += 1;
			if(hero.hp+hero.hpRegen<=hero.maxHp) {
				hero.hp+=hero.hpRegen;
			}else {
				hero.hp=hero.maxHp;
			}if(hero.mana+hero.manaRegen<=hero.maxMana) {
				hero.mana+=hero.manaRegen;
			}else {
				hero.mana=hero.maxMana;
			}
				model.addAttribute("hpRegen",hero.hpRegen);
				model.addAttribute("manaRegen",hero.manaRegen);
			Cookie theHero=hero.createCookie();
			theHero.setPath("/");
			theHero.setMaxAge(60*60*24*2);
			response.addCookie(theHero);
			model.addAttribute("resource", resourceCookie);
			model.addAttribute("enemiesLeft",hero.enemyEncountersLeft);

			Cookie d = new Cookie("bossState", "dead");
			d.setPath("/");
			d.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(d);
		}
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
		}else {
			model.addAttribute("zone","redWoods");
		}
		return "skippedSettlement";
	}

	@RequestMapping(value = "/sleep", method = RequestMethod.GET)
	String sleep(ModelMap model, HttpServletResponse response, @CookieValue("resource") String resourceCookie,
			@CookieValue("bossState") String bossState, @CookieValue("hero") String heroCookie) {
		if (!bossState.equals("dead")) {
			hero = Hero.fromCookie(heroCookie);
			hero.enemyEncountersLeft -= 1;
			model.addAttribute("enemiesLeft", hero.enemyEncountersLeft);
			model.addAttribute("resource", resourceCookie);
			if (hero.zone.equals("Green Woods")) {
				if (hero.hp + 15 <= hero.maxHp) {
					hero.hp += 15;
				} else {
					hero.hp = hero.maxHp;
				}
				if (hero.hp + hero.hpRegen <= hero.maxHp) {
					hero.hp += hero.hpRegen;
				} else {
					hero.hp = hero.maxHp;
				}
				if (hero.heroClass.equals("Giant")) {
					if (hero.hp + 15 <= hero.maxHp) {
						hero.hp += 15;
					} else {
						hero.hp = hero.maxHp;
					}
				}
				if (hero.mana + 15 <= hero.maxMana) {
					hero.mana += 15;
				} else {
					hero.mana = hero.maxMana;
				}
				if (hero.mana + hero.manaRegen <= hero.maxMana) {
					hero.mana += hero.manaRegen;
				} else {
					hero.mana = hero.maxMana;
				}
				model.addAttribute("sleep", "You decide to sleep Restoring 15 Health and 15 Mana");
				model.addAttribute("zone","hello");
			}else {
				if (hero.hp + 30 <= hero.maxHp) {
					hero.hp += 30;
				} else {
					hero.hp = hero.maxHp;
				}
				if (hero.mana + 30 <= hero.maxMana) {
					hero.mana += 30;
				} else {
					hero.mana = hero.maxMana;
				}
				if (hero.hp + hero.hpRegen <= hero.maxHp) {
					hero.hp += hero.hpRegen;
				} else {
					hero.hp = hero.maxHp;
				}
				if (hero.mana + hero.manaRegen <= hero.maxMana) {
					hero.mana += hero.manaRegen;
				} else {
					hero.mana = hero.maxMana;
				}
				model.addAttribute("sleep", "You decide to sleep Restoring 30 Health and 30 Mana");
				model.addAttribute("zone","redWoods");
			}
			

				model.addAttribute("hpRegen",hero.hpRegen);
				model.addAttribute("manaRegen",hero.manaRegen);
			Cookie d = new Cookie("bossState", "dead");
			Cookie cc = hero.createCookie();
			cc.setPath("/");
			cc.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(cc);
			d.setPath("/");
			d.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(d);
		}
		return "leaveSettlement";
	}

	@RequestMapping(value="/leaveSettlement",method=RequestMethod.GET)
	String bye(ModelMap model,HttpServletResponse response,@CookieValue("hero")String heroCookie,@CookieValue("leftEnemies") String fooCookie,@CookieValue("resource") String resourceCookie,@CookieValue("bossState")String bossState)
	{
		

		model.addAttribute("resource",resourceCookie);
		
		Cookie d=new Cookie("bossState","dead");
		d.setPath("/");
		d.setMaxAge(60*60*24*2);
		response.addCookie(d);
		Hero hero=Hero.fromCookie(heroCookie);
		if(hero.hp+hero.hpRegen<=hero.maxHp) {
			hero.hp+=hero.hpRegen;
		}else {
			hero.hp=hero.maxHp;
		}if(hero.mana+hero.manaRegen<=hero.maxMana) {
			hero.mana+=hero.manaRegen;
		}else {
			hero.mana=hero.maxMana;
		}
			model.addAttribute("hpRegen",hero.hpRegen);
			model.addAttribute("manaRegen",hero.manaRegen);
			Cookie f=hero.createCookie();
			f.setMaxAge(60*60*24*2);
			f.setPath("/");
			response.addCookie(f);
			if(hero.zone.equals("Green Woods")) {
				model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
		
		return "leaveSettlement";
	}
	
	@RequestMapping(value="/settlement", method=RequestMethod.GET)
	String insideSettlement(ModelMap model,HttpServletResponse response,@CookieValue("hero")String heroCookie,@CookieValue("resource") String resourceCookie,@CookieValue("bossState")String bossStateCookie,@CookieValue("leftEnemies")String leftEnemiesCookie)
	{
		if(Integer.parseInt(leftEnemiesCookie)<0)
		{
			leftEnemiesCookie="0";
		}
		hero=Hero.fromCookie(heroCookie);
		model.addAttribute("leftEnemies",leftEnemiesCookie);
		model.addAttribute("resource",resourceCookie);
		model.addAttribute("message", hero.createDisplayText());
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("sleepInfo","+15 Health +15 Mana");
		Item smallPotion = new Item("Small Health Potion", 9).setCurrentHealth(25);
		Item magicBoots = new Item("Magic Boots", 17).setMagicResist(2);
		Item woodenShield = new Item("Wooden Shield", 17).setArmor(2);
		Item woodenSword = new Item("Wooden Sword", 20).setAttackMin(2).setAttackMax(2);
		Item smallVest = new Item("Small Vest", 14).setHealthLimit(15).setCurrentHealth(15);
		Item swordSharpener = new Item("Sword Sharpener", 17).setCritChance(3);
		Item woodenBow = new Item("Woden Bow", 20).setAttackMax(4);
		Item strawHat = new Item("Straw Hat", 14).setArmor(1).setCurrentHealth(15);
		Item ironSword = new Item("Iron Sword", 33).setAttackMin(4).setAttackMax(4);
		Item mediumPotion = new Item("Medium Health Potion", 16).setCurrentHealth(50);
		Item energyBoost = new Item("Energy Boost", 24).setCurrentHealth(10).setAttackMin(1).setAttackMax(1).setArmor(1).setMagicResist(1).setCritChance(2);
		Item leatherArmor = new Item("Leather Armor", 27).setArmor(2).setMagicResist(2);
		Item vitalityBoost = new Item("Vitality Boost", 19).setCurrentHealth(20).setAttackMax(2);
		Item handMadeArrows = new Item("Hand-made Arrows", 14).setAttackMin(1).setAttackMax(1).setCritChance(1);
		Item kunai = new Item("Kunai", 13).setAttackMax(2);
		Item largePotion=new Item("Large Health Potion", 29).setCurrentHealth(100);
		Item linenShirt=new Item("Linen Shirt",16).setArmor(1).setMagicResist(1);
		Item mediumVest=new Item("Medium Vest",24).setCurrentHealth(30).setHealthLimit(30);
		Item smallManaPotion=new Item("Small Mana Potion",9).setMana(25);
		Item mediumManaPotion=new Item("Medium Mana Potion",16).setMana(50);
		Item largeManaPotion=new Item("Large Mana Potion",29).setMana(100);
		Item smallMixedPotion=new Item("Small Mixed Potion",16).setCurrentHealth(25).setMana(25);
		Item mediumMixedPotion=new Item("Medium Mixed Potion",28).setCurrentHealth(50).setMana(50);
		Item smallCape=new Item("Small Cape",18).setMaxMana(20).setMana(20);
		Item mediumCape=new Item("Medium Cape",30).setMaxMana(40).setMana(40);
		Item woodenStaff=new Item("Wooden Staff",32).setMana(10).setMaxMana(30).setMagicResist(2);
		Item vitalityNecklace=new Item("Vitality Necklace",30).setCurrentHealth(15).setHealthLimit(30).setMaxMana(30).setMana(15);
		Item rejuvinationBracelet=new Item("Rejuvination Bracelet",23).setHpRegen(4).setManaRegen(4);
		Item smallJacket=new Item("Small Jacket",16).setCurrentHealth(15).setHpRegen(2);
		Item brokenStaff=new Item("Broken Staff",16).setMana(15).setManaRegen(2);
		model.addAttribute("message", hero.createDisplayText());
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
		items.add(largePotion);
		items.add(linenShirt);
		items.add(mediumVest);
		items.add(smallManaPotion);
		items.add(mediumManaPotion);
		items.add(largeManaPotion);
		items.add(smallMixedPotion);
		items.add(mediumMixedPotion);
		items.add(smallCape);
		items.add(mediumCape);
		items.add(woodenStaff);
		items.add(vitalityNecklace);
		items.add(rejuvinationBracelet);
		items.add(smallJacket);
		items.add(brokenStaff);
		}else {
			model.addAttribute("sleepInfo","+30 Health +30 Mana");
			Item largePotion=new Item("Large Health Potion", 29).setCurrentHealth(100);
			Item largeManaPotion=new Item("Large Mana Potion",29).setMana(100);
			Item fireSword=new Item("Fire Sword",61).setAttackMin(5).setAttackMax(5);
			Item enhancedDagger=new Item("Enhanced Dagger",47).setAttackMin(4).setAttackMax(2);
			Item enhancedBow=new Item("Enhanced Bow",47).setAttackMax(6);
			Item steelSharpener=new Item("Steel Sharpener",55).setCritChance(5);
			Item specialHealthPotion=new Item("Special Health Potion",50).setCurrentHealth(150);
			Item specialManaPotion=new Item("Special Mana Potion",50).setMana(150);
			Item plateArmor=new Item("Plate Armor",57).setArmor(4).setMagicResist(4);
			Item tunic=new Item("Tunic",59).setCurrentHealth(30).setHealthLimit(50).setMaxMana(50).setMana(30);
			Item largeMixedPotion=new Item("Large Mixed Potion",58).setCurrentHealth(100).setMaxMana(50);
			Item largeEnergyBoost=new Item("Large Energy Boost",54).setCurrentHealth(25).setAttackMin(2).setAttackMax(2).setArmor(2).setMagicResist(2).setCritChance(2);
			Item mageHat=new Item("Mage Hat",68).setMaxMana(60).setMana(40).setMagicResist(2).setCritChance(2);
			Item amuletOfRestoration=new Item("Amulet of Restoration",59).setHpRegen(6).setManaRegen(6).setCurrentHealth(30).setMana(30);
			Item upgradedFireSword=new Item("Upgraded Fire Sword",100).setAttackMin(6).setAttackMax(6).setCritChance(4);
			Item specialMixedPotion=new Item("Special Mixed Potion",88).setMana(150).setCurrentHealth(150);
			Item magicShield=new Item("Magic Shield",48).setMagicResist(4).setMaxMana(40);
			Item steelShield=new Item("Steel Shield",48).setArmor(4).setHealthLimit(40);
			Item mediumJacket=new Item("Medium Jacket",52).setCurrentHealth(45).setHpRegen(6).setHealthLimit(20);
			Item staffOfMagic=new Item("Staff of Magic",49).setMana(50).setManaRegen(3).setMaxMana(20);
			items.add(largePotion);
			items.add(largeManaPotion);
			items.add(fireSword);
			items.add(enhancedDagger);
			items.add(enhancedBow);
			items.add(specialHealthPotion);
			items.add(specialManaPotion);
			items.add(steelSharpener);
			items.add(plateArmor);
			items.add(tunic);
			items.add(largeMixedPotion);
			items.add(largeEnergyBoost);
			items.add(mageHat);
			items.add(amuletOfRestoration);
			items.add(upgradedFireSword);
			items.add(specialMixedPotion);
			items.add(magicShield);
			items.add(steelShield);
			items.add(mediumJacket);
			items.add(staffOfMagic);
		}

		if (!bossStateCookie.equals("shopping")) {
			shop = new ArrayList<Item>();
			getFiveItems(0, items.size() - 1, shop);
		}
		
			 tempItem=shop.get(0);
			model.addAttribute("name",tempItem.name+":  ");
			if(tempItem.currentHealth!=0)
			{
				model.addAttribute("currentHealth","Current Health +"+String.valueOf(tempItem.currentHealth)+"  ");
			}
			if(tempItem.healthLimit!=0)
			{
				model.addAttribute("maxHealth","Max Health +"+String.valueOf(tempItem.healthLimit)+"  ");
			}
			if(tempItem.mana!=0)
			{
				model.addAttribute("mana","Mana +"+String.valueOf(tempItem.mana)+"  ");
			}
			if(tempItem.maxMana!=0)
			{
				model.addAttribute("maxMana","Max Mana +"+String.valueOf(tempItem.maxMana)+"  ");
			}
			if(tempItem.attackMin!=0)
			{
				model.addAttribute("attackMin","Attack Minimum +"+String.valueOf(tempItem.attackMin)+"  ");
			}
			if(tempItem.attackMax!=0)
			{
				model.addAttribute("attackMax","Attack Maximum +"+String.valueOf(tempItem.attackMax)+"  ");
			}
			if(tempItem.armor!=0)
			{
				model.addAttribute("armor","Armor +"+String.valueOf(tempItem.armor)+"  ");
			}
			if(tempItem.magicResist!=0)
			{
				model.addAttribute("magicResist","Magic Resist +"+String.valueOf(tempItem.magicResist)+"  ");
			}
			if(tempItem.critChance!=0)
			{
				model.addAttribute("critChance","Critical Chance +"+String.valueOf(tempItem.critChance)+"%  ");
			}
			if(tempItem.hpRegen!=0) 
			{
				model.addAttribute("hpRegen","Hp Regen +"+String.valueOf(tempItem.hpRegen)+"  ");
			}
			if(tempItem.manaRegen!=0)
			{
				model.addAttribute("manaRegen","Mana Regen +"+String.valueOf(tempItem.manaRegen)+"  ");
			}
			model.addAttribute("costsGold","Gold -"+String.valueOf(tempItem.costsGold));
		
			tempItem2=shop.get(1);
			model.addAttribute("secondName",tempItem2.name+":  ");
			if(tempItem2.currentHealth!=0)
			{
				model.addAttribute("secondCurrentHealth","Current Health +"+String.valueOf(tempItem2.currentHealth)+"  ");
			}
			if(tempItem2.healthLimit!=0)
			{
				model.addAttribute("secondMaxHealth","Max Health +"+String.valueOf(tempItem2.healthLimit)+"  ");
			}
			
			if(tempItem2.mana!=0)
			{
				model.addAttribute("secondMana","Mana +"+String.valueOf(tempItem2.mana)+"  ");
			}
			if(tempItem2.maxMana!=0)
			{
				model.addAttribute("secondMaxMana","Max Mana +"+String.valueOf(tempItem2.maxMana)+"  ");
			}
			if(tempItem2.attackMin!=0)
			{
				model.addAttribute("secondAttackMin","Attack Minimum +"+String.valueOf(tempItem2.attackMin)+"  ");
			}
			if(tempItem2.attackMax!=0)
			{
				model.addAttribute("secondAttackMax","Attack Maximum +"+String.valueOf(tempItem2.attackMax)+"  ");
			}
			if(tempItem2.armor!=0)
			{
				model.addAttribute("secondArmor","Armor +"+String.valueOf(tempItem2.armor)+"  ");
			}
			if(tempItem2.magicResist!=0)
			{
				model.addAttribute("secondMagicResist","Magic Resist +"+String.valueOf(tempItem2.magicResist)+"  ");
			}
			if(tempItem2.critChance!=0)
			{
				model.addAttribute("secondCritChance","Critical Chance +"+String.valueOf(tempItem2.critChance)+"%  ");
			}
			if(tempItem2.hpRegen!=0) 
			{
				model.addAttribute("secondHpRegen","Hp Regen +"+String.valueOf(tempItem2.hpRegen)+"  ");
			}
			if(tempItem2.manaRegen!=0)
			{
				model.addAttribute("secondManaRegen","Mana Regen +"+String.valueOf(tempItem2.manaRegen)+"  ");
			}
			model.addAttribute("secondCostsGold","Gold -"+String.valueOf(tempItem2.costsGold)+"  ");
			
			tempItem3=shop.get(2);
			model.addAttribute("thirdName",tempItem3.name+":  ");
			if(tempItem3.currentHealth!=0)
			{
				model.addAttribute("thirdCurrentHealth","Current Health +"+String.valueOf(tempItem3.currentHealth)+"  ");
			}
			if(tempItem3.healthLimit!=0)
			{
				model.addAttribute("thirdMaxHealth","Max Health +"+String.valueOf(tempItem3.healthLimit)+"  ");
			}
			if(tempItem3.mana!=0)
			{
				model.addAttribute("thirdMana","Mana +"+String.valueOf(tempItem3.mana)+"  ");
			}
			if(tempItem3.maxMana!=0)
			{
				model.addAttribute("thirdMaxMana","Max Mana +"+String.valueOf(tempItem3.maxMana)+"  ");
			}
			if(tempItem3.attackMin!=0)
			{
				model.addAttribute("thirdAttackMin","Attack Minimum +"+String.valueOf(tempItem3.attackMin)+"  ");
			}
			if(tempItem3.attackMax!=0)
			{
				model.addAttribute("thirdAttackMax","Attack Maximum +"+String.valueOf(tempItem3.attackMax)+"  ");
			}
			if(tempItem3.armor!=0)
			{
				model.addAttribute("thirdArmor","Armor +"+String.valueOf(tempItem3.armor)+"  ");
			}
			if(tempItem3.magicResist!=0)
			{
				model.addAttribute("thirdMagicResist","Magic Resist +"+String.valueOf(tempItem3.magicResist)+"  ");
			}
			if(tempItem3.critChance!=0)
			{
				model.addAttribute("thirdCritChance","Critical Chance +"+String.valueOf(tempItem3.critChance)+"%  ");
			}
			if(tempItem3.hpRegen!=0) 
			{
				model.addAttribute("thirdHpRegen","Hp Regen +"+String.valueOf(tempItem3.hpRegen)+"  ");
			}
			if(tempItem3.manaRegen!=0)
			{
				model.addAttribute("thirdManaRegen","Mana Regen +"+String.valueOf(tempItem3.manaRegen)+"  ");
			}
			model.addAttribute("thirdCostsGold","Gold -"+String.valueOf(tempItem3.costsGold)+"  ");
			
			tempItem4=shop.get(3);
			model.addAttribute("fourthName",tempItem4.name+":  ");
			if(tempItem4.currentHealth!=0)
			{
				model.addAttribute("fourthCurrentHealth","Current Health +"+String.valueOf(tempItem4.currentHealth)+"  ");
			}
			if(tempItem4.healthLimit!=0)
			{
				model.addAttribute("fourthMaxHealth","Max Health +"+String.valueOf(tempItem4.healthLimit)+"  ");
			}
			if(tempItem4.mana!=0)
			{
				model.addAttribute("fourthMana","Mana +"+String.valueOf(tempItem4.mana)+"  ");
			}
			if(tempItem4.maxMana!=0)
			{
				model.addAttribute("fourthMaxMana","Max Mana +"+String.valueOf(tempItem4.maxMana)+"  ");
			}
			if(tempItem4.attackMin!=0)
			{
				model.addAttribute("fourthAttackMin","Attack Minimum +"+String.valueOf(tempItem4.attackMin)+"  ");
			}
			if(tempItem4.attackMax!=0)
			{
				model.addAttribute("fourthAttackMax","Attack Maximum +"+String.valueOf(tempItem4.attackMax)+"  ");
			}
			if(tempItem4.armor!=0)
			{
				model.addAttribute("fourthArmor","Armor +"+String.valueOf(tempItem4.armor)+"  ");
			}
			if(tempItem4.magicResist!=0)
			{
				model.addAttribute("fourthMagicResist","Magic Resist +"+String.valueOf(tempItem4.magicResist)+"  ");
			}
			if(tempItem4.critChance!=0)
			{
				model.addAttribute("fourthCritChance","Critical Chance +"+String.valueOf(tempItem4.critChance)+"%  ");
			}
			if(tempItem4.hpRegen!=0) 
			{
				model.addAttribute("fourthHpRegen","Hp Regen +"+String.valueOf(tempItem4.hpRegen)+"  ");
			}
			if(tempItem4.manaRegen!=0)
			{
				model.addAttribute("fourthManaRegen","Mana Regen +"+String.valueOf(tempItem4.manaRegen)+"  ");
			}
			model.addAttribute("fourthCostsGold","Gold -"+String.valueOf(tempItem4.costsGold)+"  ");
			


			
			
			
			
			tempItem5=shop.get(4);
			model.addAttribute("fifthName",tempItem5.name+":  ");
			if(tempItem5.currentHealth!=0)
			{
				model.addAttribute("fifthCurrentHealth","Current Health +"+String.valueOf(tempItem5.currentHealth)+"  ");
			}
			if(tempItem5.healthLimit!=0)
			{
				model.addAttribute("fifthMaxHealth","Max Health +"+String.valueOf(tempItem5.healthLimit)+"  ");
			}
			if(tempItem5.mana!=0)
			{
				model.addAttribute("fifthMana","Mana +"+String.valueOf(tempItem5.mana)+"  ");
			}
			if(tempItem5.maxMana!=0)
			{
				model.addAttribute("fifthMaxMana","Max Mana +"+String.valueOf(tempItem5.maxMana)+"  ");
			}
			if(tempItem5.attackMin!=0)
			{
				model.addAttribute("fifthAttackMin","Attack Minimum +"+String.valueOf(tempItem5.attackMin)+"  ");
			}
			if(tempItem5.attackMax!=0)
			{
				model.addAttribute("fifthAttackMax","Attack Maximum +"+String.valueOf(tempItem5.attackMax)+"  ");
			}
			if(tempItem5.armor!=0)
			{
				model.addAttribute("fifthArmor","Armor +"+String.valueOf(tempItem5.armor)+"  ");
			}
			if(tempItem5.magicResist!=0)
			{
				model.addAttribute("fifthMagicResist","Magic Resist +"+String.valueOf(tempItem5.magicResist)+"  ");
			}
			if(tempItem5.critChance!=0)
			{
				model.addAttribute("fifthCritChance","Critical Chance +"+String.valueOf(tempItem5.critChance)+"%  ");
			}
			if(tempItem5.hpRegen!=0) 
			{
				model.addAttribute("fifthHpRegen","Hp Regen +"+String.valueOf(tempItem5.hpRegen)+"  ");
			}
			if(tempItem5.manaRegen!=0)
			{
				model.addAttribute("fifthManaRegen","Mana Regen +"+String.valueOf(tempItem5.manaRegen)+"  ");
			}
			model.addAttribute("fifthCostsGold","Gold -"+String.valueOf(tempItem5.costsGold)+"  ");
			
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
		if(hero.gold-tempItem.costsGold>=0) {
			hero.gold-=tempItem.costsGold;
		} else {
			Cookie c = hero.createCookie();
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}

		if (tempItem.healthLimit != 0) {
			hero.maxHp += tempItem.healthLimit;
		}
		if (tempItem.currentHealth!=0) {
			if(hero.hp + tempItem.currentHealth < hero.maxHp) {
				hero.hp += tempItem.currentHealth;
			} else {
				hero.hp = hero.maxHp;
			}
		}
		
		if(tempItem.maxMana !=0)
		{
			hero.maxMana+=tempItem.maxMana;
		}
		if (tempItem.mana != 0) {
			if(hero.heroClass.equals("Giant")) {
				if(hero.hp+tempItem.mana*2<=hero.maxHp) {
				hero.hp+=tempItem.mana*2;
				}else {
					hero.hp=hero.maxHp;
				}
			}
			else if (hero.mana + tempItem.mana < hero.maxMana) {
				hero.mana += tempItem.mana;
			} else {
				hero.mana = hero.maxMana;
			}
		}
		if (tempItem.attackMin != 0) {
			if(hero.attackMin+tempItem.attackMin<=hero.attackMax) {
			hero.attackMin += tempItem.attackMin;
			}else {
				hero.attackMin=hero.attackMax;
			}
		}
		if (tempItem.attackMax != 0) {
			hero.attackMax += tempItem.attackMax;
		}
		if (tempItem.armor != 0) {
			hero.armor += tempItem.armor;
		}
		if (tempItem.magicResist != 0) {
			hero.magicResist += tempItem.magicResist;
		}
		if (tempItem.critChance != 0) {
			hero.critChance+=tempItem.critChance;
		}
		if(tempItem.hpRegen !=0) {
			hero.hpRegen+=tempItem.hpRegen;
		}
		if(tempItem.manaRegen !=0) {
			hero.manaRegen+=tempItem.manaRegen;
		}
		if(hero.heroClass.equals("Giant")) {
			model.addAttribute("spell","Earth Shock");
			hero.hp+=hero.mana*2;
			hero.maxHp+=hero.maxMana*2;
			hero.hpRegen+=hero.manaRegen*2;
			hero.mana=0;
			hero.manaRegen=0;
			hero.maxMana=0;
		}
		Cookie c = hero.createCookie();
		model.addAttribute("resource", resourceCookie);
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		model.addAttribute("message", hero.createDisplayText());
		return "shopped";
	}
	
	@RequestMapping(value="/item2",method=RequestMethod.GET)
	String theItem2(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(hero.gold-tempItem2.costsGold>=0) {
			hero.gold-=tempItem2.costsGold;
		} else {
			Cookie c = hero.createCookie();
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		if (tempItem2.healthLimit != 0) {
			hero.maxHp += tempItem2.healthLimit;
		}
		if (tempItem2.currentHealth!=0) {
			if(hero.hp + tempItem2.currentHealth < hero.maxHp) {
				hero.hp += tempItem2.currentHealth;
			} else {
				hero.hp = hero.maxHp;
			}
		}
		if(tempItem2.maxMana !=0)
		{
			hero.maxMana+=tempItem2.maxMana;
		}
		if (tempItem2.mana != 0) {
			if(hero.heroClass.equals("Giant")) {
				if(hero.hp+tempItem2.mana*2<=hero.maxHp) {
				hero.hp+=tempItem2.mana*2;
				}else {
					hero.hp=hero.maxHp;
				}
			}
			else if (hero.mana + tempItem2.mana < hero.maxMana) {
				hero.mana += tempItem2.mana;
			} else {
				hero.mana = hero.maxMana;
			}
		}
		if (tempItem2.attackMin != 0) {
			if(hero.attackMin+tempItem2.attackMin<=hero.attackMax) {
				hero.attackMin += tempItem2.attackMin;
				}else {
					hero.attackMin=hero.attackMax;
				}
			
		}
		if (tempItem2.attackMax != 0) {
			hero.attackMax += tempItem2.attackMax;
		}
		if (tempItem2.armor != 0) {
			hero.armor += tempItem2.armor;
		}
		if (tempItem2.magicResist != 0) {
			hero.magicResist += tempItem2.magicResist;
		}
		if (tempItem2.critChance != 0) {
			hero.critChance+=tempItem2.critChance;
		}
		if(tempItem2.hpRegen !=0) {
			hero.hpRegen+=tempItem2.hpRegen;
		}
		if(tempItem2.manaRegen !=0) {
			hero.manaRegen+=tempItem2.manaRegen;
		}
		
		if(hero.heroClass.equals("Giant")) {
			model.addAttribute("spell","Earth Shock");
			hero.hp+=hero.mana*2;
			hero.maxHp+=hero.maxMana*2;
			hero.hpRegen+=hero.manaRegen*2;
			hero.mana=0;
			hero.manaRegen=0;
			hero.maxMana=0;
		}
		Cookie c = hero.createCookie();
		model.addAttribute("resource", resourceCookie);
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		model.addAttribute("message", hero.createDisplayText());
		return "shopped";
	}
	
	@RequestMapping(value="/item3",method=RequestMethod.GET)
	String theItem3(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(hero.gold-tempItem3.costsGold>=0) {
			hero.gold-=tempItem3.costsGold;
		} else {
			Cookie c = hero.createCookie();
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		
		if (tempItem3.healthLimit != 0) {
			hero.maxHp += tempItem3.healthLimit;
		}
		if (tempItem3.currentHealth!=0) {
			if(hero.hp + tempItem3.currentHealth < hero.maxHp) {
				hero.hp += tempItem3.currentHealth;
			} else {
				hero.hp = hero.maxHp;
			}
		}
		
		if(tempItem3.maxMana !=0)
		{
			hero.maxMana+=tempItem3.maxMana;
		}
		if (tempItem3.mana != 0) {
			if(hero.heroClass.equals("Giant")) {
				if(hero.hp+tempItem3.mana*2<=hero.maxHp) {
				hero.hp+=tempItem3.mana*2;
				}else {
					hero.hp=hero.maxHp;
				}
			}
			else if (hero.mana + tempItem3.mana < hero.maxMana) {
				hero.mana += tempItem3.mana;
			} else {
				hero.mana = hero.maxMana;
			}
		}
		if (tempItem3.attackMin != 0) {
			if(hero.attackMin+tempItem3.attackMin<=hero.attackMax) {
				hero.attackMin += tempItem3.attackMin;
				}else {
					hero.attackMin=hero.attackMax;
				}
			
		}
		if (tempItem3.attackMax != 0) {
			hero.attackMax += tempItem3.attackMax;
		}
		if (tempItem3.armor != 0) {
			hero.armor += tempItem3.armor;
		}
		if (tempItem3.magicResist != 0) {
			hero.magicResist += tempItem3.magicResist;
		}
		if (tempItem3.critChance != 0) {
			hero.critChance+=tempItem3.critChance;
		}
		if(tempItem3.hpRegen !=0) {
			hero.hpRegen+=tempItem3.hpRegen;
		}
		if(tempItem3.manaRegen !=0) {
			hero.manaRegen+=tempItem3.manaRegen;
		}
		
		if(hero.heroClass.equals("Giant")) {
			model.addAttribute("spell","Earth Shock");
			hero.hp+=hero.mana*2;
			hero.maxHp+=hero.maxMana*2;
			hero.hpRegen+=hero.manaRegen*2;
			hero.mana=0;
			hero.manaRegen=0;
			hero.maxMana=0;
		}
		Cookie c = hero.createCookie();
		model.addAttribute("resource", resourceCookie);
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		model.addAttribute("message", hero.createDisplayText());
		return "shopped";
	}
	
	@RequestMapping(value="/item4",method=RequestMethod.GET)
	String theItem4(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(hero.gold-tempItem4.costsGold>=0) {
			hero.gold-=tempItem4.costsGold;
		} else {
			Cookie c = hero.createCookie();
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		
		if (tempItem4.healthLimit != 0) {
			hero.maxHp += tempItem4.healthLimit;
		}
		if (tempItem4.currentHealth!=0) {
			if(hero.hp + tempItem4.currentHealth < hero.maxHp) {
				hero.hp += tempItem4.currentHealth;
			} else {
				hero.hp = hero.maxHp;
			}
		}
		
		if(tempItem4.maxMana !=0)
		{
			hero.maxMana+=tempItem4.maxMana;
		}
		if (tempItem4.mana != 0) {
			if(hero.heroClass.equals("Giant")) {
				if(hero.hp+tempItem4.mana*2<=hero.maxHp) {
				hero.hp+=tempItem4.mana*2;
				}else {
					hero.hp=hero.maxHp;
				}
			}
			else if (hero.mana + tempItem4.mana < hero.maxMana) {
				hero.mana += tempItem4.mana;
			} else {
				hero.mana = hero.maxMana;
			}
		}
		if (tempItem4.attackMin != 0) {
			if(hero.attackMin+tempItem4.attackMin<=hero.attackMax) {
				hero.attackMin += tempItem4.attackMin;
				}else {
					hero.attackMin=hero.attackMax;
				}
			
		}
		if (tempItem4.attackMax != 0) {
			hero.attackMax += tempItem4.attackMax;
		}
		if (tempItem4.armor != 0) {
			hero.armor += tempItem4.armor;
		}
		if (tempItem4.magicResist != 0) {
			hero.magicResist += tempItem4.magicResist;
		}
		if (tempItem4.critChance != 0) {
			hero.critChance+=tempItem4.critChance;
		}
		if(tempItem4.hpRegen !=0) {
			hero.hpRegen+=tempItem4.hpRegen;
		}
		if(tempItem4.manaRegen !=0) {
			hero.manaRegen+=tempItem4.manaRegen;
		}
		if(hero.heroClass.equals("Giant")) {
			model.addAttribute("spell","Earth Shock");
			hero.hp+=hero.mana*2;
			hero.maxHp+=hero.maxMana*2;
			hero.hpRegen+=hero.manaRegen*2;
			hero.mana=0;
			hero.manaRegen=0;
			hero.maxMana=0;
		}
		
		Cookie c = hero.createCookie();
		model.addAttribute("resource", resourceCookie);
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		model.addAttribute("message", hero.createDisplayText());
		return "shopped";
	}
	
	@RequestMapping(value="/item5",method=RequestMethod.GET)
	String theItem5(ModelMap model,HttpServletResponse response,@CookieValue("resource")String resourceCookie)
	{
		if(hero.gold-tempItem5.costsGold>=0) {
			hero.gold-=tempItem5.costsGold;
		} else {
			Cookie c = hero.createCookie();
			model.addAttribute("resource",resourceCookie);
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("notEnoughGold","Not enough Gold for");
			return "shopped";
		}
		if (tempItem5.healthLimit != 0) {
			hero.maxHp += tempItem5.healthLimit;
		}
		if (tempItem5.currentHealth!=0) {
			if(hero.hp + tempItem5.currentHealth < hero.maxHp) {
				hero.hp += tempItem5.currentHealth;
			} else {
				hero.hp = hero.maxHp;
			}
		}
		if(tempItem5.maxMana !=0)
		{
			hero.maxMana+=tempItem5.maxMana;
		}
		if (tempItem5.mana != 0) {
			if(hero.heroClass.equals("Giant")) {
				if(hero.hp+tempItem5.mana*2<=hero.maxHp) {
				hero.hp+=tempItem5.mana*2;
				}else {
					hero.hp=hero.maxHp;
				}
			}
			else if (hero.mana + tempItem5.mana < hero.maxMana) {
				hero.mana += tempItem5.mana;
			} else {
				hero.mana = hero.maxMana;
			}
		}
		if (tempItem5.attackMin != 0) {
			if(hero.attackMin+tempItem5.attackMin<=hero.attackMax) {
				hero.attackMin += tempItem5.attackMin;
				}else {
					hero.attackMin=hero.attackMax;
				}
		}
		if (tempItem5.attackMax != 0) {
			hero.attackMax += tempItem5.attackMax;
		}
		if (tempItem5.armor != 0) {
			hero.armor += tempItem5.armor;
		}
		if (tempItem5.magicResist != 0) {
			hero.magicResist += tempItem5.magicResist;
		}
		if (tempItem5.critChance != 0) {
			hero.critChance+=tempItem5.critChance;
		}
		if(tempItem5.hpRegen !=0) {
			hero.hpRegen+=tempItem5.hpRegen;
		}
		if(tempItem5.manaRegen !=0) {
			hero.manaRegen+=tempItem5.manaRegen;
		}
		if(hero.heroClass.equals("Giant")) {
			model.addAttribute("spell","Earth Shock");
			hero.hp+=hero.mana*2;
			hero.maxHp+=hero.maxMana*2;
			hero.hpRegen+=hero.manaRegen*2;
			hero.mana=0;
			hero.manaRegen=0;
			hero.maxMana=0;
		}
		
		Cookie c = hero.createCookie();
		model.addAttribute("resource", resourceCookie);
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		model.addAttribute("message", hero.createDisplayText());
		return "shopped";
	}
	
	
}