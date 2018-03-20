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
	final static Logger logger=Logger.getLogger(FightController.class);
	@RequestMapping(value="/fight",method = RequestMethod.GET)
	public String fight(ModelMap model, @CookieValue("hero") String fooCookie, @CookieValue(value="enemy",defaultValue="-1001") String badCookie,@CookieValue(value="resource",defaultValue="-1001") String resourceCookie,
			HttpServletResponse response) {
	
		resource=resourceCookie;
		logger.debug("the bad Cookie is: "+badCookie);
		hero = Hero.fromCookie(fooCookie);
		if (hero == null) {
			return "defeat";
		}
		model.addAttribute("resource",resourceCookie);
		logger.debug("enemy123:  "+badCookie);
		enemy = Enemy.fromCookie(badCookie);

		String fightOutcome=fight(enemy.health,enemy.attackType,enemy.damageMin,enemy.damageMax,enemy.armor,enemy.dropsGold,enemy.critChance,response,model,hero);
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
			int enemyArmor, int dropsGold, int enemyCritChance, HttpServletResponse response, ModelMap model,Hero hero) {
		int defense;
		if(hero.heroClass.equals("Mage"))
		{
			hero.attackMin+=hero.magicResist/2;
			hero.attackMax+=hero.magicResist/2;
			model.addAttribute("spell","Fireball");
		}
		if(attackType==1) {
			defense=hero.armor;
		} else {
			defense=hero.magicResist;
		}
		int tempEnemyHealth=enemyHealth;
		boolean crit=critical(hero.critChance);
		double multiply=1;
		if(crit==true) {
			model.addAttribute("critically","CRITICALLY ");
			multiply=1.8;
		} else {
			model.addAttribute("critically","");
		}
		if ((attack(hero.attackMin,hero.attackMax)*multiply - enemyArmor)<0) {
			enemyHealth=enemyHealth-1;
		} else {
			enemyHealth =(int) (enemyHealth - (attack(hero.attackMin, hero.attackMax) * multiply - enemyArmor));
		}
		if (enemyHealth <= 0) {
			hero.gold += dropsGold;
			if(hero.heroClass.equals("Mage"))
			{
				hero.attackMin-=hero.magicResist/2;
				hero.attackMax-=hero.magicResist/2;
			}
			Cookie c = hero.createCookie();

			c.setPath("/");
			c.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(c);
			model.addAttribute("gold",String.valueOf(dropsGold));
			Cookie bossState=new Cookie("bossState","dead");
			bossState.setPath("/");
			bossState.setMaxAge(60*60*24*2);
			response.addCookie(bossState);
			return "fightvictory";
		}
		int tempHealth=hero.hp;
		crit=critical(enemyCritChance);
		multiply=1;
		if(crit==true) {
			model.addAttribute("enemyCritically","CRITICALLY ");
			multiply=2;
		} else {
			model.addAttribute("enemyCritically","");
		}
		if ((attack(enemyAttackMin,enemyAttackMax) - hero.armor) < 0) {
			hero.hp = hero.hp - 1;
		} else {
			hero.hp =(int) (hero.hp - (attack(enemyAttackMin,enemyAttackMax)*multiply - defense));
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
			
			int theIndex=attack(0,images.size()-1);
			model.addAttribute("defeatScreen",images.get(theIndex));
			return "defeat";

		}
		if(hero.heroClass.equals("Mage"))
		{
			hero.attackMin-=hero.magicResist/2;
			hero.attackMax-=hero.magicResist/2;
		}
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
		if(hero.heroClass.equals("Mage"))
			if(hero.mana-20<0)
			{
				model.addAttribute("message",hero.createDisplayText());
				return "noMana";
				
			}
				hero.mana-=20;
				Enemy enemy=Enemy.fromCookie(enemyCookie);
				int currentEnemyHealth=enemy.health;
				enemy.health-=hero.maxMana*0.20;
				currentEnemyHealth=currentEnemyHealth-enemy.health;
				model.addAttribute("spellDamage","You cast Fireball Dealing "+String.valueOf(currentEnemyHealth)+" Damage");
				Cookie leHeroCookie=hero.createCookie();
				leHeroCookie.setPath("/");
				leHeroCookie.setMaxAge(60*60*24*2);
				response.addCookie(leHeroCookie);
				String fightOutcome=fight(enemy.health,enemy.attackType,enemy.damageMin,enemy.damageMax,enemy.armor,enemy.dropsGold,enemy.critChance, response,model,hero);
				if(fightOutcome.equals("nobodyDied"))
				{
					return "fight";
				}
				return fightOutcome;
		
		
	}
}
