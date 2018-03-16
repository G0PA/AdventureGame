package com.aarestu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayController {
	int count=0;
	ArrayList<Enemy> enemies= new ArrayList<Enemy>();
	ArrayList<Item> items=new ArrayList<Item>();
	ArrayList<Settlement> settlements=new ArrayList<Settlement>();
	String strToResource;
	Enemy theBoss;
	Settlement theSettlement;
	int settlementTimer=101;
	int chooseZone(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	final static Logger logger=Logger.getLogger(PlayController.class);
	Hero hero=new Hero();
	
	@RequestMapping(value="/play",method=RequestMethod.GET)
	public String plays(ModelMap model, HttpServletResponse response)
	{
		settlementTimer=101;
		String health=String.valueOf(hero.hp);
		String maxHealth=String.valueOf(hero.maxHp);
		String attackMin=String.valueOf(hero.attackMin);
		String attackMax=String.valueOf(hero.attackMax);
		String armor=String.valueOf(hero.armor);
		String magicResist=String.valueOf(hero.magicResist);
		String gold=String.valueOf(hero.gold);
		String critChance=String.valueOf(hero.critChance);
		Cookie leftEnemies=new Cookie("leftEnemies","15");
		leftEnemies.setPath("/");
		leftEnemies.setMaxAge(60*60*24*2);
		response.addCookie(leftEnemies);
		Cookie c = new Cookie("hero","health = "+health+"/"+maxHealth+",   attack = "+attackMin+"-"+attackMax+",   armor = "+armor+",   magic resist = "+magicResist+",   gold = "+gold+",   critical chance = "+critChance);
		Cookie passedMaps2=new Cookie("passedMaps","-9");
		Cookie bossState2=new Cookie("bossState","dead");
		bossState2.setPath("/");
		bossState2.setMaxAge(60*60*24*2);
		passedMaps2.setPath("/");
		passedMaps2.setMaxAge(60*60*24*2);
		c.setPath("/");
		c.setMaxAge(60*60*24*2);
		response.addCookie(c);
		response.addCookie(passedMaps2);
		response.addCookie(bossState2);
		logger.debug("Cookie name: "+c.getName()); 
		logger.debug("the cookie in PlayController is : "+c.getValue());
		model.addAttribute("message", c.getValue()+"%");
		return "play";
	}
	String cookie;
	@RequestMapping("/hello")
	public String index(ModelMap model,@CookieValue(value="hero",defaultValue="defaultHero") String fooCookie,@CookieValue(value="settlement",defaultValue="0") String settlementCookie,@CookieValue(value="bossState",defaultValue="dead") String bossStateCookie,@CookieValue(value="leftEnemies",defaultValue="15") String leftEnemiesString,@CookieValue(value="passedMaps",defaultValue="-9") String passedMaps,HttpServletResponse response)
	{
		Cookie passed=new Cookie("passed","passed");
		passed.setPath("/");
		passed.setMaxAge(60*60*24*2);
		response.addCookie(passed);
		if(bossStateCookie.equals("alive"))
		{
			model.addAttribute("cheater","No cheating : )");
			return "hello";		
		}
		if(bossStateCookie.equals("inSettlement"))
		{
			String[] cookieArr=settlementCookie.split(",");
			model.addAttribute("resource",cookieArr[0]);
			model.addAttribute("settlementName",cookieArr[1]);
			model.addAttribute("message",fooCookie+"%");
			model.addAttribute("cheating","No Cheating : )");
			return "helloSettlement";
		}
		int enemiesLeftCount=Integer.parseInt(leftEnemiesString);
		settlementTimer--;
		if(settlementTimer%3==0)
		{
			int settlementIndex=chooseZone(0,settlements.size()-1);
			theSettlement=settlements.get(settlementIndex);
			Cookie settlement=new Cookie("settlement",theSettlement.resource+","+theSettlement.name);
			settlement.setPath("/");
			settlement.setMaxAge(60*60*24*2);
			Cookie resource=new Cookie("resource",theSettlement.resource);
			resource.setPath("/");
			resource.setMaxAge(60*60*24*2);
			response.addCookie(settlement);
			response.addCookie(resource);
			model.addAttribute("resource",theSettlement.resource);
			model.addAttribute("settlementName",theSettlement.name);
			model.addAttribute("message",fooCookie+"%");
			Cookie bossState=new Cookie("bossState","inSettlement");
			bossState.setPath("/");
			bossState.setMaxAge(60*60*24*2);
			response.addCookie(bossState);
			return "helloSettlement";
		}
	
		model.addAttribute("leftEnemies",leftEnemiesString);
		int numberOfEnemiesLeft=Integer.parseInt(leftEnemiesString)-1;
		Cookie a=new Cookie("leftEnemies",String.valueOf(numberOfEnemiesLeft));
		a.setPath("/");
		a.setMaxAge(60*60*24*2);
		response.addCookie(a);
		count++;
		logger.debug("Hello Cookie is: "+fooCookie);
		if(count==1) {
		Enemy boundEntity= new Enemy("boundEntity","Bound Entity",1, 78, 12, 15, 3, 20,10);
		Enemy orochi=new Enemy("Orochi","Orochi",1, 73, 14, 17, 2, 20,13);
		Enemy undeadArmy=new Enemy("undeadArmy","Undead Army",1, 53, 6, 10, 2, 7,2);
		Enemy darkKnights=new Enemy("darkKnights","Dark Knights",1, 66, 10, 13, 3, 11,3);
		Enemy insectoid=new Enemy("Insectoid","Insectoid",1,88,8,13,4,20,12);
		Enemy lampLighter=new Enemy("lampLighter","Lamp Lighter",2, 45, 11, 15, 2, 9,7);
		Enemy elementalist=new Enemy("elementalist","Elementalist",2, 59,9,13,3,10,15);
		Enemy warlock=new Enemy("warlock","Warlock",2,52,7,10,2,8,5);
		Enemy tribeMen=new Enemy("tribeMen","Tribe men",1,58,7,11,2,9,5);
		Enemy mutatedWolf=new Enemy("mutatedWolf","Mutated Wolf",1,82,12,15,3,20,13);
		Enemy slugLord=new Enemy("slugLord","Slug Lord",1,78,11,15,2,17,6);
		Enemy fireKnight=new Enemy("fireKnight","Fire Knight",2,87,9,14,5,22,15);
		Enemy fireMage=new Enemy("fireMage","Fire Mage",2,60,9,12,2,11,9);
		Enemy evolvedHumanoid=new Enemy("evolvedHumanoid","Evolved Humanoid",1,57,8,10,2,9,5);
		Enemy swampInsect=new Enemy("swampInsect","Swamp Insect",1,68,9,12,2,14,8);
		Enemy assassin=new Enemy("assassin","Assassin",1,50,7,11,2,8,5);
		Enemy scorpionMother=new Enemy("scorpionMother","Scorpion Mother",1,70,13,15,3,18,8);
		Enemy dispeller=new Enemy("dispeller","Dispeller",2,57,10,13,6,14,9);
		enemies.add(boundEntity);
		enemies.add(orochi);
		enemies.add(undeadArmy);
		enemies.add(darkKnights);
		enemies.add(insectoid);
		enemies.add(elementalist);
		enemies.add(warlock);
		enemies.add(slugLord);
		enemies.add(lampLighter);
		enemies.add(tribeMen);
		enemies.add(mutatedWolf);
		enemies.add(fireKnight);
		enemies.add(fireMage);
		enemies.add(evolvedHumanoid);
		enemies.add(swampInsect);
		enemies.add(assassin);
		enemies.add(scorpionMother);
		enemies.add(dispeller);
		}
		
		
		
		if(count==1)
		{
			Settlement snowyCastle=new Settlement("snowyCastle","Snowy Castle");
			Settlement cliffTown=new Settlement("cliffTown","Cliff Town");
			Settlement caveTown=new Settlement("caveTown","Cave Town");
			Settlement ancientTemple=new Settlement("ancientTemple","Ancient Temple");
			Settlement riverSideCastle=new Settlement("riverSideCastle","Riverside Castle");
			Settlement monkTown=new Settlement("monkTown","Monk Town");
			settlements.add(snowyCastle);
			settlements.add(cliffTown);
			settlements.add(caveTown);
			settlements.add(ancientTemple);
			settlements.add(riverSideCastle);
			settlements.add(monkTown);
			
		}
		
		int theZone=-1;
		int bosses=enemies.size()+2;
		while(bosses>passedMaps.length())
		{
			theZone=chooseZone(0, enemies.size()-1);
			if(!passedMaps.contains(String.valueOf(theZone)))
			{
				break;
			}
		}
		theBoss=enemies.get(theZone);
		int type=theBoss.attackType;
		String attackType="";
		if(type==1)
		{
			attackType="PHYSICAL";
		}
		else 
		{
			attackType="MAGIC";
		}
		strToResource=theBoss.resourcePath;
		logger.debug("the resource path is: "+strToResource);
		String theEnemy="Name: "+theBoss.name+", Attack Type: "+attackType+",  health = "+theBoss.health+", attack = "+theBoss.damageMin+"-"+theBoss.damageMax+", armor = "+theBoss.armor+", critical chance = "+theBoss.critChance+"%"+", Gold reward = "+theBoss.dropsGold;
		cookie=fooCookie;
		String theEnemy2="Name: "+theBoss.name+",  health = "+theBoss.health+", "+type+", attack = "+theBoss.damageMin+"-"+theBoss.damageMax+", armor = "+theBoss.armor+", Gold reward = "+theBoss.dropsGold+", critical chance = "+theBoss.critChance;
		model.addAttribute("message",cookie+"%");
		model.addAttribute("enemyInfo",theEnemy);
		model.addAttribute("resource",strToResource);
		Cookie c2=new Cookie("hero",cookie);
		Cookie c3=new Cookie("resource",strToResource);
		Cookie c4=new Cookie("enemy",theEnemy2);
		Cookie passedRegions=new Cookie("passedMaps",passedMaps+String.valueOf(theZone));
		Cookie bossState=new Cookie("bossState","alive");
		bossState.setPath("/");
		bossState.setMaxAge(60*60*24*2);
		c4.setPath("/");
		c4.setMaxAge(60*60*24*2);
		c3.setPath("/");
		c3.setMaxAge(60*60*24*2);
		c2.setPath("/");
		c2.setMaxAge(60*60*24*2);
		passedRegions.setPath("/");
		passedRegions.setMaxAge(60*60*24*2);
		response.addCookie(c2);
		response.addCookie(c3);
		response.addCookie(c4);
		response.addCookie(passedRegions);
		response.addCookie(bossState);
		return "hello";
	}
}
