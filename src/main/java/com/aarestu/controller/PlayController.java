package com.aarestu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("session")
public class PlayController {
	int count=0;
	ArrayList<Enemy> enemies= new ArrayList<Enemy>();
	ArrayList<Item> items=new ArrayList<Item>();
	ArrayList<Settlement> settlements=new ArrayList<Settlement>();
	String strToResource;
	Enemy theBoss;
	Settlement theSettlement;
	ArrayList<Enemy> bosses= new ArrayList<Enemy>();
	int settlementTimer=101;
	static int deaths=0;
	final static Logger logger=Logger.getLogger(PlayController.class);
	Hero hero=new Hero();
	Hero hero2;
	@RequestMapping(value="/play",method=RequestMethod.GET)
	public String plays(ModelMap model, HttpServletResponse response) {
		settlementTimer=101;
		deaths++;
		logger.debug("deaths count: "+deaths);
		Cookie leftEnemies=new Cookie("leftEnemies","25");
		leftEnemies.setPath("/");
		leftEnemies.setMaxAge(60*60*24*2);
		response.addCookie(leftEnemies);
		Cookie c = hero.createCookie();
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
		model.addAttribute("message", hero.createDisplayText());
		Cookie passedEvents=new Cookie("passedEvents","-9");
		passedEvents.setMaxAge(60*60*24*2);
		passedEvents.setPath("/");
		response.addCookie(passedEvents);
		return "chooseClass";
	}
	
	@RequestMapping("/hello")
	public String index(ModelMap model,@CookieValue(value="hero",defaultValue="defaultHero") String fooCookie,@CookieValue(value="enemy",defaultValue="none")String enemyCookie,@CookieValue(value="settlement",defaultValue="0") String settlementCookie,@CookieValue(value="bossState",defaultValue="dead") String bossStateCookie,@CookieValue(value="leftEnemies",defaultValue="15") String leftEnemiesString,@CookieValue(value="passedMaps",defaultValue="-9") String passedMaps,@CookieValue(value="firstBoss",defaultValue="notReached")String firstBossCookie,@CookieValue(value="spellCast",defaultValue ="none")String spellCastCookie,HttpServletResponse response)
	{

		hero2=Hero.fromCookie(fooCookie);
		Cookie poison=new Cookie("poison","0");
		poison.setPath("/");
		poison.setMaxAge(60*60*24*2);
		response.addCookie(poison);
		Cookie regenCookie=new Cookie("regeneration","0");
		regenCookie.setPath("/");
		regenCookie.setMaxAge(60*60*24*2);
		response.addCookie(regenCookie);
		Cookie golemCookie=new Cookie("golem","0");
		golemCookie.setPath("/");
		golemCookie.setMaxAge(60*60*24*2);
		response.addCookie(golemCookie);
		if(hero2.enemyEncountersLeft==26) {
			Cookie firstBoss=new Cookie("firstBoss","notReached");
			firstBoss.setPath("/");
			firstBoss.setMaxAge(60*60*24*2);
			response.addCookie(firstBoss);
		}
		if(firstBossCookie.equals("fighting") && hero2.enemyEncountersLeft!=26) {
			
			hero2.zone="Red Woods";
			hero2.hp=hero2.maxHp;
			hero2.mana=hero2.maxMana;
			hero2.enemyEncountersLeft=20;
			Cookie zone=hero2.createCookie();
			zone.setMaxAge(60*60*24*2);
			zone.setPath("/");
			response.addCookie(zone);
			return "secondZone";
		}

		if (hero2.heroClass.equals("Giant")) {
			hero2.hp += hero2.mana;
			hero2.maxHp += hero2.maxMana;
			hero2.hpRegen += hero2.manaRegen;
			int rageHealth=(int)(hero2.rage*0.10);
			model.addAttribute("rage","You lose all rage and Increase your Current and Max Health with "+String.valueOf(rageHealth));
			hero2.maxHp+=rageHealth;
			hero2.hp+=rageHealth;
			hero2.rage=0;
			hero2.mana = 0;
			hero2.manaRegen = 0;
			hero2.maxMana = 0;
			Cookie heroCookie=hero2.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60*60*24*2);
			response.addCookie(heroCookie);
		}		
		Cookie passed=new Cookie("passed","passed");
		passed.setPath("/");
		passed.setMaxAge(60*60*24*2);
		response.addCookie(passed);
		if(bossStateCookie.equals("alive"))
		{
			model.addAttribute("cheater","No cheating : )");
			Enemy enemy=Enemy.fromCookie(enemyCookie);
			model.addAttribute("resource",enemy.resourcePath);
			return "hello";		
		}
		if(bossStateCookie.equals("inSettlement"))
		{
			String[] cookieArr=settlementCookie.split(",");
			model.addAttribute("resource",cookieArr[0]);
			model.addAttribute("settlementName",cookieArr[1]);
			model.addAttribute("message",hero2.createDisplayText());
			model.addAttribute("cheating","No Cheating : )");
			return "helloSettlement";
		}

		settlementTimer--;
		logger.debug("settlement Timer count is: "+settlementTimer);
		if(settlementTimer%3==0)
		{
			int settlementIndex=Utils.attack(0,settlements.size()-1);
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
			model.addAttribute("message",hero2.createDisplayText());
			model.addAttribute("leftEnemies",leftEnemiesString);
			Cookie bossState=new Cookie("bossState","inSettlement");
			bossState.setPath("/");
			bossState.setMaxAge(60*60*24*2);
			response.addCookie(bossState);
			return "helloSettlement";
		}
		//finalBoss
		if(hero2.enemyEncountersLeft<=0) {
			Enemy islandShark=new Enemy("islandShark","Island Shark",1,140,25,31,8,50,14);
			Enemy whiteScaleDragon=new Enemy("whiteScaleDragon","Whitescale Dragon",2,145,26,32,7,50,11);
			bosses.add(islandShark);
			bosses.add(whiteScaleDragon);
			theBoss=bosses.get(Utils.attack(0,bosses.size()-1));
//			int type=theBoss.attackType;
			Cookie firstBoss=new Cookie("firstBoss","fighting");
			firstBoss.setPath("/");
			firstBoss.setMaxAge(60*60*24);
			response.addCookie(firstBoss);
			Cookie passedMapss=new Cookie("passedMaps","-1");
			passedMapss.setPath("/");
			passedMapss.setMaxAge(60*60*24*2);
			response.addCookie(passedMapss);
//			String attackType="";
//			if (type == 1) {
//				attackType = "PHYSICAL";
//			} else {
//				attackType = "MAGIC";
//			}
			strToResource=theBoss.resourcePath;
			logger.debug("the resource path is: "+strToResource);
			String theEnemy=theBoss.displayText();
			String theEnemy2=theBoss.toCookie();
			model.addAttribute("message",hero2.createDisplayText());
			model.addAttribute("enemyInfo",theEnemy);
			model.addAttribute("resource",strToResource);
			Cookie c2=hero2.createCookie();
			Cookie c3=new Cookie("resource",strToResource);
			Cookie c4=new Cookie("enemy",theEnemy2);
			Cookie bossState=new Cookie("bossState","alive");
			bossState.setPath("/");
			bossState.setMaxAge(60*60*24*2);
			c4.setPath("/");
			c4.setMaxAge(60*60*24*2);
			c3.setPath("/");
			c3.setMaxAge(60*60*24*2);
			c2.setPath("/");
			c2.setMaxAge(60*60*24*2);
			response.addCookie(c2);
			response.addCookie(c3);
			response.addCookie(c4);
			response.addCookie(bossState);
			model.addAttribute("bossFight","BOSS FIGHT ");
			String[] theSpell=hero2.generateHeroSpellText(hero2,spellCastCookie,response);
			model.addAttribute("spell",theSpell[0]);
			model.addAttribute("tooltip",theSpell[1]);
			Cookie spellCast=new Cookie("spellCast",theSpell[0]);
			spellCast.setPath("/");
			spellCast.setMaxAge(60*60*24*2);
			response.addCookie(spellCast);
			ArrayList<String[]> skills=hero.generateHeroSkillText(hero, response);
			model.addAttribute("skill1",skills.get(0)[0]);
			model.addAttribute("skill2",skills.get(1)[0]);
			model.addAttribute("tooltip1",skills.get(0)[1]);
			model.addAttribute("tooltip2",skills.get(1)[1]);
			return "hello";
		}
		//Event
		if(Utils.randomBool(5))
		{
			Cookie eligebleForEvent=new Cookie("eventState","eligebleForNewEvent");
			eligebleForEvent.setPath("/");
			eligebleForEvent.setMaxAge(60*60*24*2);
			response.addCookie(eligebleForEvent);
			settlementTimer++;
			return "eventHello";
		}


		hero2.enemyEncountersLeft -= 1;
		count++;
		logger.debug("Hello Cookie is: " + fooCookie);
		if (count == 1) {
			enemies.add(new Enemy("boundEntity", "Bound Entity", 1, 82, 14, 17, 3, 20, 8));
			enemies.add(new Enemy("Orochi", "Orochi", 1, 77, 14, 17, 2, 20, 13));
			enemies.add(new Enemy("undeadArmy", "Undead Army", 1, 52, 9, 13, 2, 7, 2));
			enemies.add(new Enemy("spectre", "Spectre", 2, 70, 14, 17, 3, 17, 9));
			enemies.add(new Enemy("darkKnights", "Dark Knights", 1, 66, 13, 15, 3, 11, 3));
			enemies.add(new Enemy("Insectoid", "Insectoid", 1, 92, 10, 15, 4, 20, 10));
			enemies.add(new Enemy("elementalist", "Elementalist", 2, 61, 10, 14, 3, 10, 13));
			enemies.add(new Enemy("warlock", "Warlock", 2, 54, 12, 14, 2, 8, 5));
			enemies.add(new Enemy("slugLord", "Slug Lord", 1, 82, 13, 17, 2, 17, 6));
			enemies.add(new Enemy("lampLighter", "Lamp Lighter", 2, 66, 13, 17, 2, 12, 8));
			enemies.add(new Enemy("tribeMen", "Tribe men", 1, 60, 10, 14, 2, 9, 5));
			enemies.add(new Enemy("mutatedWolf", "Mutated Wolf", 1, 86, 14, 17, 3, 20, 11));
			enemies.add(new Enemy("fireKnight", "Fire Knight", 2, 91, 13, 17, 5, 22, 11));
			enemies.add(new Enemy("fireMage", "Fire Mage", 2, 62, 12, 15, 2, 11, 8));
			enemies.add(new Enemy("evolvedHumanoid", "Evolved Humanoid", 1, 59, 11, 13, 2, 9, 5));
			enemies.add(new Enemy("swampInsect", "Swamp Insect", 1, 70, 12, 15, 2, 14, 8));
			enemies.add(new Enemy("assassin", "Assassin", 1, 52, 10, 14, 2, 8, 5));
			enemies.add(new Enemy("scorpionMother", "Scorpion Mother", 1, 74, 15, 18, 3, 18, 8));
			enemies.add(new Enemy("dispeller", "Dispeller", 2, 59, 13, 16, 6, 14, 9));
			enemies.add(new Enemy("reptile", "Reptile", 1, 89, 14, 18, 3, 20, 11));
			enemies.add(new Enemy("disciple", "Disciple", 2, 61, 12, 15, 2, 11, 8));
			enemies.add(new Enemy("trollWarrior", "Troll Warrior", 1, 58, 11, 14, 3, 10, 7));
			enemies.add(new Enemy("cutthroat", "Cutthroat", 1, 56, 10, 14, 2, 8, 5));
			enemies.add(new Enemy("madMan", "Madman", 1, 56, 10, 14, 2, 10, 18));
			enemies.add(new Enemy("alienRefugee", "Alien Refugee", 2, 70, 13, 16, 2, 14, 7));
			enemies.add(new Enemy("corruptedCentaur", "Corrupted Centaur", 1, 64, 13, 15, 2, 12, 6));
			enemies.add(new Enemy("riteOfTheStorm", "Rite of the Storm", 2, 78, 14, 18, 4, 20, 9));
			enemies.add(new Enemy("iceLizard", "Ice Lizard", 2, 75, 13, 17, 4, 17, 6));
			enemies.add(new Enemy("seaSerpent", "Sea Serpent",1,70,12,16,3,16,8));
			enemies.add(new Enemy("stoneGiant","Stone Giant",1,82,12,16,6,20,8));
			Settlement snowyCastle=new Settlement("snowyCastle","Snowy Castle");
			Settlement cliffTown=new Settlement("cliffTown","Cliff Town");
			Settlement caveTown=new Settlement("caveTown","Cave Town");
			Settlement ancientTemple=new Settlement("ancientTemple","Ancient Temple");
			Settlement riverSideCastle=new Settlement("riverSideCastle","Riverside Castle");
			Settlement monkTown=new Settlement("monkTown","Monk Town");
			Settlement mountainPassSanctuary=new Settlement("mountainPassSanctuary","Mountain pass Sanctuary");
			Settlement theCapital=new Settlement("theCapital","The Capital");
			settlements.add(snowyCastle);
			settlements.add(cliffTown);
			settlements.add(caveTown);
			settlements.add(ancientTemple);
			settlements.add(riverSideCastle);
			settlements.add(monkTown);
			settlements.add(mountainPassSanctuary);
			settlements.add(theCapital);
		}

		
		int theZone=0;
		//int bosses=enemies.size()+2;
		String[] encounteredBosses=passedMaps.split(",");
		int randomCount=0;
		while (true) {
			theZone = Utils.attack(0, enemies.size() - 1);
			for (int i = 0; i < encounteredBosses.length; i++) {
				if (encounteredBosses[i].equals(String.valueOf(theZone))) {
					randomCount++;
				}
			}
			if (randomCount == 0) {
				logger.debug("boss index is: "+theZone);
				break;
			}
			randomCount = 0;
		}
		theBoss=enemies.get(theZone);
//		int type=theBoss.attackType;
//		String attackType="";
//		if (type == 1) {
//			attackType = "PHYSICAL";
//		} else {
//			attackType = "MAGIC";
//		}
		
		String[] theSpell=hero2.generateHeroSpellText(hero2,spellCastCookie,response);
		model.addAttribute("spell",theSpell[0]);
		model.addAttribute("tooltip",theSpell[1]);
		Cookie spellCast=new Cookie("spellCast",theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60*60*24*2);
		response.addCookie(spellCast);
		ArrayList<String[]> skills=hero2.generateHeroSkillText(hero, response);
		model.addAttribute("skill1",skills.get(0)[0]);
		model.addAttribute("skill2",skills.get(1)[0]);
		model.addAttribute("tooltip1",skills.get(0)[1]);
		model.addAttribute("tooltip2",skills.get(1)[1]);
		
		strToResource=theBoss.resourcePath;
		String theEnemy=theBoss.displayText();
		String theEnemy2=theBoss.toCookie();
		model.addAttribute("enemyInfo",theEnemy);
		model.addAttribute("resource",strToResource);
		Cookie c2=hero2.createCookie();
		Cookie c3=new Cookie("resource",strToResource);
		Cookie c4=new Cookie("enemy",theEnemy2);
		Cookie passedRegions=new Cookie("passedMaps",passedMaps+","+String.valueOf(theZone));
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
		model.addAttribute("message",hero2.createDisplayText());
		return "hello";
	}
}
