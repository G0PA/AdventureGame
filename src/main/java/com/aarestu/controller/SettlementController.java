package com.aarestu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SettlementController {
	@RequestMapping(value="/skipSettlement",method=RequestMethod.GET)
	String hello(ModelMap model,HttpServletResponse response,@CookieValue("leftEnemies") String fooCookie,@CookieValue("resource") String resourceCookie)
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
		return "skippedSettlement";
	}
}