
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
.violetText{
color:violet;
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
.greenText{
color:Chartreuse;
}
</style>
<head>
<title>Ranger</title>
</head>
<body background= "resources/images/ranger.jpg"class=bg>
<div class="asd">
	<h1 style="color:Chartreuse"><font size="10"><i>RANGER</i></font></h1>
	<h1 style="color:white"><i>Passive: Your pet fights with you, having 15% of your Attack Min and Max and same Critical Chance</i></h1>
		<div onclick="location.href='http://localhost:8081/AdventureGame/ranger';" style="cursor:pointer;" class="tooltip"><i>Ranger Sight</i> 
  <span class="tooltiptext"><i><span class="yellowText">increases your Attack Damage Min and Max with 80%. -40 Mana</span></i></span>
</div>
<div onclick="location.href='http://localhost:8081/AdventureGame/ranger';" style="cursor:pointer;" class="tooltip"><i>Frozen Arrow</i> 
  <span class="tooltiptext"><i><span class="yellowText">Fire a freezing arrow dealing 65% of your Attack Damage Max and freezing the enemy making him unable to attack this round.Can be a Critical and ignores Armor. -40 Mana</span></i></span>
</div>
<div onclick="location.href='http://localhost:8081/AdventureGame/ranger';" style="cursor:pointer;" class="tooltip"><i>Poison Arrow</i> 
  <span class="tooltiptext"><i><span class="yellowText">Fire a poisonous arrow dealing 30% of your Attack Damage Max every turn and ignoring armor until the fight ends,also reduce the enemy armor by 20% of your Attack Damage Min(Cannot be reduced under 0). -30 Mana</span></i></span>
</div>
<div onclick="location.href='http://localhost:8081/AdventureGame/ranger';" style="cursor:pointer;" class="tooltip"><i>Perfect Duo</i> 
  <span class="tooltiptext"><i><span class="yellowText">Your pet's Attack Damage Min and Max becomes equal to yours for this round and if either you or your pet make a Critical the other one makes a Critical too. -50 Mana</span></i></span>
</div>
<h1></h1>
	<a href="greenWoods" class="button">CHOOSE RANGER</a>
	<a href="play" class="button">RETURN</a>
	</div>
	
</body>
</html>