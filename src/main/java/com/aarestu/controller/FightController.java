package com.aarestu.controller;

import java.util.ArrayList;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("session")
public class FightController {
	String theBadCookie = "";
	String enemyName = "";
	Hero hero;
	Enemy enemy;
	final static Logger logger = Logger.getLogger(FightController.class);

	private String fightInternal(ModelMap model, String fooCookie, String badCookie, String bossStateCookie,
			String resourceCookie, String spellCastCookie, String skillsCookie, HttpServletResponse response,
			String poisonCookie, String regenCookie,String golemCookie, int skillNumber) {
		if (bossStateCookie.equals("dead")) {
			model.addAttribute("cheating", "No cheating : )");
			model.addAttribute("resource", resourceCookie);
			Hero hero = Hero.fromCookie(fooCookie);
			if (hero.zone.equals("Green Woods")) {
				model.addAttribute("zone", "hello");
			} else {
				model.addAttribute("zone", "redWoods");
			}
			return "fightvictory";
		}
		hero = Hero.fromCookie(fooCookie);
		if (hero == null) {
			return "defeat";
		}
		model.addAttribute("resource", resourceCookie);
		enemy = Enemy.fromCookie(badCookie);
		if(enemy.health<=0) {
			model.addAttribute("cheating","Enemy already Dead");
			return "fightvictory";
		}
		if(!poisonCookie.equals("0")) {
			enemy.health-=Integer.parseInt(poisonCookie);
			model.addAttribute("poison","The poison damages the enemy dealing "+poisonCookie+" damage");
		}
		if(!regenCookie.equals("0")) {
			hero.hp+=Integer.parseInt(regenCookie);
			model.addAttribute("poison","You Regenerate "+regenCookie+" health");
		}
		if(!golemCookie.equals("0")) {
			int tempEnemyHealth=enemy.health;
			int golemDamage=Integer.parseInt(golemCookie);
			String golemCritically="";
			if(Utils.critical(hero.critChance)) {
				golemDamage=(int)(golemDamage*1.8);
				golemCritically=" CRITICALLY";
			}
			golemDamage-=enemy.armor;
			enemy.health-=golemDamage;
			tempEnemyHealth=enemy.health;
			model.addAttribute("yourPetAttacks","Flesh Golem attacks");
			model.addAttribute("dealing"," dealing ");
				model.addAttribute("petCritically",golemCritically);
				tempEnemyHealth-=enemy.health;
				model.addAttribute("petDamage",golemDamage+" Damage");
		}
		String[] theSpell = hero.generateHeroSpellText(hero, spellCastCookie, response);
		model.addAttribute("spell", theSpell[0]);
		model.addAttribute("tooltip", theSpell[1]);
		Cookie spellCast = new Cookie("spellCast", theSpell[0]);
		spellCast.setPath("/");
		spellCast.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(spellCast);
		String[] skills = skillsCookie.split(",");
		String theSkill = skills[skillNumber];
		ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
		Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
		newSkillsCookie.setPath("/");
		newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(newSkillsCookie);
		model.addAttribute("skill1", newSkills.get(0)[0]);
		model.addAttribute("skill2", newSkills.get(1)[0]);
		model.addAttribute("tooltip1", newSkills.get(0)[1]);
		model.addAttribute("tooltip2", newSkills.get(1)[1]);
		if (theSkill.equals("Attack")) {

			return fight(enemy, response, model, hero);
		} else if (theSkill.equals("Shield Throw")) {
			int defenses = 0;
			int tempHeroArmor = hero.armor;
			int tempHeroMagicResist = hero.magicResist;
			defenses = (hero.armor + hero.magicResist)*2;
			hero.armor = 0;
			hero.magicResist = 0;
			int tempHeroCritChance = hero.critChance;
			int tempEnemyHealth = enemy.health;
			int tempHealth = hero.hp;
			hero.critChance = hero.critChance + defenses;
			hero.heroAttack(hero, enemy, model, response);
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.armor = tempHeroArmor;
				hero.magicResist=tempHeroMagicResist;
				hero.critChance=tempHeroCritChance;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
			}
			enemy.enemyAttack(hero, enemy, model, response);
			hero.critChance = tempHeroCritChance;
			hero.armor = tempHeroArmor;
			hero.magicResist = tempHeroMagicResist;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			} 
		} else if(theSkill.equals("Armor Deflection")) {
			
			int tempHeroArmor = hero.armor;
			int tempHeroMagicResist=hero.magicResist;
			hero.armor =(int)(hero.armor+(hero.magicResist*0.5));
			hero.magicResist=(int)(hero.magicResist*0.5);
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			hero.attackMin=(hero.armor*2);
			hero.attackMax=(hero.armor*2);
			int tempEnemyHealth = enemy.health;
			int tempHealth = hero.hp;
			enemy=hero.heroAttack(hero, enemy, model, response);
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.armor = tempHeroArmor;
				hero.magicResist=tempHeroMagicResist;
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
			}
			enemy.enemyAttack(hero, enemy, model, response);
			hero.armor = tempHeroArmor;
			hero.magicResist=tempHeroMagicResist;
			hero.attackMin=tempHeroAttackMin;
			hero.attackMax=tempHeroAttackMax;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}else if(theSkill.equals("Magic Deflection")) {
			int tempHeroMagicResist = hero.magicResist;
			int tempHeroArmor=hero.armor;
			hero.magicResist =(int)(hero.magicResist+(hero.armor*0.5));
			hero.armor=(int)(hero.armor*0.5);
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			hero.attackMin=(hero.magicResist*2);
			hero.attackMax=(hero.magicResist*2);
			int tempEnemyHealth = enemy.health;
			int tempHealth = hero.hp;
			hero.heroAttack(hero, enemy, model, response);
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.magicResist = tempHeroMagicResist;
				hero.armor=tempHeroArmor;
				
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
				
			} 
			enemy.enemyAttack(hero, enemy, model, response);
			hero.magicResist = tempHeroMagicResist;
			hero.armor=tempHeroArmor;
			hero.attackMin=tempHeroAttackMin;
			hero.attackMax=tempHeroAttackMax;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}else if(theSkill.equals("Lucky hit")) {
			int tempHeroCritChance = hero.critChance;
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			hero.attackMin=(int)(hero.attackMin*0.90);
			hero.attackMax=(int)(hero.attackMax*0.90);
			hero.critChance+=10;
			int tempEnemyHealth = enemy.health;
			int tempHealth = hero.hp;
			hero.heroAttack(hero, enemy, model, response);
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				hero.critChance=tempHeroCritChance;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
				
			} 
			enemy.enemyAttack(hero, enemy, model, response);
			hero.attackMin=tempHeroAttackMin;
			hero.attackMax=tempHeroAttackMax;
			hero.critChance=tempHeroCritChance;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}else if(theSkill.equals("Dying Blow")) {
			int tempHeroCritChance = hero.critChance;
			
			hero.critChance+=25;
			int tempEnemyHealth = enemy.health;
			hero.hp=(int)(hero.hp*0.5);
			int tempHealth = hero.hp;
			hero.heroAttack(hero, enemy, model, response);
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.critChance=tempHeroCritChance;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
				
			} 
			enemy.enemyAttack(hero, enemy, model, response);
			hero.critChance=tempHeroCritChance;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}else if(theSkill.equals("Life Steal")) {
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			int tempEnemyHealth = enemy.health;
			hero.attackMin=(int)(hero.attackMin*0.80);
			hero.attackMax=(int)(hero.attackMax*0.80);
			hero.heroAttack(hero, enemy, model, response);
			String damageDealt=String.valueOf(model.get("damageDealt"));
			int lifeSteal=(int)(Integer.parseInt(damageDealt)*0.20);
			if(hero.hp+lifeSteal<=hero.maxHp) {
				hero.hp+=lifeSteal;
				}else {
					hero.hp=hero.maxHp;
				}
			int tempHealth = hero.hp;
			model.addAttribute("lifesteal"," and healing yourself for "+String.valueOf(lifeSteal));
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
				
			} 
			enemy.enemyAttack(hero, enemy, model, response);
			hero.attackMin=tempHeroAttackMin;
			hero.attackMax=tempHeroAttackMax;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}

			 else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}else if(theSkill.equals("Mana Steal")) {
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			int tempEnemyHealth = enemy.health;
			hero.attackMin=(int)(hero.attackMin*0.80);
			hero.attackMax=(int)(hero.attackMax*0.80);
			hero.heroAttack(hero, enemy, model, response);
			String damageDealt=String.valueOf(model.get("damageDealt"));
			int lifeSteal=(int)(Integer.parseInt(damageDealt)*0.20);
			if(hero.heroClass.equals("Giant")) {
				if(hero.hp+lifeSteal<=hero.maxHp) {
					hero.hp+=lifeSteal;
				}else {
					hero.hp=hero.maxHp;
				}
			}else {
			if(hero.mana+lifeSteal<=hero.maxMana) {
				hero.mana+=lifeSteal;
				}else {
					hero.mana=hero.maxMana;
				}
			}
			int tempHealth = hero.hp;
			model.addAttribute("manasteal"," and restoring "+String.valueOf(lifeSteal)+" mana");
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
				
			} 
			enemy.enemyAttack(hero, enemy, model, response);
			hero.attackMin=tempHeroAttackMin;
			hero.attackMax=tempHeroAttackMax;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}else if(theSkill.equals("Empowered Attack")) {
			
			
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			int tempHeroArmor=hero.armor;
			int tempHeroMagicResist=hero.magicResist;
			int tempEnemyHealth = enemy.health;
			int tempHealth = hero.hp;
			hero.attackMin=(int)(hero.attackMin*1.2);
			hero.attackMax=(int)(hero.attackMax*1.2);
			hero.armor=(int)(hero.armor*0.5);
			hero.magicResist=(int)(hero.magicResist*0.5);
			hero.heroAttack(hero, enemy, model, response);
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				hero.armor=tempHeroArmor;
				hero.magicResist=tempHeroMagicResist;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
				
			} 
			enemy.enemyAttack(hero, enemy, model, response);
			hero.attackMin=tempHeroAttackMin;
			hero.attackMax=tempHeroAttackMax;
			hero.armor=tempHeroArmor;
			hero.magicResist=tempHeroMagicResist;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			}
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}else if(theSkill.equals("Double Trouble")) {
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			int tempEnemyHealth = enemy.health;
			int tempHealth = hero.hp;
			hero.attackMin=(int)(hero.attackMin*0.7);
			hero.attackMax=(int)(hero.attackMax*0.7);
			int count=0;
			if(Utils.critical(75)) {
				count++;
				hero.heroAttack(hero, enemy, model, response);
			}
			if(enemy.health<=0) {
				model.addAttribute("missedHits"," From both attacks you manage to hit the enemy "+String.valueOf(count)+" times");
				model.addAttribute("damageDealt",String.valueOf((tempEnemyHealth-enemy.health)));
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
			}
			if(Utils.critical(75)) {
				count++;
				hero.heroAttack(hero, enemy, model, response);
			}
			model.addAttribute("missedHits"," From both attacks you manage to hit the enemy "+String.valueOf(count)+" times");
			model.addAttribute("damageDealt",String.valueOf((tempEnemyHealth-enemy.health)));
			
			Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60*60*24*2);
			response.addCookie(theEnemy);
			if(enemy.health<=0) {
				Cookie bossState=new Cookie("bossState","dead");
				bossState.setPath("/");
				bossState.setMaxAge(60*60*24*2);
				response.addCookie(bossState);	
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				
				Cookie heroCookie = hero.createCookie();
				heroCookie.setPath("/");
				heroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroCookie);
				return "fightvictory";
				
			} 
			enemy.enemyAttack(hero, enemy, model, response);
			hero.attackMin=tempHeroAttackMin;
			hero.attackMax=tempHeroAttackMax;
			Cookie heroCookie = hero.createCookie();
			heroCookie.setPath("/");
			heroCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(heroCookie);
			if (hero.hp <= 0) {
				return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
			} else if (enemy.health <= 0) {
				return "fightvictory";
			} else {
				return "fight";
			}
		}
		return "fight";
	}
	

	@RequestMapping(value = "/fight1", method = RequestMethod.GET)
	public String fight(ModelMap model, @CookieValue("hero") String fooCookie,
			@CookieValue(value = "enemy", defaultValue = "-1001") String badCookie,
			@CookieValue(value = "resource", defaultValue = "-1001") String resourceCookie,
			@CookieValue(value = "bossState") String bossStateCookie, @CookieValue("spellCast") String spellCastCookie,
			@CookieValue("skills") String skillsCookie, HttpServletResponse response,
			@CookieValue(value="poison", defaultValue="0")String poisonCookie,
			@CookieValue(value="regeneration",defaultValue="0")String regenCookie,
			@CookieValue(value="golem",defaultValue="0")String golemCookie){
		return fightInternal(model,fooCookie,badCookie,bossStateCookie,resourceCookie,spellCastCookie,skillsCookie,response,poisonCookie,regenCookie,golemCookie,0);
	}

	@RequestMapping(value = "/fight2", method = RequestMethod.GET)
	public String fight2(ModelMap model, @CookieValue("hero") String fooCookie,
			@CookieValue(value = "enemy", defaultValue = "-1001") String badCookie,
			@CookieValue(value = "resource", defaultValue = "-1001") String resourceCookie,
			@CookieValue(value = "bossState") String bossStateCookie, @CookieValue("spellCast") String spellCastCookie,
			@CookieValue("skills") String skillsCookie, HttpServletResponse response,
			@CookieValue(value="poison", defaultValue="0")String poisonCookie,
			@CookieValue(value="regeneration",defaultValue="0")String regenCookie,
			@CookieValue(value="golem",defaultValue="0")String golemCookie){
		return fightInternal(model,fooCookie,badCookie,bossStateCookie,resourceCookie,spellCastCookie,skillsCookie,response,poisonCookie,regenCookie,golemCookie,1);
	}

	String fight(Enemy enemy, HttpServletResponse response, ModelMap model, Hero hero) {
		int tempEnemyHealth = enemy.health;
		int tempHealth = hero.hp;

		hero.heroAttack(hero, enemy, model, response);
		if (enemy.health <= 0) {
			return "fightvictory";
		} else {
			enemy.enemyAttack(hero, enemy, model, response);
		}
		if (hero.hp <= 0) {
			return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
		}
		Cookie c = hero.createCookie();
		theBadCookie = enemy.toCookie();
		Cookie e = new Cookie("enemy", theBadCookie);
		e.setMaxAge(60 * 60 * 24 * 2);
		e.setPath("/");
		c.setPath("/");
		c.setMaxAge(60 * 60 * 24 * 2);
		response.addCookie(c);
		response.addCookie(e);
		return "fight";
	}

	@RequestMapping(value = "/fightWithSpell", method = RequestMethod.GET)
	String fightWithSpell(ModelMap model, HttpServletResponse response, @CookieValue("enemy") String enemyCookie,
			@CookieValue("resource") String resourceCookie, @CookieValue("hero") String heroCookie,
			@CookieValue("spellCast") String spellCastCookie, @CookieValue("skills") String skillsCookie,
			@CookieValue(value="poison", defaultValue="0")String poisonCookie,
			@CookieValue(value="regeneration",defaultValue="0")String regenCookie,
			@CookieValue(value="golem",defaultValue="0")String golemCookie) {
		Hero hero = Hero.fromCookie(heroCookie);
		Enemy enemy = Enemy.fromCookie(enemyCookie);
		if(hero.zone.equals("Green Woods")) {
		model.addAttribute("zone","hello");
		}else {
			model.addAttribute("zone","redWoods");
		}
		if(enemy.health<=0) {
			model.addAttribute("resource",enemy.resourcePath);
			model.addAttribute("cheating","Enemy already Dead");
			return "fightvictory";
		}
		if(!poisonCookie.equals("0")) {
			enemy.health-=Integer.parseInt(poisonCookie);
			model.addAttribute("poison","The poison damages the enemy dealing "+poisonCookie+" damage");
		}
		if(!regenCookie.equals("0")) {
			hero.hp+=Integer.parseInt(regenCookie);
			model.addAttribute("poison","You Regenerate "+regenCookie+" health");
		}
		if(!golemCookie.equals("0")) {
			int tempEnemyHealth=enemy.health;
			int golemDamage=Integer.parseInt(golemCookie);
			String golemCritically="";
			if(Utils.critical(hero.critChance)) {
				golemDamage=(int)(golemDamage*1.8);
				golemCritically=" CRITICALLY";
			}
			golemDamage-=enemy.armor;
			enemy.health-=golemDamage;
			tempEnemyHealth=enemy.health;
			model.addAttribute("yourPetAttacks","Flesh Golem attacks");
			model.addAttribute("dealing"," dealing ");
				model.addAttribute("petCritically",golemCritically);
				tempEnemyHealth-=enemy.health;
				model.addAttribute("petDamage",golemDamage+" Damage");
		}
		model.addAttribute("resource", resourceCookie);

		if (hero.heroClass.equals("Mage")) {
			if (spellCastCookie.equals("Fireball")) {
//				model.addAttribute("spell", "Fireball");
				if (hero.mana - 30 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";

				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				int tempEnemyHealth=enemy.health;
				int tempHealth=hero.hp;
				enemy = hero.mageFireball(hero, enemy, model, response, spellCastCookie);
				Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60*60*24*2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold+=enemy.dropsGold;
					Cookie heroNewCookie=hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60*60*24*2);
					response.addCookie(heroNewCookie);
					Cookie bossState=new Cookie("bossState","dead");
					bossState.setPath("/");
					bossState.setMaxAge(60*60*24*2);
					response.addCookie(bossState);
					model.addAttribute("message2",hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				}
				 else {
					 hero=enemy.enemyAttack(hero, enemy, model, response);
					 if(hero.hp<=0) {
						 return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					 }
					 Cookie heroNewCookie=hero.createCookie();
					 heroNewCookie.setPath("/");
					 heroNewCookie.setMaxAge(60*60*24*2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}
			} else if (spellCastCookie.equals("Portal")) {
				if (hero.mana - 30 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				if(enemy.name.equals("Island Shark") || enemy.name.equals("Whitescale Dragon") || enemy.name.equals("Ocean Horror") || enemy.name.equals("Megalodon")) {
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "cannotTeleport";
									
				}
				hero.magePortal(hero, model, response);
				Cookie c = hero.createCookie();
				c.setPath("/");
				c.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(c);
				return "escapedWithPortal";
			} else if (spellCastCookie.equals("Freezing Touch")) {
				if (hero.mana - 40 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
//				int tempEnemyHealth = enemy.health;
//				int tempHealth = hero.hp;
				enemy = hero.mageFreezingTouch(hero, enemy, model, response, spellCastCookie);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "stunnedEnemy";
				}
			} else if (spellCastCookie.equals("Magic Affinity")) {
				if (hero.mana - 50 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				int tempEnemyHealth = enemy.health;
				int tempHealth = hero.hp;
				enemy = hero.mageMagicAffinity(hero, enemy, model, response, spellCastCookie);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}

			}
		}

		if (hero.heroClass.equals("Warrior")) {
			if (spellCastCookie.equals("Endurance")) {
				if (hero.mana - 40 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				hero.mana-=40;
				hero.warriorEndurance(hero, enemy, model, response);
				int tempHealth=hero.hp;
				int tempEnemyHealth=enemy.health;
				String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				response.addCookie(newSkillsCookie);
				Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60*60*24*2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold+=enemy.dropsGold;
					Cookie heroNewCookie=hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60*60*24*2);
					response.addCookie(heroNewCookie);
					Cookie bossState=new Cookie("bossState","dead");
					bossState.setPath("/");
					bossState.setMaxAge(60*60*24*2);
					response.addCookie(bossState);
					model.addAttribute("message2",hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				}
				 else {
					 hero=enemy.enemyAttack(hero, enemy, model, response);
					 if(hero.hp<=0) {
						 return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					 }
					 Cookie heroNewCookie=hero.createCookie();
					 heroNewCookie.setPath("/");
					 heroNewCookie.setMaxAge(60*60*24*2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}
				
			}else if(spellCastCookie.equals("Shield Bash")) {
				if (hero.mana - 40 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				enemy = hero.warriorShieldBash(hero, enemy, model, response,spellCastCookie);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "stunnedEnemy";
				}
			}else if(spellCastCookie.equals("Plate Armor")) {
					if (hero.mana - 30 < 0) {
						String[] skillsArr = skillsCookie.split(",");
						model.addAttribute("skill1", skillsArr[0]);
						model.addAttribute("skill2", skillsArr[1]);
						model.addAttribute("message2", hero.createDisplayText());
						return "noMana";

					}
					hero.mana -= 30;
					ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
					Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
					newSkillsCookie.setPath("/");
					newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(newSkillsCookie);
					model.addAttribute("skill1", newSkills.get(0)[0]);
					model.addAttribute("skill2", newSkills.get(1)[0]);
					model.addAttribute("tooltip1", newSkills.get(0)[1]);
					model.addAttribute("tooltip2", newSkills.get(1)[1]);
					String[]theSpell=hero.generateHeroSpellText(hero, spellCastCookie, response);
					model.addAttribute("spell",theSpell[0]);
					model.addAttribute("tooltip",theSpell[1]);
					Cookie spellCast=new Cookie("spellCast",theSpell[0]);
					spellCast.setPath("/");
					spellCast.setMaxAge(60*60*24*2);
					response.addCookie(spellCast);
					enemy = Enemy.fromCookie(enemyCookie);
					int tempHeroArmor=hero.armor;
					int tempHeroMagicResist=hero.magicResist;
					int tempHeroCritChance=hero.critChance;
					int tempHeroAttackMin=hero.attackMin;
					int tempHeroAttackMax=hero.attackMax;
					hero.armor=hero.armor*2;
					hero.magicResist=hero.magicResist*2;
					hero.critChance=hero.critChance*2;
					hero.attackMin=(int)(hero.attackMin*1.20);
					hero.attackMax=(int)(hero.attackMax*1.20);
					model.addAttribute("spellDamage", "You cast Plate Armor.Your Damage is now "+String.valueOf((hero.attackMin+(hero.armor/2)))+"-"+String.valueOf((hero.attackMax+(hero.armor/2)))+".Your Armor is "+String.valueOf(hero.armor)+", Magic Resist "+String.valueOf(hero.magicResist)+", and Critical Chance "+String.valueOf(hero.critChance)+"%");
					Cookie leHeroCookie = hero.createCookie();
					leHeroCookie.setPath("/");
					leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(leHeroCookie);
					String fightOutcome = fight(enemy, response, model, hero);
					hero.armor=tempHeroArmor;
					hero.magicResist=tempHeroMagicResist;
					hero.critChance=tempHeroCritChance;
					hero.attackMin=tempHeroAttackMin;
					hero.attackMax=tempHeroAttackMax;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					if (fightOutcome.equals("nobodyDied")) {
						return "fight";
					}
					return fightOutcome;
				
			}else if(spellCastCookie.equals("Reckoning")) {
				if (hero.mana - 50 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";

				}
				hero.mana -= 50;
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				String[]theSpell=hero.generateHeroSpellText(hero, spellCastCookie, response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				enemy = Enemy.fromCookie(enemyCookie);
				int tempHeroCritChance=hero.critChance;
				int tempHeroAttackMin=hero.attackMin;
				int tempHeroAttackMax=hero.attackMax;
				int pointsIncreased=(int)((hero.hp*0.10)+hero.armor+hero.magicResist);
				hero.critChance+=pointsIncreased;
				hero.attackMin+=pointsIncreased;
				hero.attackMax+=pointsIncreased;
				model.addAttribute("spellDamage", "You cast Reckoning.Your Attack Min/Max and Critical Strike Chance are Increased by "+pointsIncreased);
				Cookie leHeroCookie = hero.createCookie();
				leHeroCookie.setPath("/");
				leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(leHeroCookie);
				String fightOutcome = fight(enemy, response, model, hero);
				hero.critChance=tempHeroCritChance;
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				Cookie heroNewCookie = hero.createCookie();
				heroNewCookie.setPath("/");
				heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroNewCookie);
				if (fightOutcome.equals("nobodyDied")) {
					return "fight";
				}
				return fightOutcome;
			
		}
		}

		if (hero.heroClass.equals("Ranger")) {
			if (spellCastCookie.equals("Ranger Sight")) {
				if (hero.mana - 40 < 0) {
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					model.addAttribute("message", hero.createDisplayText());
					return "noMana";

				}
				String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				hero.mana -= 40;
				enemy = Enemy.fromCookie(enemyCookie);
				hero.rangerSightBonusDamageMin = (int) (hero.attackMin * 0.80);
				hero.rangerSightBonusDamageMax = (int) (hero.attackMax * 0.80);
				model.addAttribute("spellDamage",
						"You cast Ranger Sight increasing your Damage to "
								+ String.valueOf(hero.attackMin + hero.rangerSightBonusDamageMin) + "-"
								+ String.valueOf(hero.attackMax + hero.rangerSightBonusDamageMax));
				Cookie leHeroCookie = hero.createCookie();
				leHeroCookie.setPath("/");
				leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(leHeroCookie);
				String fightOutcome = fight(enemy, response, model, hero);
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				response.addCookie(newSkillsCookie);
				if (fightOutcome.equals("nobodyDied")) {
					return "fight";
				}
				return fightOutcome;
			}
			if(spellCastCookie.equals("Poison Arrow")) {
				if (hero.mana - 30 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				hero.mana-=30;
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				int tempEnemyHealth = enemy.health;
				int tempHealth = hero.hp;
				int poisonDamage=(int)(hero.attackMax*0.30);
				enemy.health-=poisonDamage;
				tempEnemyHealth=enemy.health;
				int petDamageMin=(int)(hero.attackMin*0.15);
				int petDamageMax=(int)(hero.attackMax*0.15);
				model.addAttribute("yourPetAttacks","Your pet attacks");
				model.addAttribute("dealing"," dealing ");
				boolean petCrit=Utils.critical(hero.critChance);
				if (petCrit) {
					model.addAttribute("petCritically"," CRITICALLY");
					enemy.health-=Utils.attack(petDamageMin,petDamageMax)*1.8;
					tempEnemyHealth-=enemy.health;
					model.addAttribute("petDamage",String.valueOf(tempEnemyHealth)+" Damage");
				}else {
					
					enemy.health-=Utils.attack(petDamageMin,petDamageMax);
					tempEnemyHealth-=enemy.health;
					model.addAttribute("petDamage",String.valueOf(tempEnemyHealth)+" Damage");
				}
				int armorReduced=(int)(hero.attackMin*0.20);
				if(enemy.armor-armorReduced<=0) {
					enemy.armor-=armorReduced;
				}else {
					enemy.armor=0;
				}
//				enemy = hero.berserkAxeThrow(hero, enemy, model, response, spellCastCookie);
				model.addAttribute("spellDamage","You poison the enemy dealing "+String.valueOf(poisonDamage)+" damage every turn and reducing his armor with "+armorReduced);
				Cookie poison=new Cookie("poison",String.valueOf(poisonDamage));
				poison.setMaxAge(60*60*24*2);
				poison.setPath("/");
				response.addCookie(poison);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				} 
			} else if (spellCastCookie.equals("Frost Arrow")) {
				if (hero.mana - 40 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
//				int tempEnemyHealth = enemy.health;
//				int tempHealth = hero.hp;
				enemy = hero.rangerFrostArrow(hero, enemy, model, response, spellCastCookie);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "stunnedEnemy";
				}
			} else if(spellCastCookie.equals("Perfect Duo")) {
				if (hero.mana - 50 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				hero.mana-=50;
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				int tempEnemyHealth = enemy.health;
				int tempHealth = hero.hp;
				int petDamageMin=(int)(hero.attackMin);
				int petDamageMax=(int)(hero.attackMax);
				boolean crit=false;
				if(Utils.critical(hero.critChance)){
					crit=true;
				}else if(Utils.critical(hero.critChance)) {
					crit=true;
				}
				String critically="";
				if(crit) {
					critically=" CRITICALLY ";
					int damageDealt=Utils.attack(hero.attackMin, hero.attackMax);
					damageDealt=(int)(damageDealt*1.8);
					enemy.health-=damageDealt;
					model.addAttribute("damageDealt", String.valueOf(damageDealt));
				}else {
					int damageDealt=Utils.attack(hero.attackMin, hero.attackMax);
					enemy.health-=damageDealt;
					model.addAttribute("damageDealt", String.valueOf(damageDealt));
				}
				tempEnemyHealth=enemy.health;
				model.addAttribute("yourPetAttacks","Your pet attacks");
				model.addAttribute("dealing"," dealing ");	
				model.addAttribute("critically",critically);
				model.addAttribute("spellDamage","You cast Perfect Duo Making your pet's Attack Min and Max the same as yours and if one of you performs a critical, the other one does too");
				if (crit) {
					model.addAttribute("petCritically"," CRITICALLY");
					enemy.health-=Utils.attack(petDamageMin,petDamageMax)*1.8;
					tempEnemyHealth-=enemy.health;
					model.addAttribute("petDamage",String.valueOf(tempEnemyHealth)+" Damage");
				}else {
					
					enemy.health-=Utils.attack(petDamageMin,petDamageMax);
					tempEnemyHealth-=enemy.health;
					model.addAttribute("petDamage",String.valueOf(tempEnemyHealth)+" Damage");
				}
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					model.addAttribute("gold",String.valueOf(enemy.dropsGold));
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictory";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fight";
				} 
			}
		}

		if (hero.heroClass.equals("Berserk")) {
			if (spellCastCookie.equals("Bloodlust")) {
				if (hero.mana - 40 < 0) {
					model.addAttribute("message", hero.createDisplayText());
					return "noMana";

				}
				hero.mana -= 40;
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				String[]theSpell=hero.generateHeroSpellText(hero, spellCastCookie, response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				enemy = Enemy.fromCookie(enemyCookie);
				double bloodlustPercentIncrease = 1.5 * hero.critChance;
				int berserkPassiveDamage = (hero.maxHp - hero.hp) / 25;
				hero.bloodlustBonusDamageMin = (int) ((hero.attackMin + berserkPassiveDamage)
						* (bloodlustPercentIncrease) * 0.01);
				hero.bloodlustBonusDamageMax = (int) ((hero.attackMax + berserkPassiveDamage)
						* (bloodlustPercentIncrease) * 0.01);
				model.addAttribute("spellDamage", "You cast Bloodlust  increasing your Damage by "
						+ String.valueOf(bloodlustPercentIncrease) + "%.Your damage is now "
						+ String.valueOf(hero.attackMin + berserkPassiveDamage + hero.bloodlustBonusDamageMin) + "-"
						+ String.valueOf(hero.attackMax + berserkPassiveDamage + hero.bloodlustBonusDamageMax)
						+ " and your next attack is a critical");
				Cookie leHeroCookie = hero.createCookie();
				leHeroCookie.setPath("/");
				leHeroCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(leHeroCookie);
				hero.berserkCritical = 100;
				String fightOutcome = fight(enemy, response, model, hero);
				if (fightOutcome.equals("nobodyDied")) {
					return "fight";
				}
				return fightOutcome;
			}else if (spellCastCookie.equals("Axe Throw")) {
				if (hero.mana - 30 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				int tempEnemyHealth = enemy.health;
				int tempHealth = hero.hp;
				enemy = hero.berserkAxeThrow(hero, enemy, model, response, spellCastCookie);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				} 

			
			} else if (spellCastCookie.equals("Vigor Strike")) {
				if (hero.mana - 40 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				int tempEnemyHealth = enemy.health;
				//casting Vigor Strike
				enemy = hero.berserkVigorStrike(hero, enemy, model, response, spellCastCookie);
				//end of cast
				int tempHealth = hero.hp;
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
//					hero.gold += enemy.dropsGold;
					
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}
		} else if (spellCastCookie.equals("Enrage")) {
			if (hero.mana - 50 < 0) {
				model.addAttribute("message2", hero.createDisplayText());
				String[] skillsArr = skillsCookie.split(",");
				model.addAttribute("skill1", skillsArr[0]);
				model.addAttribute("skill2", skillsArr[1]);
				return "noMana";
			}
			ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
			Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
			newSkillsCookie.setPath("/");
			newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(newSkillsCookie);
			model.addAttribute("skill1", newSkills.get(0)[0]);
			model.addAttribute("skill2", newSkills.get(1)[0]);
			model.addAttribute("tooltip1", newSkills.get(0)[1]);
			model.addAttribute("tooltip2", newSkills.get(1)[1]);
			int tempEnemyHealth = enemy.health;
			int tempHealth = hero.hp;
			int tempHeroAttackMin=hero.attackMin;
			int tempHeroAttackMax=hero.attackMax;
			int tempHeroArmor=hero.armor;
			int tempHeroMagicResist=hero.magicResist;
			int tempCritChance=hero.critChance;
			hero.attackMin=(int)(hero.attackMin*0.80);
			hero.attackMax=(int)(hero.attackMax*0.80);
			hero.critChance+=tempHeroArmor+tempHeroMagicResist;
			enemy = hero.berserkEnrage(hero, enemy, model, response, spellCastCookie);
			hero.armor=0;
			hero.magicResist=0;
			hero.critChance=tempCritChance;
			Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(theEnemy);
			if (enemy.health <= 0) {
				if(hero.hp+hero.hpRegen<=hero.maxHp) {
					hero.hp+=hero.hpRegen;
				}else {
					hero.hp=hero.maxHp;
				}
				if(hero.mana+hero.manaRegen<=hero.maxMana) {
					hero.mana+=hero.manaRegen;
				}else {
					hero.mana=hero.maxMana;
				}
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				hero.armor=tempHeroArmor;
				hero.magicResist=tempHeroMagicResist;
				Cookie heroNewCookie = hero.createCookie();
				heroNewCookie.setPath("/");
				heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroNewCookie);
				Cookie bossState = new Cookie("bossState", "dead");
				bossState.setPath("/");
				bossState.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(bossState);
				model.addAttribute("message2", hero.createDisplayText());
				model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
				model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
				return "fightvictoryWithSpell";
			} else {
				hero = enemy.enemyAttack(hero, enemy, model, response);
				if (hero.hp <= 0) {
					return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
				}
				hero.attackMin=tempHeroAttackMin;
				hero.attackMax=tempHeroAttackMax;
				hero.armor=tempHeroArmor;
				hero.magicResist=tempHeroMagicResist;
				Cookie heroNewCookie = hero.createCookie();
				heroNewCookie.setPath("/");
				heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroNewCookie);
				model.addAttribute("message2", hero.createDisplayText());
				return "fightWithSpell";
			}
	}
		}

		if (hero.heroClass.equals("Giant")) {
			if(spellCastCookie.equals("Earth Shock")) {
			int tempEnemyHealth=enemy.health;
			hero.giantEarthShock(hero, enemy, model, response,spellCastCookie);
			ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
			Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
			int tempHealth=hero.hp;
			newSkillsCookie.setPath("/");
			newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(newSkillsCookie);
			model.addAttribute("skill1", newSkills.get(0)[0]);
			model.addAttribute("skill2", newSkills.get(1)[0]);
			model.addAttribute("tooltip1", newSkills.get(0)[1]);
			model.addAttribute("tooltip2", newSkills.get(1)[1]);
			Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
			theEnemy.setPath("/");
			theEnemy.setMaxAge(60 * 60 * 24 * 2);
			response.addCookie(theEnemy);
			if (enemy.health <= 0) {
				if(hero.hp+hero.hpRegen<=hero.maxHp) {
					hero.hp+=hero.hpRegen;
				}else {
					hero.hp=hero.maxHp;
				}
				if(hero.mana+hero.manaRegen<=hero.maxMana) {
					hero.mana+=hero.manaRegen;
				}else {
					hero.mana=hero.maxMana;
				}
				hero.gold += enemy.dropsGold;
				Cookie heroNewCookie = hero.createCookie();
				heroNewCookie.setPath("/");
				heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroNewCookie);
				Cookie bossState = new Cookie("bossState", "dead");
				bossState.setPath("/");
				bossState.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(bossState);
				model.addAttribute("message2", hero.createDisplayText());
				model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
				model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
				model.addAttribute("gold",enemy.dropsGold);
				return "fightvictoryWithSpell";
			} else {
				hero = enemy.enemyAttack(hero, enemy, model, response);
				if (hero.hp <= 0) {
					return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
				}
				Cookie heroNewCookie = hero.createCookie();
				heroNewCookie.setPath("/");
				heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(heroNewCookie);
				model.addAttribute("message2", hero.createDisplayText());
				return "fightWithSpell";
			}
			} else if(spellCastCookie.equals("Regeneration")) {
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				int tempEnemyHealth = enemy.health;
				int tempHealth = hero.hp;
				int regeneration=(int)(hero.rage*0.15);
				int damageDealt=(int)(hero.rage*0.30);
				enemy.health-=damageDealt;
				tempEnemyHealth=enemy.health;
//				enemy = hero.berserkAxeThrow(hero, enemy, model, response, spellCastCookie);
				model.addAttribute("spellDamage","You attack the enemy dealing "+String.valueOf(damageDealt)+" damage and begin regenerating "+String.valueOf(regeneration)+" health every turn until the end of the fight");
				Cookie regenerationCookie=new Cookie("regeneration",String.valueOf(regeneration));
				regenerationCookie.setMaxAge(60*60*24*2);
				regenerationCookie.setPath("/");
				response.addCookie(regenerationCookie);
				hero.rage=0;
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}
			} else if(spellCastCookie.equals("Smash")) {
					int tempEnemyHealth=enemy.health;
					hero.giantSmash(hero, enemy, model, response,spellCastCookie);
					ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
					Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
					int tempHealth=hero.hp;
					newSkillsCookie.setPath("/");
					newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(newSkillsCookie);
					model.addAttribute("skill1", newSkills.get(0)[0]);
					model.addAttribute("skill2", newSkills.get(1)[0]);
					model.addAttribute("tooltip1", newSkills.get(0)[1]);
					model.addAttribute("tooltip2", newSkills.get(1)[1]);
					Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
					theEnemy.setPath("/");
					theEnemy.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(theEnemy);
					if (enemy.health <= 0) {
						if(hero.hp+hero.hpRegen<=hero.maxHp) {
							hero.hp+=hero.hpRegen;
						}else {
							hero.hp=hero.maxHp;
						}
						if(hero.mana+hero.manaRegen<=hero.maxMana) {
							hero.mana+=hero.manaRegen;
						}else {
							hero.mana=hero.maxMana;
						}
						Cookie heroNewCookie = hero.createCookie();
						heroNewCookie.setPath("/");
						heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
						response.addCookie(heroNewCookie);
						Cookie bossState = new Cookie("bossState", "dead");
						bossState.setPath("/");
						bossState.setMaxAge(60 * 60 * 24 * 2);
						response.addCookie(bossState);
						model.addAttribute("message2", hero.createDisplayText());
						model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
						model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
						return "fightvictory";
					} else {
						hero = enemy.enemyAttack(hero, enemy, model, response);
						if (hero.hp <= 0) {
							return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
						}
						Cookie heroNewCookie = hero.createCookie();
						heroNewCookie.setPath("/");
						heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
						response.addCookie(heroNewCookie);
						model.addAttribute("message2", hero.createDisplayText());
						return "fight";
					}
			} else if(spellCastCookie.equals("Rage Control")) {
				if(hero.hp-(int)(hero.maxHp*0.20)<=0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noHealth";
				}
				int tempEnemyHealth=enemy.health;
				hero.giantRageControl(hero, enemy, model, response,spellCastCookie);
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				int tempHealth=hero.hp;
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}
			}
		
		}

		if (hero.heroClass.equals("Necromancer")) {
			if (spellCastCookie.equals("Siphon Life")) {
				if (hero.mana - 50 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";

				}
				hero.necromancerSiphonLife(hero, enemy, model, response,spellCastCookie);
				int tempEnemyHealth=enemy.health;
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				int tempHealth=hero.hp;
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					model.addAttribute("gold",enemy.dropsGold);
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}

			} else if(spellCastCookie.equals("Flesh Golem")) {
				if (hero.mana - 40 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";
				} if(hero.hp-(int)(0.10*hero.maxHp)<=0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noHealth";
				}
				hero.mana-=40;
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				String[] theSpell=hero.generateHeroSpellText(hero,spellCastCookie,response);
				model.addAttribute("spell",theSpell[0]);
				model.addAttribute("tooltip",theSpell[1]);
				Cookie spellCast=new Cookie("spellCast",theSpell[0]);
				spellCast.setPath("/");
				spellCast.setMaxAge(60*60*24*2);
				response.addCookie(spellCast);
				int tempEnemyHealth = enemy.health;
				int tempHealth = hero.hp;
				int healthLost=(int)(hero.maxHp*0.10)+hero.souls;
				int golemDamage=healthLost;
				Cookie golem=new Cookie("golem",String.valueOf(healthLost));
				golem.setMaxAge(60*60*24*2);
				golem.setPath("/");
				response.addCookie(golem);
				hero.hp-=healthLost;
				String golemCritically="";
				if(Utils.critical(hero.critChance)) {
					golemDamage=(int)(golemDamage*1.8);
					golemCritically=" CRITICALLY";
				}
				golemDamage-=enemy.armor;
				enemy.health-=golemDamage;
				tempEnemyHealth=enemy.health;
				model.addAttribute("yourPetAttacks","Flesh Golem attacks");
				model.addAttribute("dealing"," dealing ");
					model.addAttribute("petCritically",golemCritically);
					tempEnemyHealth-=enemy.health;
					model.addAttribute("petDamage",golemDamage+" Damage");
				model.addAttribute("spellDamage","You create a Flesh Golem and lose "+healthLost+" health");
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold += enemy.dropsGold;
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				} else {
					hero = enemy.enemyAttack(hero, enemy, model, response);
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}
			} else if (spellCastCookie.equals("Vitality Drain")) {
				if (hero.mana - 30 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";

				}
				hero.necromancerVitalityDrain(hero, enemy, model, response,spellCastCookie);
				int tempEnemyHealth=enemy.health;
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				int tempHealth=hero.hp;
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				Cookie theEnemy = new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					Cookie bossState = new Cookie("bossState", "dead");
					bossState.setPath("/");
					bossState.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(bossState);
					model.addAttribute("message2", hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					model.addAttribute("gold",enemy.dropsGold);
					return "fightvictory";
				} else {
					if (hero.hp <= 0) {
						return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					}
					Cookie heroNewCookie = hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60 * 60 * 24 * 2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fight";
				}
			} if (spellCastCookie.equals("Unholy Strike")) {
//				model.addAttribute("spell", "Fireball");
				if (hero.mana - 40 < 0) {
					model.addAttribute("message2", hero.createDisplayText());
					String[] skillsArr = skillsCookie.split(",");
					model.addAttribute("skill1", skillsArr[0]);
					model.addAttribute("skill2", skillsArr[1]);
					return "noMana";

				}
				ArrayList<String[]> newSkills = hero.generateHeroSkillText(hero, response);
				Cookie newSkillsCookie = new Cookie("skills", newSkills.get(0)[0] + "," + newSkills.get(1)[0]);
				newSkillsCookie.setPath("/");
				newSkillsCookie.setMaxAge(60 * 60 * 24 * 2);
				response.addCookie(newSkillsCookie);
				model.addAttribute("skill1", newSkills.get(0)[0]);
				model.addAttribute("skill2", newSkills.get(1)[0]);
				model.addAttribute("tooltip1", newSkills.get(0)[1]);
				model.addAttribute("tooltip2", newSkills.get(1)[1]);
				int tempEnemyHealth=enemy.health;
				int tempHealth=hero.hp;
				enemy = hero.necromancerUnholyStrike(hero, enemy, model, response, spellCastCookie);
				Cookie theEnemy=new Cookie("enemy", enemy.toCookie());
				theEnemy.setPath("/");
				theEnemy.setMaxAge(60*60*24*2);
				response.addCookie(theEnemy);
				if (enemy.health <= 0) {
					if(hero.hp+hero.hpRegen<=hero.maxHp) {
						hero.hp+=hero.hpRegen;
					}else {
						hero.hp=hero.maxHp;
					}
					if(hero.mana+hero.manaRegen<=hero.maxMana) {
						hero.mana+=hero.manaRegen;
					}else {
						hero.mana=hero.maxMana;
					}
					hero.gold+=enemy.dropsGold;
					Cookie heroNewCookie=hero.createCookie();
					heroNewCookie.setPath("/");
					heroNewCookie.setMaxAge(60*60*24*2);
					response.addCookie(heroNewCookie);
					Cookie bossState=new Cookie("bossState","dead");
					bossState.setPath("/");
					bossState.setMaxAge(60*60*24*2);
					response.addCookie(bossState);
					model.addAttribute("message2",hero.createDisplayText());
					model.addAttribute("hpRegen",String.valueOf(hero.hpRegen));
					model.addAttribute("manaRegen",String.valueOf(hero.manaRegen));
					return "fightvictoryWithSpell";
				}
				 else {
					 hero=enemy.enemyAttack(hero, enemy, model, response);
					 if(hero.hp<=0) {
						 return hero.heroDefeat(hero, enemy, model, response, tempEnemyHealth, tempHealth);
					 }
					 Cookie heroNewCookie=hero.createCookie();
					 heroNewCookie.setPath("/");
					 heroNewCookie.setMaxAge(60*60*24*2);
					response.addCookie(heroNewCookie);
					model.addAttribute("message2", hero.createDisplayText());
					return "fightWithSpell";
				}
			}
			return "fight";
		}
		else {
			return "fight";
		}
	}
}
