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
.violetText{
color:Sandybrown;
}
</style>
<head>
<title>Welcome</title>
</head>
<body background= "resources/images/giantClass.jpg"class=bg>
<div class="asd">
	<h1 style="color:SandyBrown"><font size="10"><i>Giant</i></font></h1>
	<h1 style="color:white"><i>Passive: Uses Rage instead of Mana as Resource and converts any mana gained to health.Every point of health lost from enemy attacks generates 1 Rage.If not used by the end of the fight, every 10 points of Rage Increase Current and Max Health by 1</i></h1>
	<div onclick="location.href='giant';" style="cursor:pointer;" class="tooltip"><i>Earth Shock</i> 
  <span class="tooltiptext"><i><span class="yellowText">Deals damage equal to 20% of your Maximum Health while losing 30% Current Health.Chance for Critical is your Critical Chance + the amount of health you lost from the ability (ignores enemy armor, but does not generate Rage)</span></i></span>
</div>
<div onclick="location.href='giant';" style="cursor:pointer;" class="tooltip"><i>Regeneration</i> 
  <span class="tooltiptext"><i><span class="yellowText">Lose all of your current rage.15% of the rage lost comes as regeneration at the start of every round until the fight ends.Deal damage equal to 30% of the Rage Lost this round(Cannot be a critical but ignores armor)</span></i></span>
</div>
<div onclick="location.href='giant';" style="cursor:pointer;" class="tooltip"><i>Smash</i> 
  <span class="tooltiptext"><i><span class="yellowText">Perform a more powerful attack, increasing your Attack Min and Max with 1.5% for every point of Rage(does not ignore armor,but can be a Critical).Critical Strike chance is increased by 1 for every 10 points of Rage for this round.The ability consumes all Rage</span></i></span>
</div>
<div onclick="location.href='giant';" style="cursor:pointer;" class="tooltip"><i>Rage Control</i> 
  <span class="tooltiptext"><i><span class="yellowText">Damage yourself and the enemy for 10% of your Maximum Health(Cannot be a critical, but ignores armor).Generate 1.5 Rage for every point of Health lost from this ability</span></i></span>
</div>
<h1></h1>
	<a href="greenWoods" class="button">CHOOSE GIANT</a>
	<a href="play" class="button">RETURN</a>
	</div>
	
</body>
</html>