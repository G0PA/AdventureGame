
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
.redText{
color:red;
}
</style>
<head>
<title>Welcome</title>
</head>
<body background= "resources/images/warriorWallpaper.jpg"class=bg>
<div class="asd">
	<h1 style="color:red"><font size="10"><i>Warrior</i></font></h1>
	<h1 style="color:black"><i>Passive: every 2 points of Armor increases Attack Damage Min and Max with 1</i></h1>
	<h1 style="color:white"><i><span class="yellowText">Spells:</span></i></h1>
	<div onclick="location.href='warrior';" style="cursor:pointer;" class="tooltip"><i>Endurance</i> 
  <span class="tooltiptext"><i><span class="yellowText">Deal damage equal to 15% of your Missing Health and Heal yourself for that amount.Cannot Critically hit but ignores armor.)-40 Mana</span></i></span>
</div>
<div onclick="location.href='warrior';" style="cursor:pointer;" class="tooltip"><i>Shield Bash</i> 
  <span class="tooltiptext"><i><span class="yellowText">Stun the enemy for this round and do 3 Damage to him for every point of Armor you have(Ignores armor and can be a Critical).-40 Mana</span></i></span>
</div>
<div onclick="location.href='warrior';" style="cursor:pointer;" class="tooltip"><i>Plate Armor</i> 
  <span class="tooltiptext"><i><span class="yellowText">Double your Armor, Magic Resist and Critical Chance for this round and Increase your Attack Min and Max with 20%.-30 Mana</span></i></span>
</div>
<div onclick="location.href='warrior';" style="cursor:pointer;" class="tooltip"><i>Reckoning</i> 
  <span class="tooltiptext"><i><span class="yellowText">Every 10 points of Current Health and every point of Armor and Magic Resist Increase your Attack Min/Max and Critical Chance by 1.-50 Mana</span></i></span>
</div>
<h1></h1>
	<a href="greenWoods" class="button">CHOOSE WARRIOR</a>
	<a href="play" class="button">RETURN</a>
	</div>
	
</body>
</html>