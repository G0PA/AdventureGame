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
}
