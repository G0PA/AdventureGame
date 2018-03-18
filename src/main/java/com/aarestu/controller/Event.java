package com.aarestu.controller;

public class Event {
	String resource;
	String eventName;
	String firstButton;
	String secondButton;
	int firstButtonSuccessRate;
	int secondButtonSuccessRate;
	Hero goodOutcomeHero1;
	String goodOutcomeText1;
	String badOutcomeText1;
	String goodOutcomeText2;
	String badOutcomeText2;
	Hero badOutcomeHero1;
	Hero goodOutcomeHero2;
	Hero badOutcomeHero2;
	public Event(String resource, String eventName,String firstButton,int firstButtonSuccessRate,Hero goodOutcomeHero1,String goodOutcomeText1,Hero badOutcomeHero1,String badOutcomeText1, String secondButton, int secondButtonSuccessRate,Hero goodOutcomeHero2,String goodOutcomeText2,Hero badOutcomeHero2,String badOutcomeText2)
	{
		this.resource=resource;
		this.eventName=eventName;
		this.firstButton=firstButton;
		this.secondButton=secondButton;
		this.firstButtonSuccessRate=firstButtonSuccessRate;
		this.secondButtonSuccessRate=secondButtonSuccessRate;
		this.goodOutcomeHero1=goodOutcomeHero1;
		this.badOutcomeHero1=badOutcomeHero1;
		this.goodOutcomeHero2=goodOutcomeHero2;
		this.badOutcomeHero2=badOutcomeHero2;
		this.goodOutcomeText1=goodOutcomeText1;
		this.badOutcomeText1=badOutcomeText1;
		this.goodOutcomeText2=goodOutcomeText2;
		this.badOutcomeText2=badOutcomeText2;
		
	}

	boolean outcome(int successRate) {

		return Utils.randomBool(successRate);
		
	}
	public Hero updateHero(Hero currentHero, Hero changedHero)
	{
		currentHero.hp+=changedHero.hp;
		currentHero.maxHp+=changedHero.maxHp;
		currentHero.attackMin+=changedHero.attackMin;
		currentHero.attackMax+=changedHero.attackMax;
		currentHero.armor+=changedHero.armor;
		currentHero.magicResist+=changedHero.magicResist;
		currentHero.critChance+=changedHero.critChance;
		currentHero.gold+=changedHero.gold;
		if(currentHero.gold<0)
		{
			currentHero.gold=0;
		}
		return currentHero;
		
		
	}
}
