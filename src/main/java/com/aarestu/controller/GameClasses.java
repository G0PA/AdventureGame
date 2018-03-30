package com.aarestu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GameClasses {
	@RequestMapping(value="/mage",method=RequestMethod.GET)
	public String returnMage(@CookieValue("hero")String heroCookie,HttpServletResponse response)
	{
		Hero hero=Hero.fromCookie(heroCookie);
		hero.heroClass="Mage";
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		
		
		return "mage";
	}
	@RequestMapping(value="/warrior",method=RequestMethod.GET)
	public String returnWarrior(@CookieValue("hero")String heroCookie,HttpServletResponse response)
	{
		Hero hero=Hero.fromCookie(heroCookie);
		hero.heroClass="Warrior";
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		
		
		return "warrior";
	}
	
	@RequestMapping(value="/ranger",method=RequestMethod.GET)
	public String returnRanger(@CookieValue("hero")String heroCookie,HttpServletResponse response)
	{
		Hero hero=Hero.fromCookie(heroCookie);
		hero.heroClass="Ranger";
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		
		
		return "ranger";
	}
	
	@RequestMapping(value="/berserk",method=RequestMethod.GET)
	public String returnBerserk(@CookieValue("hero")String heroCookie,HttpServletResponse response)
	{
		Hero hero=Hero.fromCookie(heroCookie);
		hero.heroClass="Berserk";
		
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		
		
		return "berserk";
	}
	@RequestMapping(value="/giant",method=RequestMethod.GET)
	public String returnGiant(@CookieValue("hero")String heroCookie,HttpServletResponse response)
	{
		Hero hero=Hero.fromCookie(heroCookie);
		hero.heroClass="Giant";
		hero.mana=0;
		hero.manaRegen=0;
		hero.maxMana=0;
		
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		return "giant";
	}
	
	@RequestMapping(value="/necromancer",method=RequestMethod.GET)
	public String returnNecromancer(@CookieValue("hero")String heroCookie,HttpServletResponse response)
	{
		Hero hero=Hero.fromCookie(heroCookie);
		hero.heroClass="Necromancer";
		
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		
		
		return "necromancer";
	}
	@RequestMapping(value="/greenWoods",method=RequestMethod.GET)
	public String returnGreenWoods() {
		return "greenWoods";
	}
	
}
