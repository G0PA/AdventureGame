
<html>
<style>
.tooltip {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}

.tooltip .tooltiptext {
    visibility: hidden;
    width: 600px;
    background-color: black;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
}

.tooltip {font-size: 24px;}
.tooltip {
padding:15px;
border-radius: 2px;
background-color: yellow;
    color: black;
    border: 2px solid black;

}
.tooltip{
line-height: 3em;
}
.yellowText{
color:yellow;
}
.violetText{
color:green
}

.tooltip2 {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}

.tooltip2 .tooltiptext2 {
    visibility: hidden;
    width: 600px;
    background-color: black;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
}

.tooltip2:hover .tooltiptext2 {
    visibility: visible;
}

.tooltip2 {font-size: 24px;}
.tooltip2 {
padding:15px;
border-radius: 2px;
background-color: green;
    color: black;
    border: 2px solid black;

}
.tooltip2{
line-height: 3em;
}

.tooltip3 {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}

.tooltip3 .tooltiptext3 {
    visibility: hidden;
    width: 600px;
    background-color: black;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
}

.tooltip3:hover .tooltiptext3 {
    visibility: visible;
}

.tooltip3 {font-size: 24px;}
.tooltip3 {
padding:15px;
border-radius: 2px;
background-color: brown;
    color: black;
    border: 2px solid black;

}
.tooltip3{
line-height: 3em;
}
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
.asd{
text-align:center;
}
.yellowText{
color:blue;
}
</style>
<head>
<title>Welcome</title>
</head>
<body background= "resources/images/necromancerWallpaper.jpg"class=bg>
<div class="asd">
	<h1 style="color:blue"><font size="10"><i>Necromancer</i></font></h1>
	<h1 style="color:white"><i>Passive: Every enemy kill has a 66% chance to grant you 1 soul.For every 2 souls increase damage Min and Max with 1</i></h1>
		<div onclick="location.href='necromancer';" style="cursor:pointer;" class="tooltip"><i>Vitality Drain</i> 
  <span class="tooltiptext"><i><span class="yellowText">For this Round only - reduce enemy Attack Min and Max with 35% and halve his Critical Chance. Increase your Own Attack Min and Max with 35% and Double your Critical Chance.-30 Mana</span></i></span>
</div>
<div onclick="location.href='necromancer';" style="cursor:pointer;" class="tooltip"><i>Flesh Golem</i> 
  <span class="tooltiptext"><i><span class="yellowText">Summon a flesh golem that fights with you until the enemy is defeated.You lose 10% of your Maximum Health aswell as 1 health per soul.The Golem deals damage equal to the health you lost every turn(Has same Crit chance as your Hero,but does not ignore armor).-40 Mana</span></i></span>
</div>
<div onclick="location.href='necromancer';" style="cursor:pointer;" class="tooltip"><i>Unholy Strike</i> 
  <span class="tooltiptext"><i><span class="yellowText">Lose 20% current Health but deal damage to the enemy equal to 30% of your Maximum Mana +1 per soul that ignores armor and can be a Critical.Increase Critical Chance by 1% for every 2 souls you have.-40 Mana</span></i></span>
</div>
<div onclick="location.href='necromancer';" style="cursor:pointer;" class="tooltip"><i>Siphon Life</i> 
  <span class="tooltiptext"><i><span class="yellowText">Deal damage equal to 25% of your Maximum Mana +1 per soul(has same Critical chance as your normal attacks and ignores armor) and heal yourself for 10% of your Maximum Mana +1 per soul. -50 Mana</span></i></span>
</div>
<h1></h1>
	<a href="greenWoods" class="button">CHOOSE NECROMANCER</a>
	<a href="play" class="button">RETURN</a>
	</div>
	
</body>
</html>