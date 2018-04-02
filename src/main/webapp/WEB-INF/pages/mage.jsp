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
.redText{
color: red;
}
.crimsonText{
color: Crimson;
}
.blueText{
color: Chartreuse;
}
.grayText{
color:DarkGreen;
}
.maroonText{
color:Maroon;
}
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
.brownText{
color:brown
}
.manasteal{
color:blue
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
color:yellow;
}
</style>
<head>
<title>Welcome</title>
</head>
<body background= "http://localhost:8081/AdventureGame/resources/images/mageWallpaper.jpg"class=bg>
<div class="asd">
	<h1 style="color:yellow"><font size="10"><i>Mage</i></font></h1>
	<h1 style="color:white"><i>Passive: every 2 points of Magic Resist increases Attack Damage Min and Max with 1</i></h1>
	<h1 style="color:white"><i><span class="yellowText">Spells:</span></i></h1>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<div onclick="location.href='http://localhost:8081/AdventureGame/mage';" style="cursor:pointer;" class="tooltip"><i>Fireball</i> 
  <span class="tooltiptext"><i><span class="yellowText">Deals damage equal to 25% of your Maximum Mana(Has same Critical Chance as your normal attacks and ignores armor). -30 Mana</span></i></span>
</div>
<div onclick="location.href='http://localhost:8081/AdventureGame/mage';" style="cursor:pointer;" class="tooltip"><i>Portal</i> 
  <span class="tooltiptext"><i><span class="yellowText">Teleports you away from the fight. -30 Mana</span></i></span>
</div>
<div onclick="location.href='http://localhost:8081/AdventureGame/mage';" style="cursor:pointer;" class="tooltip"><i>Freezing Touch</i> 
  <span class="tooltiptext"><i><span class="yellowText">Deals damage equal to 15% of your Maximum Mana(Cannot be a Critical, but ignores armor) and Freezing the enemy making him unable to attack this round. -40 Mana</span></i></span>
</div>
<div onclick="location.href='http://localhost:8081/AdventureGame/mage';" style="cursor:pointer;" class="tooltip"><i>Magic Affinity</i> 
  <span class="tooltiptext"><i><span class="yellowText">Deals damage equal to 30% of your Maximum Mana which is further increased by 1.5% for every point of Magic Resist,ignores armor and has your Critical Strike chance percentage +1% for every point of Magic Resist. -50 Mana</span></i></span>
</div>
	<h1></h1>
	<a href="http://localhost:8081/AdventureGame/greenWoods" class="button">CHOOSE MAGE</a>
	<a href="http://localhost:8081/AdventureGame/play" class="button">RETURN</a>
	</div>
	
</body>
</html>