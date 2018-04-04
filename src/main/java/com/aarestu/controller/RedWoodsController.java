package com.aarestu.controller;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("session")
public class RedWoodsController {
	int count=0;
	ArrayList<Enemy> enemies= new ArrayList<Enemy>();
	ArrayList<Settlement> settlements=new ArrayList<Settlement>();
	String strToResource;
	Enemy theBoss;
	Settlement theSettlement;
	ArrayList<Enemy> bosses= new ArrayList<Enemy>();
	int settlementTimer=100;
	Hero hero;

	@RequestMapping(value = "/redWoods", method = RequestMethod.GET)
	public String index(ModelMap model, @CookieValue(value = "hero", defaultValue = "defaultHero") String fooCookie,
			@CookieValue(value = "settlement", defaultValue = "0") String settlementCookie,
			@CookieValue(value = "bossState", defaultValue = "dead") String bossStateCookie,
			@CookieValue(value = "leftEnemies", defaultValue = "15") String leftEnemiesString,
			@CookieValue(value = "passedMaps", defaultValue = "-9") String passedMaps,
			@CookieValue(value = "firstBoss", defaultValue = "notReached") String firstBossCookie,
			@CookieValue("spellCast")String spellCastCookie,
			HttpServletResponse response) {
		hero=Hero.fromCookie(fooCookie);
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
		if (hero.heroClass.equals("Giant")) {
			hero.hp += hero.mana;
			hero.maxHp += hero.maxMana;
			hero.hpRegen += hero.manaRegen;
			int rageHealth=(int)(hero.rage*0.10);
			model.addAttribute("rage","You lose all rage and Increase your Current and Max Health with "+String.valueOf(rageHealth));
			hero.maxHp+=rageHealth;
			hero.hp+=rageHealth;
			hero.rage=0;
			hero.mana = 0;
			hero.manaRegen = 0;
			hero.maxMana = 0;
			Cookie heroCookie=hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60*60*24*2);
			response.addCookie(heroCookie);
		}		
		if(hero.enemyEncountersLeft<=0) {

			Enemy megalodon=new Enemy("megalodon","Megalodon",1,375,41,50,13,100,18);
			Enemy oceanHorror=new Enemy("oceanHorror","Ocean Horror",2,360,43,52,12,100,17);
			bosses.add(megalodon);
			bosses.add(oceanHorror);
			theBoss=bosses.get(Utils.attack(0,bosses.size()-1));
			Cookie firstBoss=new Cookie("firstBoss","fighting");
			firstBoss.setPath("/");
			firstBoss.setMaxAge(60*60*24);
			response.addCookie(firstBoss);

			strToResource=theBoss.resourcePath;
			String theEnemy=theBoss.displayText();
			String theEnemy2=theBoss.toCookie();
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("enemyInfo",theEnemy);
			model.addAttribute("resource",strToResource);
			Cookie c2=hero.createCookie();
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
			ArrayList<String[]> skills=hero.generateHeroSkillText(hero, response);
			model.addAttribute("skill1",skills.get(0)[0]);
			model.addAttribute("skill2",skills.get(1)[0]);
			model.addAttribute("tooltip1",skills.get(0)[1]);
			model.addAttribute("tooltip2",skills.get(1)[1]);
			return "hello";
		}
		if(hero.enemyEncountersLeft==20) {
			Cookie firstBoss=new Cookie("firstBoss","notReached");
			firstBoss.setPath("/");
			firstBoss.setMaxAge(60*60*24*2);
			response.addCookie(firstBoss);
		}

		if (count == 0) {
			count++;
			enemies.add(new Enemy("moltenGiant","Molten Giant",1,145,24,30,10,45,13));
			enemies.add(new Enemy("gryphonRider","Gryphon Rider",1,138,22,28,8,40,18));
			enemies.add(new Enemy("undeadElite","Undead Elite",1,125,20,26,7,34,15));
			enemies.add(new Enemy("archDemon","Arch Demon",2,210,29,37,11,68,10));
			enemies.add(new Enemy("twoHeadedGiants","Two Headed Giants",1,190,25,32,10,51,11 ));
			enemies.add(new Enemy("DemigodWolf","Demigod Wolf",2,192,26,33,8,54,15));
			enemies.add(new Enemy("lightEntity","Light Entity",2,270,20,26,0,49,20));
			enemies.add(new Enemy("northernRanger","Northern Ranger",1,130,21,27,8,39,12));
			enemies.add(new Enemy("summoner","Summoner",2,157,28,36,8,53,14));
			enemies.add(new Enemy("tormentor","Tormentor",1,140,26,31,8,46,16));
			enemies.add(new Enemy("undeadHorror","Undead Horror",1,136,22,27,8,38,15));
			enemies.add(new Enemy("angryGiant","Angry Giant",1,200,25,30,10,61,10));
			enemies.add(new Enemy("giganticWorm","Gigantic Worm",2,210,26,32,10,65,15));
			enemies.add(new Enemy("metalGiant","Metal Giant",1,220,24,30,13,64,11));
			enemies.add(new Enemy("SkeletalHorror","Skeletal Horror",1,175,25,29,9,52,13));
			enemies.add(new Enemy("evolvedOrochi","Evolved Orochi",1,192,28,36,10,68,18));
			enemies.add(new Enemy("colossalElemental","Colossal Elemental",2,205,27,33,8,59,12));
			enemies.add(new Enemy("waterHorror","Water Horror",2,185,26,30,9,55,14));
			enemies.add(new Enemy("graveyardHorror","Graveyard Horror",1,195,28,33,11,61,13));
			enemies.add(new Enemy("fireDrake","Fire Drake",2,190,26,32,10,59,16));
			enemies.add(new Enemy("wrathMage","Wraith Mage",2,130,21,27,7,35,12));
			
			Settlement mountainCastle=new Settlement("mountainCastle","Mountain Castle");
			settlements.add(mountainCastle);
			settlements.add(new Settlement("PitTown","Pit Town"));
			settlements.add(new Settlement("cloudCity","Cloud City"));
			settlements.add(new Settlement("lakeCastle","Lake Castle"));
			settlements.add(new Settlement("mountainFortification","Mountain Fortification"));
			settlements.add(new Settlement("harborTown","Harbor Town"));
			settlements.add(new Settlement("towerCity","Tower City"));
			settlements.add(new Settlement("metropolitan","Metropolitan"));
			Cookie passedMapss=new Cookie("passedMaps","-9");
			passedMapss.setPath("/");
			passedMapss.setMaxAge(60*60*24*2);
			response.addCookie(passedMapss);
		}
		
		
		Cookie passed=new Cookie("passed","passed");
		passed.setPath("/");
		passed.setMaxAge(60*60*24*2);
		response.addCookie(passed);
		if(bossStateCookie.equals("alive"))
		{
			model.addAttribute("cheater","No cheating : )");
			return "hello";		
		}


		settlementTimer--;

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
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("leftEnemies",leftEnemiesString);
			Cookie bossState=new Cookie("bossState","inSettlement");
			bossState.setPath("/");
			bossState.setMaxAge(60*60*24*2);
			response.addCookie(bossState);
			return "helloSettlement";
		}
		if(bossStateCookie.equals("inSettlement"))
		{
			String[] cookieArr=settlementCookie.split(",");
			model.addAttribute("resource",cookieArr[0]);
			model.addAttribute("settlementName",cookieArr[1]);
			model.addAttribute("message",hero.createDisplayText());
			model.addAttribute("cheating","No Cheating : )");
			return "helloSettlement";
		}

//		if(Utils.randomBool(7))
//		{
//			Cookie eligebleForEvent=new Cookie("eventState","eligebleForNewEvent");
//			eligebleForEvent.setPath("/");
//			eligebleForEvent.setMaxAge(60*60*24*2);
//			response.addCookie(eligebleForEvent);
//			settlementTimer++;
//			return "eventHello";
//		}
		hero.enemyEncountersLeft -= 1;
	
		int theZone=0;
//		int bosses=enemies.size()+2;
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
				
				break;
			}
			randomCount = 0;
		}
		theBoss=enemies.get(theZone);

		//strToResource=theBoss.resourcePath;
		
		String theEnemy=theBoss.displayText();
		
		String theEnemy2=theBoss.toCookie();
		
		String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
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
		
		model.addAttribute("enemyInfo",theEnemy);
		model.addAttribute("resource",theBoss.resourcePath);
		Cookie c2=hero.createCookie();
		Cookie c3=new Cookie("resource",theBoss.resourcePath); //strToResource;
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
		model.addAttribute("message",hero.createDisplayText());
		return "hello";
	}
}


