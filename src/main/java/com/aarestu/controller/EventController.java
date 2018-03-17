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
public class EventController {
	Hero hero;
	ArrayList<Event> events=new ArrayList<Event>();
	int count=0;
	int pickEventIndex(int min, int max) {
		   int range = (max - min) + 1;     
		   return (int)(Math.random() * range) + min;
		}
	Event theEvent;
	@RequestMapping(value = "/event",method = RequestMethod.GET)
	public String theEvent(ModelMap model, HttpServletResponse response, @CookieValue("hero")String heroCookie,@CookieValue(value="passedEvents",defaultValue="-9,")String passedEventsCookie)
	{
		count++;
		if(count==1) {
		hero=Hero.fromCookie(heroCookie);
		Hero elfDruidsGood1=new Hero(30,0,0,0,0,0,0,0,0);
		Hero elfDruidsBad1=new Hero(-30,0,0,0,0,0,0,0,0);
		Hero elfDruidsGood2=new Hero(0,0,0,0,0,0,20,0,0);
		Event elfDruids=new Event("elfDruids","You come across three elf druids who are healing a plant in the wilds" , "Ask the druids to heal you",70,elfDruidsGood1,"The druids agree to use their healing powers on you, restoring 30 health",elfDruidsBad1,"The druids think you are trying to trick them so they attack you dealing 30 damage to you","Threathen to attack the druids if they don't give you gold",30,elfDruidsGood2,"The elfs feel threathened by you and give you 20 gold to leave them alone",elfDruidsBad1,"the Elfs attack you dealing 30 damage then run away");
		Hero sacredMageGood1=new Hero(0,0,0,0,0,3,0,0,0);
		Hero sacredMageBad1=new Hero(0,0,0,0,0,-3,0,0,0);
		Hero sacredMageGood2=new Hero(15,30,0,0,0,0,0,0,0);
		Hero sacredMageBad2=new Hero(-15,-15,0,0,0,0,0,0,0);
		Event sacredMage=new Event("sacredMage","You venture into a cave and find a sacred mage studying arcane magic","Ask the mage to teach you what he knows",50,sacredMageGood1,"The Sacred Mage decide to teach you the ways of magic, increasing your Magic resist with 3",sacredMageBad1,"The Sacred Mage drains your magic affinity for himself reducing your Magic resist with 3","Steal a vial from the shelf and drink it",30,sacredMageGood2,"You drink the contents of the vial feeling a surge of vitality.Current Health +15, Maximum health +30",sacredMageBad2,"You drink the contents of the vial and begin to feel poisoned. Current and Maximum health are reduced by 15");
		Hero oldSageGood1=new Hero(20,20,2,2,1,1,0,2,-4);
		Hero oldSageBad1=new Hero(0,0,0,0,0,0,0,0);
		Hero oldSageGood2=new Hero(0,0,0,0,0,0,10,0);
		Hero oldSageBad2=new Hero(0,0,0,0,0,0,0,0,-1);
		Event oldSage = new Event("oldSage","You stumble across an old Sage standing near a tree of skeletons","Ask the Sage to teach you every wisdom that he knows",50,oldSageGood1,"The old Sage teaches you everything he knows, but by the time he is finished an entire year has passed. Enemy encounters -4.Current and maximum health +20, Attack Minimum and Attack Maximum +2. Armor and Magic resist +1. Critical chance +2%",oldSageBad1,"The old Sage declines your offer and leaves.","Rob the old sage",80,oldSageGood2,"You rob the old Sage stealing 10 gold",oldSageBad2,"The old sage casts a spell on you to immobilize you and then leaves. Enemy encounters -1");
		events.add(elfDruids);
		events.add(sacredMage);
		events.add(oldSage);
		}
		int theIndex;
		String[] passedEventsArr=passedEventsCookie.split(",");
		outer: while (true) {
			theIndex = pickEventIndex(0, events.size() - 1);
			for (int i = 0; i < passedEventsArr.length; i++) {
				if(!passedEventsArr[i].equals(String.valueOf(theIndex)))
				{
					break outer;
				}
			}

		}
		theEvent=events.get(theIndex);
		model.addAttribute("firstButton",theEvent.firstButton);
		model.addAttribute("secondButton",theEvent.secondButton);
		model.addAttribute("resource",theEvent.resource);
		model.addAttribute("message",hero.createDisplayText());
		model.addAttribute("event",theEvent.eventName);
		Cookie passedEvents=new Cookie("passedEvents",String.valueOf(theIndex)+",");
		passedEvents.setPath("/");
		passedEvents.setMaxAge(60*60*24*2);
		response.addCookie(passedEvents);
		return "event";
	}
	@RequestMapping(value = "/outcome1",method = RequestMethod.GET)
	public String theOutcome1(ModelMap model,HttpServletResponse response)
	{
		model.addAttribute("resource",theEvent.resource);
		if(theEvent.outcome(theEvent.firstButtonSuccessRate))
		{

			hero=theEvent.updateHero(hero, theEvent.goodOutcomeHero1);
			model.addAttribute("event",theEvent.goodOutcomeText1);
		}
		else 
		{
			hero=theEvent.updateHero(hero, theEvent.badOutcomeHero1);
			model.addAttribute("event",theEvent.badOutcomeText1);
		}
		model.addAttribute("message",hero.createDisplayText());
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);

		return "theOutcome";
	}
	
	@RequestMapping(value = "/outcome2",method = RequestMethod.GET)
	public String theOutcome2(ModelMap model,HttpServletResponse response,@CookieValue("leftEnemies")String leftEnemiesCookie)
	{
		model.addAttribute("resource",theEvent.resource);
		if(theEvent.outcome(theEvent.secondButtonSuccessRate))
		{
			hero=theEvent.updateHero(hero, theEvent.goodOutcomeHero2);
			model.addAttribute("event",theEvent.goodOutcomeText2);
		}
		else 
		{
			hero=theEvent.updateHero(hero, theEvent.badOutcomeHero2);
			model.addAttribute("event",theEvent.badOutcomeText2);
		}
		model.addAttribute("message",hero.createDisplayText());
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);

		return "theOutcome";
	}
	
}
