package com.aarestu.controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Random;
@Controller
public class FightController {
	String theBadCookie="";
	String resource="";
	Hero hero;
	Enemy enemy;
	int rangerSightBonusDamageMin=0;
	int rangerSightBonusDamageMax=0;
	int bloodlustBonusDamageMin=0;
	int bloodlustBonusDamageMax=0;
	int berserkPassiveDamageIncrease=0;
	int berserkCritical=0;
	int necromancerPassiveDamageMin=0;
	int necromancerPassiveDamageMax=0;
	final static Logger logger=Logger.getLogger(FightController.class);
	@RequestMapping(value="/fight",method = RequestMethod.GET)
	public String fight(ModelMap model, @CookieValue("hero") String fooCookie, @CookieValue(value="enemy",defaultValue="-1001") String badCookie,@CookieValue(value="resource",defaultValue="-1001") String resourceCookie,@CookieValue(value="bossState") String bossStateCookie,
			HttpServletResponse response) {
		if(bossStateCookie.equals("dead")) {
			model.addAttribute("cheating","No cheating : )");
			model.addAttribute("resource", resourceCookie);
			Hero hero=Hero.fromCookie(fooCookie);
			if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else {
				model.addAttribute("zone","redWoods");
			}
			return "fightvictory";
		}
		resource=resourceCookie;
		logger.debug("the bad Cookie is: "+badCookie);
		hero = Hero.fromCookie(fooCookie);
		if (hero == null) {
			return "defeat";
		}
		model.addAttribute("resource",resourceCookie);
		logger.debug("enemy123:  "+badCookie);
		enemy = Enemy.fromCookie(badCookie);

		String fightOutcome=fight(enemy.health,enemy.attackType,enemy.damageMin,enemy.damageMax,enemy.armor,enemy.dropsGold,enemy.critChance,0,0,response,model,hero);
		if(fightOutcome.equals("nobodyDied"))
		{
			return "fight";
		}
		return fightOutcome;
	}

	
	int attack(int min, int max) {
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	boolean critical(int critChance) {
		return Math.random() * 100 < critChance;
	}

	
	String fight(int enemyHealth,int attackType, int enemyAttackMin, int enemyAttackMax, 
			int enemyArmor, int dropsGold, int enemyCritChance,int rangerSightBonusDamageMin,int rangerSightBonusDamageMax, HttpServletResponse response, ModelMap model,Hero hero) {
		int defense;
		resource=enemy.name;
		if(hero.heroClass.equals("Mage"))
		{
			hero.attackMin+=hero.magicResist/2;
			hero.attackMax+=hero.magicResist/2;
			model.addAttribute("spell","Fireball");
		}else if(hero.heroClass.equals("Warrior"))
		{
			hero.attackMin+=hero.armor/2;
			hero.attackMax+=hero.armor/2;
			model.addAttribute("spell","Endurance");
		}
		if(hero.heroClass.equals("Ranger"))
		{
			model.addAttribute("yourPetAttacks","Your pet attacks");
			model.addAttribute("dealing"," dealing ");
			model.addAttribute("spell","Ranger Sight");
			int petDamageMin;
			int petDamageMax;
			if(rangerSightBonusDamageMax!=0 && rangerSightBonusDamageMin!=0)
			{
				 petDamageMax=(int)((hero.attackMax+rangerSightBonusDamageMax)*0.15);
				 petDamageMin=(int)((hero.attackMin+rangerSightBonusDamageMin)*0.15);
			}else {
				 petDamageMin=(int)(hero.attackMin*0.15);
				 petDamageMax=(int)(hero.attackMax*0.15);
			}
			
			int tempEnemyH=enemyHealth;
			boolean petCrit=critical(hero.critChance);
			if (petCrit) {
				model.addAttribute("petCritically"," CRITICALLY");
				enemyHealth-=attack(petDamageMin,petDamageMax)*1.8;
				tempEnemyH-=enemyHealth;
				model.addAttribute("petDamage",String.valueOf(tempEnemyH)+" Damage");
			}else {
				
				enemyHealth-=attack(petDamageMin,petDamageMax);
				tempEnemyH-=enemyHealth;
				model.addAttribute("petDamage",String.valueOf(tempEnemyH)+" Damage");
			}
			
		}
		if (hero.heroClass.equals("Berserk")) {
			model.addAttribute("spell", "Bloodlust");
			
				berserkPassiveDamageIncrease = (hero.maxHp - hero.hp) / 25;
			
		}
		if(hero.heroClass.equals("Giant")) {
			model.addAttribute("spell","Earth Shock");
		}
		if(hero.heroClass.equals("Necromancer")) {
			model.addAttribute("spell","Siphon Life");
			necromancerPassiveDamageMin=hero.souls/2;
			necromancerPassiveDamageMax=hero.souls/2;
			
		}
		if(attackType==1) {
			defense=hero.armor;
		} else {
			defense=hero.magicResist;
		}
		int tempEnemyHealth=enemyHealth;
		boolean crit=critical(hero.critChance+berserkCritical);
		double multiply=1;
		if(crit==true) {
			model.addAttribute("critically","CRITICALLY ");
			multiply=1.8;
		} else {
			model.addAttribute("critically","");
		}
		logger.debug("berserk bonus damage is :"+berserkPassiveDamageIncrease);
		logger.debug("BLoodlust bonus damage min and max are: "+bloodlustBonusDamageMin+"-"+bloodlustBonusDamageMax);
		logger.debug("Damage Min is: "+hero.attackMin);
		logger.debug("Damage max is:" +hero.attackMax);
		int theHeroDamage;
		theHeroDamage=(int)(attack(hero.attackMin+rangerSightBonusDamageMin+berserkPassiveDamageIncrease+bloodlustBonusDamageMin+necromancerPassiveDamageMin,hero.attackMax+rangerSightBonusDamageMax+berserkPassiveDamageIncrease+bloodlustBonusDamageMax+necromancerPassiveDamageMax)*multiply) - enemyArmor;
		if(theHeroDamage<=0) {	
		enemyHealth=enemyHealth-1;
		} else {
			enemyHealth-=theHeroDamage;
		}
		rangerSightBonusDamageMin=0;
		rangerSightBonusDamageMax=0;
		if (enemyHealth <= 0) {
			hero.gold += dropsGold;
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
				if(critical(66)) {
					model.addAttribute("soul","You steal the enemy's Soul");
				hero.souls++;
				}
			}
			model.addAttribute("hpRegen",hero.hpRegen);
			model.addAttribute("manaRegen",hero.manaRegen);
			Cookie c = hero.createCookie();

			c.setPath("/");
			c.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(c);
			model.addAttribute("gold",String.valueOf(dropsGold));
			Cookie bossState=new Cookie("bossState","dead");
			bossState.setPath("/");
			bossState.setMaxAge(60*60*24*2);
			response.addCookie(bossState);
			berserkPassiveDamageIncrease=0;
			bloodlustBonusDamageMin=0;
			bloodlustBonusDamageMax=0;
			berserkCritical=0;
			rangerSightBonusDamageMin=0;
			rangerSightBonusDamageMax=0;
			necromancerPassiveDamageMin=0;
			necromancerPassiveDamageMax=0;
			int damageDealt = tempEnemyHealth-enemyHealth;
			model.addAttribute("damageDealt", String.valueOf(damageDealt));
			model.addAttribute("message2", hero.createDisplayText());
			if(hero.zone.equals("Green Woods")) {
				model.addAttribute("zone","hello");
			}else if(hero.zone.equals("Red Woods")) {
				model.addAttribute("zone","redWoods");
			}
			return "fightvictory";
		}
		int tempHealth=hero.hp;
		crit=critical(enemyCritChance);
		multiply=1;
		if(crit==true) {
			model.addAttribute("enemyCritically","CRITICALLY ");
			multiply=1.8;
		} else {
			model.addAttribute("enemyCritically","");
		}
		int theEnemyDamage;
		theEnemyDamage=(int)(attack(enemyAttackMin,enemyAttackMax)*multiply) - defense; 
		if(theEnemyDamage<=0) {
			hero.hp = hero.hp - 1;
		}
		 else {
			hero.hp-=theEnemyDamage;
		}
		if (hero.hp <= 0) {
			Cookie c = hero.createCookie();

			c.setPath("/");
			c.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(c);
			ArrayList<String> images=new ArrayList<String>();
			
			images.add("defeat");
			images.add("defeat2");
			images.add("defeat3");
			images.add("defeat4");
			images.add("defeat5");
			images.add("defeat6");
			images.add("defeat7");
			
			int theIndex=attack(0,images.size()-1);
			model.addAttribute("defeatScreen",images.get(theIndex));
			Cookie enem=new Cookie("enemy","greshka");
			enem.setPath("/");
			enem.setMaxAge(60*60*24*2);
			response.addCookie(enem);
			berserkCritical=0;
			berserkPassiveDamageIncrease=0;
			bloodlustBonusDamageMin=0;
			bloodlustBonusDamageMax=0;
			rangerSightBonusDamageMin=0;
			rangerSightBonusDamageMax=0;
			int damageDealt = tempEnemyHealth-enemyHealth;
			int enemyDamage=tempHealth-hero.hp;
			model.addAttribute("message2", hero.createDisplayText());
			model.addAttribute("damageDealt", String.valueOf(damageDealt));
			model.addAttribute("enemy", String.valueOf(enemyHealth));
			model.addAttribute("enemyName",resource);
			model.addAttribute("enemyDamage",String.valueOf(enemyDamage));
			return "defeat";

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
		berserkPassiveDamageIncrease=0;
		bloodlustBonusDamageMin=0;
		bloodlustBonusDamageMax=0;
		berserkCritical=0;
		rangerSightBonusDamageMin=0;
		rangerSightBonusDamageMax=0;
		necromancerPassiveDamageMin=0;
		necromancerPassiveDamageMax=0;
		Cookie c = hero.createCookie();
		enemy.health=enemyHealth;
		theBadCookie=enemy.toCookie();
		Cookie e = new Cookie("enemy", theBadCookie);
		e.setMaxAge(60 * 60 * 24 * 2);
		e.setPath("/");
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		response.addCookie(e);
		int damageDealt = tempEnemyHealth-enemyHealth;
		int enemyDamage=tempHealth-hero.hp;
		model.addAttribute("message2", hero.createDisplayText());
		model.addAttribute("damageDealt", String.valueOf(damageDealt));
		model.addAttribute("enemy", String.valueOf(enemy.health));
		model.addAttribute("enemyName",resource);
		model.addAttribute("enemyDamage",String.valueOf(enemyDamage));
		return "nobodyDied";
	}
	
	@RequestMapping(value="/fightWithSpell",method=RequestMethod.GET)
	 String fightWithSpell(ModelMap model, HttpServletResponse response,@CookieValue("enemy")String enemyCookie,@CookieValue("resource")String resourceCookie,@CookieValue("hero")String heroCookie)
	{
		Hero hero=Hero.fromCookie(heroCookie);
		model.addAttribute("resource",resourceCookie);
		if (hero.heroClass.equals("Mage")) {
			if (hero.mana - 20 < 0) {
				model.addAttribute("message", hero.createDisplayText());
				return "noMana";

			}
			hero.mana -= 20;
			String critically = "";
			enemy = Enemy.fromCookie(enemyCookie);
			int currentEnemyHealth = enemy.health;
			if (critical(hero.critChance)) {
				enemy.health -= (hero.maxMana * 0.30) * 1.8;
				critically = " CRITICALLY";

			} else {
				enemy.health -= hero.maxMana * 0.30;
			}
			currentEnemyHealth = currentEnemyHealth - enemy.health;
			model.addAttribute("spellDamage", "You cast Fireball" + critically + " Damaging the enemy for "
					+ String.valueOf(currentEnemyHealth) + " Damage");
			Cookie leHeroCookie = hero.createCookie();
			leHeroCookie.setPath("/");
			leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(leHeroCookie);
			String fightOutcome = fight(enemy.health, enemy.attackType, enemy.damageMin, enemy.damageMax, enemy.armor,
					enemy.dropsGold, enemy.critChance,0,0, response, model, hero);
			if (fightOutcome.equals("nobodyDied")) {
				return "fight";
			}
			return fightOutcome;
		}
		if (hero.heroClass.equals("Warrior")) {
			if (hero.mana - 20 < 0) {
				model.addAttribute("message", hero.createDisplayText());
				return "noMana";

			}
			hero.mana -= 20;
			enemy = Enemy.fromCookie(enemyCookie);
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
			String fightOutcome = fight(enemy.health, enemy.attackType, enemy.damageMin, enemy.damageMax, enemy.armor,
					enemy.dropsGold, enemy.critChance,0,0, response, model, hero);
			if (fightOutcome.equals("nobodyDied")) {
				return "fight";
			}
			return fightOutcome;
		}
		if (hero.heroClass.equals("Ranger")) {
			if (hero.mana - 20 < 0) {
				model.addAttribute("message", hero.createDisplayText());
				return "noMana";

			}
			hero.mana -= 20;
			enemy = Enemy.fromCookie(enemyCookie);
			//int currentEnemyHealth = enemy.health;
			//enemy.health -= (hero.maxHp - hero.hp) * 0.10;
			//currentEnemyHealth = currentEnemyHealth - enemy.health;
			//hero.hp += currentEnemyHealth;
			rangerSightBonusDamageMin=(int)(hero.attackMin*0.80);
			rangerSightBonusDamageMax=(int)(hero.attackMax*0.80);
			model.addAttribute("spellDamage", "You cast Ranger Sight increasing your Damage to "+String.valueOf(hero.attackMin+rangerSightBonusDamageMin)+"-"
					+ String.valueOf(hero.attackMax+rangerSightBonusDamageMax));
			Cookie leHeroCookie = hero.createCookie();
			leHeroCookie.setPath("/");
			leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(leHeroCookie);
			String fightOutcome = fight(enemy.health, enemy.attackType, enemy.damageMin, enemy.damageMax, enemy.armor,
					enemy.dropsGold, enemy.critChance,rangerSightBonusDamageMin,rangerSightBonusDamageMax, response, model, hero);
			if (fightOutcome.equals("nobodyDied")) {
				return "fight";
			}
			return fightOutcome;
		}
		if(hero.heroClass.equals("Berserk")){
			if (hero.mana - 20 < 0) {
				model.addAttribute("message", hero.createDisplayText());
				return "noMana";

			}
			hero.mana -= 20;
			enemy = Enemy.fromCookie(enemyCookie);
			double bloodlustPercentIncrease=1.5*hero.critChance;
			int berserkPassiveDamage=(hero.maxHp-hero.hp)/25;
			bloodlustBonusDamageMin=(int)((hero.attackMin+berserkPassiveDamage)*(bloodlustPercentIncrease)*0.01);
			bloodlustBonusDamageMax=(int)((hero.attackMax+berserkPassiveDamage)*(bloodlustPercentIncrease)*0.01);
			model.addAttribute("spellDamage", "You cast Bloodlust  increasing your Damage by "+String.valueOf(bloodlustPercentIncrease)+"%.Your damage is now "+String.valueOf(hero.attackMin+berserkPassiveDamage+bloodlustBonusDamageMin)+"-"
					+ String.valueOf(hero.attackMax+berserkPassiveDamage+bloodlustBonusDamageMax)+" and your next attack is a critical");
			Cookie leHeroCookie = hero.createCookie();
			leHeroCookie.setPath("/");
			leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(leHeroCookie);
			berserkCritical=100;
			String fightOutcome = fight(enemy.health, enemy.attackType, enemy.damageMin, enemy.damageMax, enemy.armor,
					enemy.dropsGold, enemy.critChance,rangerSightBonusDamageMin,rangerSightBonusDamageMax, response, model, hero);
			if (fightOutcome.equals("nobodyDied")) {
				return "fight";
			}
			return fightOutcome;
		}
		if(hero.heroClass.equals("Giant")){
			enemy = Enemy.fromCookie(enemyCookie);
			int healthLost=(int)(hero.hp*0.25);
			int earthShockCritChance=healthLost+hero.critChance;
			int earthShockDamage=(int)(hero.maxHp*0.10);
			hero.hp-=healthLost;
			String critically="";
			if (critical(earthShockCritChance)) {
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
			String fightOutcome = fight(enemy.health, enemy.attackType, enemy.damageMin, enemy.damageMax, enemy.armor,
					enemy.dropsGold, enemy.critChance,rangerSightBonusDamageMin,rangerSightBonusDamageMax, response, model, hero);
			if (fightOutcome.equals("nobodyDied")) {
				return "fight";
			}
			return fightOutcome;
		}
		if (hero.heroClass.equals("Necromancer")) {
			if (hero.mana - 20 < 0) {
				model.addAttribute("message", hero.createDisplayText());
				return "noMana";

			}
			hero.mana -= 20;
			String critically = "";
			enemy = Enemy.fromCookie(enemyCookie);
			int currentEnemyHealth = enemy.health;
			if (critical(hero.critChance)) {
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
			String fightOutcome = fight(enemy.health, enemy.attackType, enemy.damageMin, enemy.damageMax, enemy.armor,
					enemy.dropsGold, enemy.critChance,0,0, response, model, hero);
			if (fightOutcome.equals("nobodyDied")) {
				return "fight";
			}
			return fightOutcome;
		}
		else {
			return "fight";
		}
		

	}
}
