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

	
	String theBadCookie="";
	String resource="";

	Hero hero;
	Enemy enemy;
	@RequestMapping(method = RequestMethod.GET)
	public String redirect(HttpServletResponse response, ModelMap model, @CookieValue("hero") String fooCookie,
			@CookieValue(value = "enemy", defaultValue = "-1001") String badCookie,
			@CookieValue(value = "resource", defaultValue = "-1001") String resourceCookie,
			@CookieValue(value = "passed", defaultValue = "passed") String passedCookie,
			@CookieValue(value = "firstBoss", defaultValue = "notReached") String firstBossCookie,
			@CookieValue("spellCast")String spellCastCookie,
			@CookieValue("skills")String skillsCookie)
	{
		hero=Hero.fromCookie(fooCookie);
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else if(hero.zone.equals("Red Woods")) {
			model.addAttribute("zone","redWoods");
			}
		if(firstBossCookie.equals("fighting")) {
			model.addAttribute("boss","The enemy is too Strong to outrun or escape");
			model.addAttribute("resource",resourceCookie);	
			return "failedToFlee";
		}
		
		if(passedCookie.equals("failed"))
		{
			model.addAttribute("cheater","No Cheating : )");
			return "failedToFlee";
		}
		int escape=flee(1,2);
		if(escape==1)
		{
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
			Cookie c=new Cookie("bossState","dead");
			c.setPath("/");
			c.setMaxAge(60*60*24*2);
			response.addCookie(c);
			Cookie heroCookie=hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60*60*24*2);
			response.addCookie(heroCookie);
			if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else if(hero.zone.equals("Red Woods")) {
			model.addAttribute("zone","redWoods");
			}
			return "escaped";
		}

		
		model.addAttribute("resource",resourceCookie);	
		enemy=enemy.fromCookie(badCookie);

		int defense;
		int enemyDamage1=hero.hp;
		//1st attack
		if(enemy.attackType==1)
		{
			defense=hero.armor;
		}else
		{
			defense=hero.magicResist;
		}
		int tempHealth=hero.hp;
		boolean crit=critical(enemy.critChance);
		int multiply=1;
		if(crit==true)
		{
			model.addAttribute("enemyCritically","CRITICALLY ");
			multiply=2;
		}
		else {
			model.addAttribute("enemyCritically","");
		}
		if((attack(enemy.damageMin,enemy.damageMax) - hero.armor)<0)
		{
			//do nothing
		}else {
			
		hero.hp = hero.hp - (attack(enemy.damageMin,enemy.damageMax)*multiply - defense);
		}
		enemyDamage1-=hero.hp;
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
			
			int theIndex=attack(0,images.size()-1);
			model.addAttribute("defeatScreen",images.get(theIndex));
			model.addAttribute("message2", hero.createDisplayText());
			model.addAttribute("enemyName",enemy.name);
			model.addAttribute("enemy",enemy.health);
			model.addAttribute("enemyDamage",String.valueOf(enemyDamage1));
			return "defeat";

		}
		int enemyDamage=tempHealth-hero.hp;
		model.addAttribute("enemyName",resource);
		model.addAttribute("enemyDamage",String.valueOf(enemyDamage));
		//2nd attack
		 tempHealth=hero.hp;
		 crit=critical(enemy.critChance);
		 multiply=1;
		if(crit==true)
		{
			model.addAttribute("enemyCritically2","CRITICALLY ");
			multiply=2;
		}
		else {
			model.addAttribute("enemyCritically2","");
		}
		if((attack(enemy.damageMin,enemy.damageMax) - hero.armor)<0)
		{
			//do nothing
		}else {
		hero.hp = hero.hp - (attack(enemy.damageMin,enemy.damageMax)*multiply - defense);
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
			model.addAttribute("defeatScreen",images.get(theIndex));
			model.addAttribute("message2", hero.createDisplayText());
			model.addAttribute("enemyName",enemy.name);
			model.addAttribute("enemy",enemy.health);
			model.addAttribute("enemyDamage2",enemy.name+" damages you dealing "+String.valueOf(enemyDamage1)+" damage");
			return "defeat";

		}
		
		Cookie c = hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		 enemyDamage=tempHealth-hero.hp;
		model.addAttribute("message2", hero.createDisplayText());
		model.addAttribute("enemyDamage2",String.valueOf(enemyDamage));
		model.addAttribute("resource",resourceCookie);
		Cookie passed= new Cookie("passed","failed");
		passed.setMaxAge(60*60*24*2);
		passed.setPath("/");
		response.addCookie(passed);
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		String[] skills=skillsCookie.split(",");
		String theSkill=skills[0];
		ArrayList<String[]>newSkills=hero.generateHeroSkillText(hero, response);
		Cookie newSkillsCookie=new Cookie("skills",newSkills.get(0)[0]+","+newSkills.get(1)[0]);
		newSkillsCookie.setPath("/");
		newSkillsCookie.setMaxAge(60*60*24*2);
		response.addCookie(newSkillsCookie);
		model.addAttribute("skill1",newSkills.get(0)[0]);
		model.addAttribute("skill2",newSkills.get(1)[0]);
		model.addAttribute("tooltip1",newSkills.get(0)[1]);
		model.addAttribute("tooltip2",newSkills.get(1)[1]);
		if(hero.zone.equals("Green Woods")) {
			model.addAttribute("zone","hello");
			}else if(hero.zone.equals("Red Woods")) {
			model.addAttribute("zone","redWoods");
			}
		return "failedToFlee";
		
	}
}
