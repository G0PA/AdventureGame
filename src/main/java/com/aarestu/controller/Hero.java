package com.aarestu.controller;

import java.util.ArrayList;

import javax.enterprise.inject.Model;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;

public class Hero {
	String heroClass="";
	int hp=100;
	int maxHp = 100;
	int mana=100;
	int maxMana=100;
	int attackMin=13;
	int attackMax=16;
	int armor=4;
	int magicResist=4;
	int gold = 10;
	int critChance = 5;
	int enemyEncountersLeft=26;
	int hpRegen=2;
	int manaRegen=2;
	int souls=0;
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
			if(hero.heroClass.equals("Necromancer")){
				hero.souls=Integer.parseInt(heroArr[15]);
			}
			
			return hero;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Cookie createCookie() {
		if (heroClass.equals("Necromancer")) {
			return new Cookie("hero",
					String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s,%d", heroClass, hp, maxHp, mana,
							maxMana, attackMin, attackMax, armor, magicResist, gold, critChance, enemyEncountersLeft,
							hpRegen, manaRegen, zone,souls));
		} else {
			return new Cookie("hero",
					String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s", heroClass, hp, maxHp, mana, maxMana,
							attackMin, attackMax, armor, magicResist, gold, critChance, enemyEncountersLeft, hpRegen,
							manaRegen, zone));
		}
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
		}else if(heroClass.equals("Giant")) {
			return  "class = " +heroClass+
					",   health = " + hp +"/" + maxHp +" (+"+hpRegen+" regen)"+  
					",   attack = " + attackMin+ "-" + attackMax+
					",   armor = " + armor +
					",   magic resist = " + magicResist + 
					",   gold = " + gold +
					",   critical chance = " + critChance + "%" +
					",   Enemy encounters left until Boss: " + enemyEncountersLeft+
					",   Current Zone: "+zone;
			}else if(heroClass.equals("Necromancer")) {
				return  "class = " +heroClass+
						",   health = " + hp +"/" + maxHp +" (+"+hpRegen+" regen)"+  
						",   mana = "+mana+"/"+maxMana+" (+"+manaRegen+" regen)"+ 
						",   attack = " + (attackMin+souls/2)+"-"+(attackMax+souls/2)+
						",   armor = " + armor +
						",   magic resist = " + magicResist + 
						",   gold = " + gold +
						",   critical chance = " + critChance + "%" +
						",   Enemy encounters left until Boss: " + enemyEncountersLeft+
						",   Souls: "+souls+
						",   Current Zone: "+zone;
				}
		
		else {
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
	int rangerSightBonusDamageMax=0;
	int rangerSightBonusDamageMin=0;
	int berserkPassiveDamageIncrease=0;
	int necromancerPassiveDamageMin=0;
	int necromancerPassiveDamageMax=0;
	int defense=0;
	int berserkCritical=0;
	int theHeroDamage=0;
	int bloodlustBonusDamageMin=0;
	int bloodlustBonusDamageMax=0;
	public Enemy heroAttack(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response) {

		if(hero.heroClass.equals("Mage"))
		{
			hero.attackMin+=hero.magicResist/2;
			hero.attackMax+=hero.magicResist/2;
		}else if(hero.heroClass.equals("Warrior"))
		{
			hero.attackMin+=hero.armor/2;
			hero.attackMax+=hero.armor/2;
		}
		if(hero.heroClass.equals("Ranger"))
		{
			model.addAttribute("yourPetAttacks","Your pet attacks");
			model.addAttribute("dealing"," dealing ");
			int petDamageMin;
			int petDamageMax;
			if(hero.rangerSightBonusDamageMax!=0 && hero.rangerSightBonusDamageMin!=0)
			{
				 petDamageMax=(int)((hero.attackMax+hero.rangerSightBonusDamageMax)*0.15);
				 petDamageMin=(int)((hero.attackMin+hero.rangerSightBonusDamageMin)*0.15);
			}else {
				 petDamageMin=(int)(hero.attackMin*0.15);
				 petDamageMax=(int)(hero.attackMax*0.15);
			}
			
			int tempEnemyH=enemy.health;
			boolean petCrit=Utils.critical(hero.critChance);
			if (petCrit) {
				model.addAttribute("petCritically"," CRITICALLY");
				enemy.health-=Utils.attack(petDamageMin,petDamageMax)*1.8;
				tempEnemyH-=enemy.health;
				model.addAttribute("petDamage",String.valueOf(tempEnemyH)+" Damage");
			}else {
				
				enemy.health-=Utils.attack(petDamageMin,petDamageMax);
				tempEnemyH-=enemy.health;
				model.addAttribute("petDamage",String.valueOf(tempEnemyH)+" Damage");
			}
			
		}
		if (hero.heroClass.equals("Berserk")) {		
			hero.berserkPassiveDamageIncrease = (hero.maxHp - hero.hp) / 25;
			
		}
		if(hero.heroClass.equals("Necromancer")) {
			hero.necromancerPassiveDamageMin=hero.souls/2;
			hero.necromancerPassiveDamageMax=hero.souls/2;
			
		}
		if(enemy.attackType==1) {
			defense=hero.armor;
		} else {
			defense=hero.magicResist;
		}
		int tempEnemyHealth=enemy.health;
		boolean crit=Utils.critical(hero.critChance+hero.berserkCritical);
		double multiply=1;
		if(crit==true) {
			model.addAttribute("critically","CRITICALLY ");
			multiply=1.8;
		} else {
			model.addAttribute("critically","");
		}
		int theHeroDamage;
		theHeroDamage=(int)(Utils.attack(hero.attackMin+hero.rangerSightBonusDamageMin+hero.berserkPassiveDamageIncrease+hero.bloodlustBonusDamageMin+hero.necromancerPassiveDamageMin,hero.attackMax+hero.rangerSightBonusDamageMax+hero.berserkPassiveDamageIncrease+hero.bloodlustBonusDamageMax+hero.necromancerPassiveDamageMax)*multiply) - enemy.armor;
		if(theHeroDamage<=0) {	
		enemy.health=enemy.health-1;
		} else {
			enemy.health-=theHeroDamage;
		}
		rangerSightBonusDamageMin=0;
		rangerSightBonusDamageMax=0;
		if (enemy.health <= 0) {
			hero.gold += enemy.dropsGold;
			if(hero.heroClass.equals("Mage"))
			{
				hero.attackMin-=hero.magicResist/2;
				hero.attackMax-=hero.magicResist/2;
			}
			else if(hero.heroClass.equals("Warrior"))
			{
				hero.attackMin-=hero.armor/2;
				hero.attackMax-=hero.armor/2;
			}
			if(hero.hp+hero.hpRegen<=hero.maxHp) {
			hero.hp+=hero.hpRegen;
		}else {
			hero.hp=hero.maxHp;
		}if(hero.mana+hero.manaRegen<=hero.maxMana) {
			hero.mana+=hero.manaRegen;
		}else {
			hero.mana=hero.maxMana;
		}
			if(hero.heroClass.equals("Necromancer")) {
				if(Utils.critical(66)) {
					model.addAttribute("soul","You steal the enemy's Soul");
				hero.souls++;
				}
			}
			model.addAttribute("hpRegen",hero.hpRegen);
			model.addAttribute("manaRegen",hero.manaRegen);
			hero.berserkPassiveDamageIncrease=0;
			hero.bloodlustBonusDamageMin=0;
			hero.bloodlustBonusDamageMax=0;
			hero.berserkCritical=0;
			hero.rangerSightBonusDamageMin=0;
			hero.rangerSightBonusDamageMax=0;
			hero.necromancerPassiveDamageMin=0;
			hero.necromancerPassiveDamageMax=0;
			Cookie c = hero.createCookie();

			c.setPath("/");
			c.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(c);
			model.addAttribute("gold",String.valueOf(enemy.dropsGold));
			Cookie bossState=new Cookie("bossState","dead");
			bossState.setPath("/");
			bossState.setMaxAge(60*60*24*2);
			response.addCookie(bossState);
			if(hero.zone.equals("Green Woods")) {
				model.addAttribute("zone","hello");
			}else if(hero.zone.equals("Red Woods")) {
				model.addAttribute("zone","redWoods");
			}
		}
		if(hero.heroClass.equals("Mage"))
		{
			hero.attackMin-=hero.magicResist/2;
			hero.attackMax-=hero.magicResist/2;
		}
		else if(hero.heroClass.equals("Warrior"))
		{
			hero.attackMin-=hero.armor/2;
			hero.attackMax-=hero.armor/2;
		}
		int damageDealt = tempEnemyHealth-enemy.health;
		model.addAttribute("damageDealt", String.valueOf(damageDealt));
		model.addAttribute("message2", hero.createDisplayText());
		return enemy;
	}
	public String heroDefeat(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response,int tempEnemyHealth, int tempHealth) {
		ArrayList<String> images=new ArrayList<String>();
		
		images.add("defeat");
		images.add("defeat2");
		images.add("defeat3");
		images.add("defeat4");
		images.add("defeat5");
		images.add("defeat6");
		images.add("defeat7");
		
		int theIndex=Utils.attack(0,images.size()-1);
		model.addAttribute("defeatScreen",images.get(theIndex));
		int damageDealt = tempEnemyHealth-enemy.health;
		int enemyDamage=tempHealth-hero.hp;
		model.addAttribute("message2", hero.createDisplayText());
		model.addAttribute("damageDealt", String.valueOf(damageDealt));
		model.addAttribute("enemy", String.valueOf(enemy.health));
		model.addAttribute("enemyName",enemy.name);
		model.addAttribute("enemyDamage",String.valueOf(enemyDamage));
		Cookie enem=new Cookie("enemy","greshka");
		enem.setPath("/");
		enem.setMaxAge(60*60*24*2);
		response.addCookie(enem);
		return "defeat";
	}
	public Enemy mageFireball(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response,String spellCastCookie) {
		hero.mana -= 30;
		String critically = "";
		int currentEnemyHealth = enemy.health;
		if (Utils.critical(hero.critChance)) {
			enemy.health -= (hero.maxMana * 0.25) * 1.8;
			critically = " CRITICALLY";

		} else {
			enemy.health -= hero.maxMana * 0.25;
		}
		currentEnemyHealth = currentEnemyHealth - enemy.health;
		model.addAttribute("spellDamage", "You cast Fireball" + critically + " Damaging the enemy for "
				+ String.valueOf(currentEnemyHealth) + " Damage");
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		response.addCookie(leHeroCookie);
		model.addAttribute("gold",String.valueOf(enemy.dropsGold));
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
		return enemy;
	}
	public void magePortal(Hero hero, ModelMap model, HttpServletResponse response) {
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
		}else {
			model.addAttribute("zone","redWoods");
		}
		hero.mana -=30;
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(leHeroCookie);
		Cookie bossState=new Cookie("bossState","dead");
		bossState.setPath("/");
		bossState.setMaxAge(60*60*24*2);
		response.addCookie(bossState);
		model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
		model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
		model.addAttribute("spellDamage","You create a portal and escape the enemy");
		if(hero.hp+hero.hpRegen<=hero.maxHp) {
			hero.hp+=hero.hpRegen;
		}else {
			hero.hp=hero.maxHp;
		}
		if(hero.mana+hero.manaRegen<=hero.maxMana) {
			hero.mana+=hero.manaRegen;
		}else {
			hero.mana=hero.maxMana;
		}
		model.addAttribute("message2",hero.createDisplayText());
	}
	public Enemy mageFreezingTouch(Hero hero,Enemy enemy, ModelMap model, HttpServletResponse response,String spellCastCookie) {
		hero.mana-=40;
		enemy.health-=hero.maxMana*0.15;
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		Cookie enemyCookie=new Cookie("enemy",enemy.toCookie());
		enemyCookie.setPath("/");
		enemyCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(enemyCookie);
		response.addCookie(leHeroCookie);
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		model.addAttribute("spellDamage","You cast Freezing Touch stunning the enemy and dealing "+String.valueOf((int)(hero.maxMana*0.15))+" damage");
		model.addAttribute("enemy",enemy.health);
		model.addAttribute("gold",String.valueOf(enemy.dropsGold));
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
		return enemy;
	}
	public Enemy mageMagicAffinity(Hero hero,Enemy enemy, ModelMap model, HttpServletResponse response,String spellCastCookie) {
		hero.mana-=50;
		int damageDealt=(int)((hero.maxMana*0.30)+(hero.maxMana*0.30*((hero.magicResist*1.5)/100)));
		String critically="";
		if(Utils.critical((hero.critChance+hero.magicResist))) {
			damageDealt=(int)(damageDealt*1.8);
			critically=" CRITICALLY";
		}
		enemy.health-=damageDealt;
		model.addAttribute("spellDamage", "You cast Magic affinity"+critically+" damaging the enemy for "+String.valueOf(damageDealt));
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		model.addAttribute("enemy",enemy.health);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		Cookie enemyCookie=new Cookie("enemy",enemy.toCookie());
		enemyCookie.setPath("/");
		enemyCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(enemyCookie);
		response.addCookie(leHeroCookie);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		model.addAttribute("gold",String.valueOf(enemy.dropsGold));
		if(hero.zone.equals("Green Woods")) {
		model.addAttribute("zone","hello");
		}else {
			model.addAttribute("zone","redWoods");
		}
		return enemy;
		
	}
	
	public Enemy berserkAxeThrow(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response,String spellCastCookie) {
		hero.mana -= 30;
		String critically = "";
		int currentEnemyHealth = enemy.health;
		if (Utils.critical(hero.critChance*2)) {
			enemy.health -= (hero.critChance *3) * 1.8;
			critically = " CRITICALLY";

		} else {
			enemy.health -= hero.critChance*3;
		}
		currentEnemyHealth = currentEnemyHealth - enemy.health;
		model.addAttribute("spellDamage", "You cast Axe Throw" + critically + " Damaging the enemy for "
				+ String.valueOf(currentEnemyHealth) + " Damage");
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		response.addCookie(leHeroCookie);
		model.addAttribute("gold",String.valueOf(enemy.dropsGold));
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
		return enemy;
	}
	
	public Enemy berserkVigorStrike(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response,String spellCastCookie) {
		hero.mana -= 40;
		String critically = "";
		int currentEnemyHealth = enemy.health;
		enemy=hero.heroAttack(hero, enemy, model, response);
		int healthStolen=((hero.maxHp-hero.hp)/40)*5;
		currentEnemyHealth = currentEnemyHealth - enemy.health;
		enemy.health-=healthStolen;
		if(hero.hp+healthStolen<=hero.maxHp) {
			hero.hp+=healthStolen;
		}else {
			hero.hp=hero.maxHp;
		}
		model.addAttribute("spellDamage", "You cast Vigor Strike" + critically + " Damaging the enemy for "
				+ String.valueOf(currentEnemyHealth) + " Damage and stealing "+String.valueOf(healthStolen)+" health from them");
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		response.addCookie(leHeroCookie);
		model.addAttribute("gold",String.valueOf(enemy.dropsGold));
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
		return enemy;
	}
	public Enemy berserkEnrage(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response,String spellCastCookie) {
		hero.mana -= 50;
		
		int currentEnemyHealth = enemy.health;
		String firstHit="";
		String secondHit="";
		String thirdHit="";
		String fourthHit="";
		int tempHeroHp=hero.hp;
		hero.hp=(int)(hero.hp*0.75);
		tempHeroHp=tempHeroHp-hero.hp;
		for(int i=0; i<1; i++) {
		if(Utils.critical(50+hero.armor+hero.magicResist)) {
			enemy=hero.heroAttack(hero, enemy, model, response);
			int firstDamage=currentEnemyHealth-enemy.health;
			currentEnemyHealth=enemy.health;
			firstHit="Your first attack hits the enemy dealing "+String.valueOf(firstDamage)+" damage. ";
		}else {
			firstHit="Your first attack misses";
			
		} 
		if(enemy.health<=0) {
			break;
		}
		if(Utils.critical(50+hero.armor+hero.magicResist)) {
			enemy=hero.heroAttack(hero, enemy, model, response);
			int secondDamage=currentEnemyHealth-enemy.health;
			currentEnemyHealth=enemy.health;
			secondHit="Your second attack hits the enemy dealing "+String.valueOf(secondDamage)+" damage. ";
		}else {
			secondHit="Your second attack misses";
		}
		if(enemy.health<=0) {
			break;
		}
		if(Utils.critical(50+hero.armor+hero.magicResist)) {
			enemy=hero.heroAttack(hero, enemy, model, response);
			int thirdDamage=currentEnemyHealth-enemy.health;
			currentEnemyHealth=enemy.health;
			thirdHit="Your third attack hits the enemy dealing "+String.valueOf(thirdDamage)+" damage. ";
		}else {
			thirdHit="Your third attack misses";
		}
		if(enemy.health<=0) {
			break;
		}
		if(Utils.critical(50+hero.armor+hero.magicResist)) {
			enemy=hero.heroAttack(hero, enemy, model, response);
			int fourthDamage=currentEnemyHealth-enemy.health;
			currentEnemyHealth=enemy.health;
			fourthHit="Your fourth attack hits the enemy dealing "+String.valueOf(fourthDamage)+" damage. ";
		}else {
			fourthHit="Your fourth attack misses";
		}
		if(enemy.health<=0) {
			break;
		}
		}
		model.addAttribute("hit1",firstHit);
		model.addAttribute("hit2",secondHit);
		model.addAttribute("hit3",thirdHit);
		model.addAttribute("hit4",fourthHit);
		model.addAttribute("spellDamage", "You cast Enrage damaging yourself for " +tempHeroHp+" losing all your defenses and attacking the enemy 4 times .");
				
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		response.addCookie(leHeroCookie);
		model.addAttribute("gold",String.valueOf(enemy.dropsGold));
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
		return enemy;
	}
	
	public Enemy rangerFrostArrow(Hero hero,Enemy enemy, ModelMap model, HttpServletResponse response,String spellCastCookie) {
		hero.mana-=40;
		int damage=(int)(hero.attackMax*0.65);
		String critically="";
		if(Utils.critical(hero.critChance)) {
			damage=(int)(damage*1.8);
			critically=" CRITICALLY";
		}
		enemy.health-=hero.attackMax*0.65;
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		Cookie enemyCookie=new Cookie("enemy",enemy.toCookie());
		enemyCookie.setPath("/");
		enemyCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(enemyCookie);
		response.addCookie(leHeroCookie);
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		model.addAttribute("spellDamage","You cast Frost Arrow stunning the enemy and"+critically+" damaging him for "+String.valueOf(damage));
		model.addAttribute("enemy",enemy.health);
		model.addAttribute("gold",String.valueOf(enemy.dropsGold));
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
		return enemy;
	}
	public Enemy warriorEndurance(Hero hero, Enemy enemy,ModelMap model,HttpServletResponse response) {
		hero.mana -= 20;
		int currentEnemyHealth = enemy.health;
		enemy.health -= (hero.maxHp - hero.hp) * 0.10;
		currentEnemyHealth = currentEnemyHealth - enemy.health;
		hero.hp += currentEnemyHealth;
		model.addAttribute("spellDamage", "You cast Endurance Damaging the enemy for "
				+ String.valueOf(currentEnemyHealth) + " and healing yourself for that amount.");
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(leHeroCookie);
		return enemy;
	}
	public Enemy giantEarthShock(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response) {
		int healthLost=(int)(hero.hp*0.25);
		int earthShockCritChance=healthLost+hero.critChance;
		int earthShockDamage=(int)(hero.maxHp*0.10);
		hero.hp-=healthLost;
		String critically="";
		if (Utils.critical(earthShockCritChance)) {
			earthShockDamage=(int)(earthShockDamage*1.8);
			enemy.health -=earthShockDamage;
			critically = " CRITICALLY";

		} else {
			enemy.health -= earthShockDamage;
		}
		model.addAttribute("spellDamage", "You cast Earth Shock damaging yourself for "+String.valueOf(healthLost)+" and"+critically+" damaging the enemy for "+String.valueOf(earthShockDamage)+" damage");
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(leHeroCookie);
		return enemy;
	}
	public Enemy necromancerSiphonLife(Hero hero, Enemy enemy, ModelMap model, HttpServletResponse response) {
		hero.mana -= 20;
		String critically = "";
		int currentEnemyHealth = enemy.health;
		if (Utils.critical(hero.critChance)) {
			enemy.health -= (hero.maxMana * 0.15+hero.souls) * 1.8;
			critically = " CRITICALLY";

		} else {
			enemy.health -= hero.maxMana * 0.15 + hero.souls;
		}
		int siphonLifeHealing=(int)(hero.maxMana*0.10+hero.souls);
		currentEnemyHealth = currentEnemyHealth - enemy.health;
		hero.hp+=siphonLifeHealing;
		model.addAttribute("spellDamage", "You cast Siphon Life" + critically + " Damaging the enemy for "
				+ String.valueOf(currentEnemyHealth) + " Damage and healing yourself for "+String.valueOf(siphonLifeHealing));
		Cookie leHeroCookie = hero.createCookie();
		leHeroCookie.setPath("/");
		leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(leHeroCookie);
		return enemy;
	}
	public String[] generateHeroSpellText(Hero hero,String spellCastCookie,HttpServletResponse response) {
		ArrayList<String[]> spells=new ArrayList<String[]>();
		if(hero.heroClass.equals("Mage")) {			
			String[] fireball=new String[2];
			fireball[0]="Fireball";
			fireball[1]="Deals damage equal to 25% of your Maximum Mana(Has same Critical Chance as your normal attacks and ignores armor). -30 Mana";
			spells.add(fireball);
			String[] freezingTouch=new String[2];
			freezingTouch[0]="Freezing Touch";
			freezingTouch[1]="Deals damage equal to 15% of your Maximum Mana(Cannot be a Critical, but ignores armor) and Freezing the enemy making him unable to attack this round. -40 Mana";
			spells.add(freezingTouch);
			String[] portal=new String[2];
			portal[0]="Portal";
			portal[1]="Teleports you away from the fight. -30 Mana";
			spells.add(portal);
			String[] magicAffinity=new String[2];
			magicAffinity[0]="Magic Affinity";
			magicAffinity[1]="Deals damage equal to 30% of your Maximum Mana which is further increased by 1.5% for every point of Magic Resist,ignores armor and has your  Critical Strike chance percantage +1% for every point of Magic Resist. -50 Mana";
			spells.add(magicAffinity);
			int theSpell;
		while (true) {
			theSpell = Utils.attack(0, spells.size() - 1);
			if (!spells.get(theSpell)[0].equals(spellCastCookie)) {
				break;
			}
		}
			Cookie spellCast=new Cookie("spellCast",spells.get(theSpell)[0]);
			spellCast.setPath("/");
			spellCast.setMaxAge(60*60*24*2);
			response.addCookie(spellCast);
			return spells.get(theSpell);
		}else if(hero.heroClass.equals("Berserk")){
			String[] bloodlust=new String[2];
			bloodlust[0]="Bloodlust";
			bloodlust[1]="You cast an empowered Attack.The Attack is a Critical and your Attack Damage Min and Max are increased by 1.5% for every point of Critical Strike you have. -40 Mana";
			spells.add(bloodlust);
			String[] axeThrow=new String[2];
			axeThrow[0]="Axe Throw";
			axeThrow[1]="Throw an enchanted axe ignoring armor and dealing damage equal to 3 times your Critical Strike chance.Critical Chance for this ability is two times your Critical Strike chance. -30 Mana";
			spells.add(axeThrow);
			String[] vigorStrike=new String[2];
			vigorStrike[0]="Vigor Strike";
			vigorStrike[1]="Do a normal Attack, but for every 40 Missing Health steal 5 health from the enemy.-40 Mana";
			spells.add(vigorStrike);
			String[] enrage=new String[2];
			enrage[0]="Enrage";
			enrage[1]="Lose all your Armor and Magic resist for this round, aswell as 25% of your current Health.Perform 4 attacks, each one has a 50% chance of hitting the enemy and doing 80% of your Damage Min and Max.Hit chance and Critical Strike Chance for all 4 attacks are increased by 1% for every point of Armor/Magic resist lost.-50 Mana";
			spells.add(enrage);
			int theSpell;
			while (true) {
				theSpell = Utils.attack(0, spells.size() - 1);
				if (!spells.get(theSpell)[0].equals(spellCastCookie)) {
					break;
				}
			}
				Cookie spellCast=new Cookie("spellCast",spells.get(theSpell)[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				return spells.get(theSpell);
		}else {
			String[] rangerSight=new String[2];
			rangerSight[0]="Ranger Sight";
			rangerSight[1]="increases your Attack Damage Min and Max with 80%. -40 Mana";
			spells.add(rangerSight);
			String[] poisonArrow=new String[2];
			poisonArrow[0]="Poison Arrow";
			poisonArrow[1]="Fire a poisonous arrow dealing 30% of your Attack Damage Max every turn and ignoring armor until the fight ends,also reduce the enemy armor by 20% of your Attack Damage Min(Cannot be reduced under 0). -30 Mana";
			spells.add(poisonArrow);
			String[] frostArrow=new String[2];
			frostArrow[0]="Frost Arrow";
			frostArrow[1]="Fire a freezing arrow dealing 65% of your Attack Damage Max and freezing the enemy making him unable to attack this round.Can be a Critical and ignores Armor. -40 Mana";
			spells.add(frostArrow);
			String[] perfectDuo=new String[2];
			perfectDuo[0]="Perfect Duo";
			perfectDuo[1]="Your pet's Attack Damage Min and Max becomes equal to yours for this round and if either you or your pet make a Critical the other one makes a Critical too. -50 Mana";
			spells.add(perfectDuo);
			int theSpell;
			while (true) {
				theSpell = Utils.attack(0, spells.size() - 1);
				if (!spells.get(theSpell)[0].equals(spellCastCookie)) {
					break;
				}
			}
				Cookie spellCast=new Cookie("spellCast",spells.get(theSpell)[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				return spells.get(theSpell);
		}
	}

	public ArrayList<String[]> generateHeroSkillText(Hero hero, HttpServletResponse response) {
		ArrayList<String[]> skills = new ArrayList<String[]>();
		String[] magicDeflection = new String[2];
		magicDeflection[0] = "Magic Deflection";
		magicDeflection[1] = "Reduces your Armor with 50% and adds it to your Magic Resist for this round. Your damage Min and Max become equal to 200% of your Magic Resist.";
		skills.add(magicDeflection);
		String[] armorDeflection = new String[2];
		armorDeflection[0] = "Armor Deflection";
		armorDeflection[1] = "Reduces your Magic Resist with 50% and adds it to your Armor for this round. Your damage Min and Max become equal to 200% of your Armor.";
		skills.add(armorDeflection);
		String[] doubleTrouble = new String[2];
		doubleTrouble[0] = "Double Trouble";
		doubleTrouble[1] = "Attack the enemy twice.Each attack has 30% reduced Damage and 75% hit rate";
		skills.add(doubleTrouble);
		String[] attack = new String[2];
		attack[0] = "Attack";
		attack[1] = "Do a normal attack";
		skills.add(attack);
		String[] empoweredAttack = new String[2];
		empoweredAttack[0] = "Empowered Attack";
		empoweredAttack[1] = "Increases your damage Min and Max with 20% for this round, while reducing both your defenses by 50%";
		skills.add(empoweredAttack);
		String[] luckyHit = new String[2];
		luckyHit[0] = "Lucky hit";
		luckyHit[1] = "Increases your critical chance by 10% for this round, but reduces your damage dealt by 10% aswell.";
		skills.add(luckyHit);
		String[] lifeSteal = new String[2];
		lifeSteal[0] = "Life Steal";
		lifeSteal[1] = "Reduces your damage by 20% for this round, but your attack heals you for 20% of the damage it did";
		skills.add(lifeSteal);
		String[] manaSteal = new String[2];
		manaSteal[0] = "Mana Steal";
		manaSteal[1] = "Reduces your damage by 20% for this round, but your attack restores mana equal to 20% of the damage it did";
		skills.add(manaSteal);
		String[] shieldThrow = new String[2];
		shieldThrow[0] = "Shield Throw";
		shieldThrow[1] = "You lose all defenses for this round.For every point of Magic Resist and Armor lost, increase your Critical Chance by 2%";
		skills.add(shieldThrow);
		String[] dyingBlow = new String[2];
		dyingBlow[0] = "Dying Blow";
		dyingBlow[1] = "Increase your critical Strike Chance by 25% for your next Attack, but reduces your Current Health by 50%";
		skills.add(dyingBlow);
		int theSpell;
		int firstSkillIndex;
		ArrayList<String[]> chosenSkills = new ArrayList<String[]>();
		firstSkillIndex = Utils.attack(0, skills.size() - 1);
		chosenSkills.add(skills.get(firstSkillIndex));
		while (true) {
			theSpell = Utils.attack(0, skills.size() - 1);
			if (theSpell == firstSkillIndex) {
				continue;
			} else {
				chosenSkills.add(skills.get(theSpell));
				break;
			}
		}
		Cookie skillsCookie=new Cookie("skills",chosenSkills.get(0)[0]+","+chosenSkills.get(1)[0]);
		skillsCookie.setPath("/");
		skillsCookie.setMaxAge(60*60*24*2);
		response.addCookie(skillsCookie);
		return chosenSkills;
	}
}
