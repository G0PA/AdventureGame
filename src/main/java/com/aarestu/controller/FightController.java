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

@Controller
@RequestMapping("/fight")
public class FightController {
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
	String maxHealth="";
	final static Logger logger=Logger.getLogger(FightController.class);
	@RequestMapping(method = RequestMethod.GET)
	public String fight(ModelMap model, @CookieValue("hero") String fooCookie, @CookieValue(value="enemy",defaultValue="-1001") String badCookie,@CookieValue(value="resource",defaultValue="-1001") String resourceCookie,
			HttpServletResponse response) {
		resource=resourceCookie;
		logger.debug("the bad Cookie is: "+badCookie);
		String theCookie = fooCookie.replaceAll("[A-Za-z=]+", "");
		String[] heroAttributes = theCookie.split(",");
		String currentHealth=heroAttributes[0].trim();
		String[] healthArr =currentHealth.split("/");
		maxHealth=healthArr[1];
		health=Integer.parseInt(healthArr[0]);
		attacks=heroAttributes[1].trim().split("-");
		attackMin = Integer.parseInt(attacks[0]);
		attackMax = Integer.parseInt(attacks[1]);
		armor = Integer.parseInt(heroAttributes[2].trim());
		magicResist = Integer.parseInt(heroAttributes[3].trim());
		gold = Integer.parseInt(heroAttributes[4].trim());
		critChance=Integer.parseInt(heroAttributes[5].trim());
		model.addAttribute("resource",resourceCookie);
		logger.debug("enemy123:  "+badCookie);
		theBadCookie=badCookie.replaceAll("[A-Za-z=]+","");
		logger.debug("enemy Cookie is: "+theBadCookie);
		String []enemyAttributes=theBadCookie.split(",");
		int attackType=Integer.parseInt(enemyAttributes[2].trim());
		int enemyHealth=Integer.parseInt(enemyAttributes[1].trim());
		String[] enemyAttacks=enemyAttributes[3].split("-");
		int enemyAttackMin=Integer.parseInt(enemyAttacks[0].trim());
		int enemyAttackMax=Integer.parseInt(enemyAttacks[1].trim());
		int enemyArmor=Integer.parseInt(enemyAttributes[4].trim());
		int dropsGold=Integer.parseInt(enemyAttributes[5].trim());
		int enemyCritChance=Integer.parseInt(enemyAttributes[6].trim());

		String fightOutcome=fight(enemyHealth,attackType,enemyAttackMin,enemyAttackMax,enemyArmor,dropsGold,enemyCritChance, response,model);
		if(fightOutcome.equals("nobodyDied"))
		{
			return "fight";
		}
		return fightOutcome;
	}

	
	
	
	
	
	int attack(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	boolean critical(int critChance)
	{
		int range= (100/critChance)+1;
		if((int)(Math.random()* range)+0==1)
		{
			return true;
		}
		return false;
	}
	String fight(int enemyHealth,int attackType, int enemyAttackMin, int enemyAttackMax, int enemyArmor,int dropsGold,int enemyCritChance, HttpServletResponse response,ModelMap model)
	{
		int defense;
		if(attackType==1)
		{
			defense=armor;
		}else
		{
			defense=magicResist;
		}
		int tempEnemyHealth=enemyHealth;
		boolean crit=critical(critChance);
		int multiply=1;
		if(crit==true)
		{
			model.addAttribute("critically","CRITICALLY ");
			multiply=2;
		}
		else {
			model.addAttribute("critically","");
		}
		if((attack(attackMin,attackMax)*multiply - enemyArmor)<0)
		{
			//do nothing
		}else {
		enemyHealth = enemyHealth - (attack(attackMin,attackMax)*multiply - enemyArmor);
		}
		if (enemyHealth <= 0) {
			gold += dropsGold;
			String health2 = String.valueOf(health);
			String attackMin2 = String.valueOf(attackMin);
			String attackMax2 = String.valueOf(attackMax);
			String armor2 = String.valueOf(armor);
			String magicResist2 = String.valueOf(magicResist);
			String gold2 = String.valueOf(gold);
			String critChance2=String.valueOf(critChance);
			String leCookie = "health = " + health2 +"/"+maxHealth+ ",   attack = " + attackMin2 +"-"+attackMax2+",   armor = " + armor2
					+ ",   magic resist = " + magicResist2 + ",   gold = " + gold2+",   critical chance = "+critChance2;
			Cookie c = new Cookie("hero", leCookie);

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
		int tempHealth=health;
		crit=critical(enemyCritChance);
		multiply=1;
		if(crit==true)
		{
			model.addAttribute("enemyCritically","CRITICALLY ");
			multiply=2;
		}
		else {
			model.addAttribute("enemyCritically","");
		}
		if((attack(enemyAttackMin,enemyAttackMax) - armor)<0)
		{
			//do nothing
		}else {
		health = health - (attack(enemyAttackMin,enemyAttackMax)*multiply - defense);
		}
		if (health <= 0) {
			String health2 = String.valueOf(health);
			String attackMin2 = String.valueOf(attackMin);
			String attackMax2 = String.valueOf(attackMax);
			String armor2 = String.valueOf(armor);
			String magicResist2 = String.valueOf(magicResist);
			String gold2 = String.valueOf(gold);
			String critChance2=String.valueOf(critChance);
			String leCookie = "health = " + health2 +"/"+maxHealth+ ",   attack = " + attackMin2 +"-"+attackMax2+",   armor = " + armor2
					+ ",   magic resist = " + magicResist2 + ",   gold = " + gold2+",   critical chance = "+critChance2;
			Cookie c = new Cookie("hero", leCookie);

			c.setPath("/");
			c.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(c);
			ArrayList<String> images=new ArrayList<String>();
			
			images.add("defeat");
			images.add("defeat2");
			images.add("defeat3");
			images.add("defeat4");
			images.add("defeat5");
			
			int theIndex=attack(0,images.size()-1);
			model.addAttribute("defeatScreen",images.get(theIndex));
			return "defeat";

		}
		String health2 = String.valueOf(health);
		String attackMin2 = String.valueOf(attackMin);
		String attackMax2 = String.valueOf(attackMax);
		String armor2 = String.valueOf(armor);
		String magicResist2 = String.valueOf(magicResist);
		String gold2 = String.valueOf(gold);
		String critChance2=String.valueOf(critChance);
		String leCookie = "health = " + health2 +"/"+maxHealth+ ",   attack = " + attackMin2 +"-"+attackMax2+",   armor = " + armor2
				+ ",   magic resist = " + magicResist2 + ",   gold = " + gold2+",   critical chance = "+critChance2;
		Cookie c = new Cookie("hero", leCookie);
		theBadCookie=theBadCookie.replaceFirst("[0-9]+", String.valueOf(enemyHealth));
		Cookie e = new Cookie("enemy", theBadCookie);
		e.setMaxAge(60 * 60 * 24 * 2);
		e.setPath("/");
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		response.addCookie(e);
		int damageDealt = tempEnemyHealth-enemyHealth;
		int enemyDamage=tempHealth-health;
		model.addAttribute("message2", leCookie+"%");
		model.addAttribute("damageDealt", String.valueOf(damageDealt));
		model.addAttribute("enemy", String.valueOf(enemyHealth));
		model.addAttribute("enemyName",resource);
		model.addAttribute("enemyDamage",String.valueOf(enemyDamage));
		return "nobodyDied";
	}
}
