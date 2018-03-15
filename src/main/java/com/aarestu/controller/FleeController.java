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
@RequestMapping("/flee")
public class FleeController {
	int flee(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
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
	@RequestMapping(method = RequestMethod.GET)
	public String redirect(HttpServletResponse response,ModelMap model, @CookieValue("hero") String fooCookie, @CookieValue(value="enemy",defaultValue="-1001") String badCookie,@CookieValue(value="resource",defaultValue="-1001") String resourceCookie,@CookieValue(value="passed",defaultValue="passed") String passedCookie)
	{
		if(passedCookie.equals("failed"))
		{
			model.addAttribute("cheater","No Cheating : )");
			return "failedToFlee";
		}
		int escape=flee(1,2);
		if(escape==1)
		{
			Cookie c=new Cookie("bossState","dead");
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			
			return "escaped";
		}
		
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
		theBadCookie=badCookie.replaceAll("[A-Za-z=]+","");
		String []enemyAttributes=theBadCookie.split(",");
		int attackType=Integer.parseInt(enemyAttributes[2].trim());
		int enemyHealth=Integer.parseInt(enemyAttributes[1].trim());
		String[] enemyAttacks=enemyAttributes[3].split("-");
		int enemyAttackMin=Integer.parseInt(enemyAttacks[0].trim());
		int enemyAttackMax=Integer.parseInt(enemyAttacks[1].trim());
		int enemyArmor=Integer.parseInt(enemyAttributes[4].trim());
		int dropsGold=Integer.parseInt(enemyAttributes[5].trim());
		int enemyCritChance=Integer.parseInt(enemyAttributes[6].trim());
		int defense;
		//1st attack
		if(attackType==1)
		{
			defense=armor;
		}else
		{
			defense=magicResist;
		}
		int tempHealth=health;
		boolean crit=critical(enemyCritChance);
		int multiply=1;
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
		int enemyDamage=tempHealth-health;
		model.addAttribute("enemyName",resource);
		model.addAttribute("enemyDamage",String.valueOf(enemyDamage));
		//2nd attack
		 tempHealth=health;
		 crit=critical(enemyCritChance);
		 multiply=1;
		if(crit==true)
		{
			model.addAttribute("enemyCritically2","CRITICALLY ");
			multiply=2;
		}
		else {
			model.addAttribute("enemyCritically2","");
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
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		 enemyDamage=tempHealth-health;
		model.addAttribute("message2", leCookie+"%");
		model.addAttribute("enemyDamage2",String.valueOf(enemyDamage));
		model.addAttribute("resource",resourceCookie);
		Cookie passed= new Cookie("passed","failed");
		passed.setMaxAge(60*60*24*2);
		passed.setPath("/");
		response.addCookie(passed);
		return "failedToFlee";
		
	}
}
