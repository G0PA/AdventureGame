
<html>
<style>
.button {font-size: 24px;}
.button {
padding: 30px 40px;
border-radius: 2px;
background-color: #1E90FF;
    color: black;
    border: 2px solid red;

}
.button{
line-height: 5em;
}
.button:hover {
background-color: red; /* Green */
    color: black;
}
.bg{
height: 100%; 

 -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;

}

.button2 {font-size: 24px;}
.button2 {
padding: 30px 40px;
border-radius: 2px;
background-color: red;
    color: black;
    border: 2px solid red;

}
.button2{
line-height: 5em;
}
.button2:hover {
background-color: #1E90FF; /* Green */
    color: black;
}
.redText{
color: crimson;
}
.greenText{
color:Chartreuse;
}

.button3 {font-size: 24px;}
.button3 {
position:relative;
padding: 30px 40px;
border-radius: 2px;
background-color: #1E90FF;
    color: black;
    border: 2px solid red;
    margin-top:50px;
    bottom: -125px

}
.button3{
line-height: 5em;
}
.button3:hover {
background-color: red; /* Green */
    color: black;
}
</style>
<head>
<title>Settlement</title>
</head>
<body background= "resources/images/${resource}.jpg"class=bg>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<h1>${cheating}</h1>
	<h1 style="color:Chartreuse;"> ${settlementName}</h1>
		<h2 style="color:Chartreuse;">You can shop</h2>
	<div id="zdr">
	<a href="http://localhost:8081/AdventureGame/item1" class="button">${name}<span class="greenText">${currentHealth}${maxHealth}${attackMin}${attackMax}${armor}${magicResist}${critChance}</span><span class="redText">${costsGold}</span></a>
	</div>
	<div>
	<a href="http://localhost:8081/AdventureGame/item2" class="button">${secondName}<span class="greenText">${secondCurrentHealth}${secondMaxHealth}${secondAttackMin}${secondAttackMax}${secondArmor}${secondMagicResist}${secondCritChance}</span><span class="redText">${secondCostsGold}</span></a>
	</div>
	<div>
	<a href="http://localhost:8081/AdventureGame/item3" class="button">${thirdName}<span class="greenText">${thirdCurrentHealth}${thirdMaxHealth}${thirdAttackMin}${thirdAttackMax}${thirdArmor}${thirdMagicResist}${thirdCritChance}</span><span class="redText">${thirdCostsGold}</span></a>
	</div>
	<div>
	<a href="http://localhost:8081/AdventureGame/item4" class="button">${fourthName}<span class="greenText">${fourthCurrentHealth}${fourthMaxHealth}${fourthAttackMin}${fourthAttackMax}${fourthArmor}${fourthMagicResist}${fourthCritChance}</span><span class="redText">${fourthCostsGold}</span></a>
	</div>
	<div>
	<a href="http://localhost:8081/AdventureGame/sleep" class="button">SLEEP: <span class="greenText">+15 Health</span> <span class="redText">-1 Enemy Encounter</span></a>
	</div>
	<a href="http://localhost:8081/AdventureGame/leaveSettlement" class="button2">LEAVE SETTLEMENT</a>
	
</body>
</html>