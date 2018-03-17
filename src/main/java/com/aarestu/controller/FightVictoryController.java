package com.aarestu.controller;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/fightvictory")
public class FightVictoryController {
	int count=0;
	final static Logger logger = Logger.getLogger(FightVictoryController.class);
	@RequestMapping(method = RequestMethod.GET)
	public String fightVictory(ModelMap model,@CookieValue("hero") String fooCookie) {
		String cookie=fooCookie;
		model.addAttribute("message", cookie+"%");
		return "fightvictory";
	}
}
