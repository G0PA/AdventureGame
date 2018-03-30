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
	Event theEvent;
	@RequestMapping(value = "/event",method = RequestMethod.GET)
	public String theEvent(ModelMap model, HttpServletResponse response, @CookieValue("hero")String heroCookie,@CookieValue(value="passedEvents",defaultValue="-9,")String passedEventsCookie,@CookieValue(value="eventState",defaultValue="eligebleForNewEvent")String eventStateCookie)
	{
		if (eventStateCookie.equals("eligebleForNewEvent")) {
			count++;
			hero = Hero.fromCookie(heroCookie);
			if (count == 1) {
				Hero elfDruidsGood1 = new Hero(25, 0, 0,0, 0, 0, 0, 0, 0, 0, 0,0,0,"");
				Hero elfDruidsBad1 = new Hero(-25, 0, 0,0, 0, 0, 0, 0, 0, 0, 0,0,0,"");
				Hero elfDruidsGood2 = new Hero(0, 0, 0, 0,0, 0, 0, 0, 15, 0, 0,0,0,"");
				Event elfDruids = new Event("elfDruids",
						"You come across three elf druids who are healing a plant in the wilds",
						"Ask the druids to heal you", 70, elfDruidsGood1,
						"The druids agree to use their healing powers on you, RESTORING 25 health", elfDruidsBad1,
						"The druids think you are trying to trick them, so they attack you DEALING 25 damage to you",
						"Threathen to attack the druids if they don't give you gold", 30, elfDruidsGood2,
						"The elfs feel threathened by you and give you 15 gold to leave them alone", elfDruidsBad1,
						"the Elfs attack you DEALING 25 damage then run away");
				Hero sacredMageGood1 = new Hero(0, 0, 0, 0,0, 0, 0, 2, 0, 0, 0,0,0,"");
				Hero sacredMageBad1 = new Hero(0, 0, 0, 0, 0, 0, 0, -2, 0, 0,0,0,0,"");
				Hero sacredMageGood2 = new Hero(0, 0, 10,15, 0, 0, 0, 0, 0, 0, 0,0,0,"");
				Hero sacredMageBad2 = new Hero(-10,-10,0,0, 0, 0, 0, 0, 0, 0, 0,0,0,"");
				Event sacredMage = new Event("sacredMage",
						"You venture into a cave and find a sacred mage studying arcane magic",
						"Ask the mage to teach you what she knows", 50, sacredMageGood1,
						"The Sacred Mage decide to teach you the ways of magic, INCREASING your Magic resist with 2",
						sacredMageBad1,
						"The Sacred Mage drains your magic affinity for herself REDUCING your Magic resist with 2",
						"Steal a vial from the shelf and drink it", 35, sacredMageGood2,
						"You drink the contents of the vial feeling a surge of vitality.Current Mana +10, Maximum mana +15",
						sacredMageBad2,
						"You drink the contents of the vial and begin to feel poisoned. Current and Maximum health are REDUCED by 10");
				Hero oldSageGood1 = new Hero(15, 15, 0,0, 2, 2, 0, 0, 0, 2, -3,0,0,"");
				Hero oldSageBad1 = new Hero(0, 0, 0, 0,0, 0, 0, 0, 0, 0,0,0,0,"");
				Hero oldSageGood2 = new Hero(0, 0, 0, 0,0, 0, 0, 0, 0, 10, 0,0,0,"");
				Hero oldSageBad2 = new Hero(0, 0, 0, 0,0, 0, 0, 0, 0, 0, -1,0,0,"");
				Event oldSage = new Event("oldSage", "You stumble across an old Sage standing near a tree of skeletons",
						"Ask the Sage to teach you every wisdom that he knows", 33, oldSageGood1,
						"The old Sage teaches you everything he knows, but by the time he is finished an entire year has passed. Enemy encounters -3.Current and maximum health +15, Attack Minimum and Attack Maximum +2, Critical chance +2%",
						oldSageBad1, "The old Sage declines your offer and leaves.", "Rob the old sage", 80,
						oldSageGood2, "You rob the old Sage stealing 10 gold", oldSageBad2,
						"The old sage casts a spell on you to immobilize you and then leaves. Enemy encounters -1");
				Hero riverEncounterGood1 = new Hero(0, 0,0, 0, 2, 2, 0, 0, 0, 0, -2,0,0,"");
				Hero riverEncounterBad1 = new Hero(-15, 0,0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,"");
				Hero riverEncounterGood2 = new Hero(0, 0,0, 0, 0, 0, 2, 0, 0, 0, 0,0,0,"");
				Hero riverEncounterBad2 = new Hero(0, 0,0, 0, 0, 0, -2, 0, 0, 0, 0,0,0,"");
				Event riverEncounter = new Event("riverEncounter",
						"You walk past a wounded Dark Knight with a dragon on his knee",
						"Help the Dark Knight to recover", 28, riverEncounterGood1,
						"You LOSE 2 enemy encounters while helping the Dark Knight, but in return he gives you his Sword INCREASING your Attack Minimum and Attack Maximum with 2",
						riverEncounterBad1,
						"As soon as you approach the wounded Dark Knight he attacks you, DEALING 15 damage before dying.",
						"Try to take the wounded Dark Knight's armor", 50, riverEncounterGood2,
						"You take the dying Knight's armor INCREASING your armor with 2", riverEncounterBad2,
						"As soon as you put on the armor a black magic binds you to it, REDUCING your armor with 2");
				Hero assassinationGood1 = new Hero(0, 0, 0,0, 0, 0, 0, 0, 15, 0);
				Hero assassinationBad1 = new Hero(-20, 0,0, 0, 0, 0, 0, 0, 0, 0);
				Hero assassinationGood2 = new Hero(0, 0,0, 0, 0, 0, 0, 0, 20, 0);
				Hero assassinationBad2 = new Hero(0, 0,0, 0, 0, 0, 0, 0, -15, 0);
				Event assassination = new Event("assassination",
						"You see a shady individual trying to assassinate an elderly man", "Expose the assassin", 50,
						assassinationGood1,
						"The Assassin runs away and you save the elderman's life.He repays you by GIVING you 15 gold",
						assassinationBad1, "The assassin immediately attacks you then runs away.Current health -20",
						"Join the assassin for a share of the Gold reward", 36, assassinationGood2,
						"You help the assassin with the murder.She gives you 20 Gold and runs away.", assassinationBad2,
						"You help the assassin with the murder after which she STEALS 15 gold from you and runs away");
				Hero hoodedIndividualGood1 = new Hero(0, 0, 0,0, 0, 0, 0, 0, 0, 3);
				Hero hoodedIndividualBad1 = new Hero(0, 0, 0,0, 0, 0, 0, 0, 0, -3);
				Hero hoodedIndividualGood2 = new Hero(0, 0, 0,0, 0, 0, 0, 0, 0, 0, 1,0,0,"");
				Event hoodedIndividual = new Event("hoodedIndividual",
						"You see a hooded individual walking infront you", "Go and talk to him", 50,
						hoodedIndividualGood1,
						"The person appears to be a shaman which shows you the way of the spirits INCREASING your Critical chance with 3%",
						hoodedIndividualBad1,
						"The hooded individual appears to be a shaman.He curses you REDUCING your critical strike chance with 3% and leaves",
						"Ignore him", 100, hoodedIndividualGood2,
						"You ignore the unknown person and continue on your way swiftly. Enemy encounters +1",
						hoodedIndividualGood2, "asd");
				Hero travelInTheMarshesGood1 = new Hero(0, 0, 0,0, 1, 1, 1, 1, 0, 2, -2,0,0,"");
				Hero travelInTheMarshesBad1 = new Hero(-20, 0, 0,0, 0, 0, 0, 0, 0, 0, -2,0,0,"");
				Hero travelInTheMarshesGood2 = new Hero(0, 0, 0,0, 0, 0, 0, 0, 15, 0);
				Hero travelInTheMarshesBad2 = new Hero(-20, 0, 0,0, 0, 0, 0, 0, 0, 0, -2,0,0,"");
				Event travelInTheMarshes = new Event("travelInTheMarshes",
						"As you venture into the marshes you find a group of people waling on the path with a loaded carriage",
						"Help the travelers pass the Marshes", 50, travelInTheMarshesGood1,
						"You help the travelers pass the Marshes safely, by the time you pass them you have LOST 2 enemy encounters,however the travelers repay you by giving you an Item increasing your Attack Min and Attack Max with 1, Armor and Magic Resist with 1 and Critical Chance with 2%",
						travelInTheMarshesBad1,
						"You are attacked on the road while trying to help the travelers pass the Marshes safely.You LOSE 20 health and 2 Enemy Encounters",
						"Rob the travelers", 80, travelInTheMarshesGood2,
						"You rob the passing traders INCREASING your Gold with 15", travelInTheMarshesBad2,
						"You begin chasing the people deep into the Marshes, they eventually escape from you, but by that time you have LOST 2 Enemy Encounters and 20 Health");
				Hero spiritBeastGood1 = new Hero(0, 0, 0,0, 0, 0, 0, 0, 0, 0, 3,0,0,"");
				Hero spiritBeastBad1 = new Hero(0, 0, 0, 0,0, 0, 0, 0, 0, 0, -3,0,0,"");
				Hero spiritBeastGood2 = new Hero(0, 0, 0,0, 0, 0, 0, 3, 0, 0, 0,0,0,"");
				Hero spiritBeastBad2 = new Hero(-20, 0, 0,0, 0, 0, 0, -2, 0, 0, 0,0,0,"");
				Event spiritBeast=new Event("spiritBeast",
						"You find a spiritual beast staring into an aurora sky",
						"Pet the animal",50,
						spiritBeastGood1,
						"The animal senses your positive energy.As soon as you pet it the aurora sky begins to spin taking you back in time. Enemy encounters +3",
						spiritBeastBad1,
						"the animal senses your negative energy.As soon as you approach it the animal mesmerizes you and you fall asleep for a very long time. Enemy encounters -3",
						"Attack the animal",33,
						spiritBeastGood2,
						"You slay the beast in one blow, gaining his Magic affinity for yourself.Magic Resist +3",
						spiritBeastBad2,
						"the animal drains your Magic affinity and leaves.Health -20, Magic Resist -2");
						
				events.add(elfDruids);
				events.add(sacredMage);
				events.add(oldSage);
				events.add(riverEncounter);
				events.add(assassination);
				events.add(hoodedIndividual);
				events.add(travelInTheMarshes);
				events.add(spiritBeast);
			}
			int theIndex;
			String[] passedEventsArr = passedEventsCookie.split(",");
			outer: while (true) {
				theIndex = Utils.attack(0, events.size() - 1);
				for (int i = 0; i < passedEventsArr.length; i++) {
					if (!passedEventsArr[i].equals(String.valueOf(theIndex))) {
						break outer;
					}
				}

			}
			theEvent = events.get(theIndex);
			model.addAttribute("firstButton", theEvent.firstButton);
			model.addAttribute("secondButton", theEvent.secondButton);
			model.addAttribute("resource", theEvent.resource);
			model.addAttribute("message", hero.createDisplayText());
			model.addAttribute("event", theEvent.eventName);
			Cookie passedEvents = new Cookie("passedEvents",  passedEventsCookie+","+String.valueOf(theIndex) );
			passedEvents.setPath("/");
			passedEvents.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(passedEvents);
			Cookie eventState=new Cookie("eventState","choiceEligeble");
			eventState.setPath("/");
			eventState.setMaxAge(60*60*24*2);
			response.addCookie(eventState);
			if(hero.hp<=0) {
				return "defeat";
			}
			return "event";
		}
		else {
			return "hello";
		}
	}
	@RequestMapping(value = "/outcome1",method = RequestMethod.GET)
	public String theOutcome1(ModelMap model,HttpServletResponse response,@CookieValue("eventState")String eventStateCookie)
	{
		if(!eventStateCookie.equals("choiceEligeble"))
		{
			return "theOutcome";
		}
		model.addAttribute("resource",theEvent.resource);
		if(theEvent.outcome(theEvent.firstButtonSuccessRate))
		{

			hero=theEvent.updateHero(hero, theEvent.goodOutcomeHero1);
			model.addAttribute("event",theEvent.goodOutcomeText1);
		}
		else 
		{
			hero=theEvent.updateHero(hero, theEvent.badOutcomeHero1);
			model.addAttribute("event2",theEvent.badOutcomeText1);
		}
		model.addAttribute("message",hero.createDisplayText());
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		if(hero.hp<=0) {
			return "defeat";
		}
		

		return "theOutcome";
	}
	
	@RequestMapping(value = "/outcome2",method = RequestMethod.GET)
	public String theOutcome2(ModelMap model,HttpServletResponse response,@CookieValue("leftEnemies")String leftEnemiesCookie,@CookieValue("eventState")String eventStateCookie)
	{
		if(!eventStateCookie.equals("choiceEligeble"))
		{
			return "theOutcome";
		}
		model.addAttribute("resource",theEvent.resource);
		if(theEvent.outcome(theEvent.secondButtonSuccessRate))
		{
			hero=theEvent.updateHero(hero, theEvent.goodOutcomeHero2);
			model.addAttribute("event",theEvent.goodOutcomeText2);
		}
		else 
		{
			hero=theEvent.updateHero(hero, theEvent.badOutcomeHero2);
			model.addAttribute("event2",theEvent.badOutcomeText2);
		}
		model.addAttribute("message",hero.createDisplayText());
		Cookie c=hero.createCookie();
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		if(hero.hp<=0) {
			return "defeat";
		}

		return "theOutcome";
	}
	
}
