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
.manasteal{
color:blue;
}
</style>
<head>
</head>
<body background= "resources/images/${defeatScreen}.jpg" class=bg>
	<h1 style="background-color:Chartreuse;">${message2}</h1>
	<h2 style="color:yellow"><i>${spellDamage}</i></h2>
	<h4 style="color:yellow"><i>${yourPetAttacks}${petCritically}${dealing}${petDamage} </i></h4>
	<h3><i><span class="blueText">You</span> <span class="blueText">${critically}</span><span class="grayText">damage the enemy dealing</span><span class="blueText"> ${damageDealt}</span> <span class="grayText">damage<span class="blueText">${lifesteal}</span><span class="manasteal">${manasteal}</span> </span></i></h3>
	<h3 style="color:Maroon;"> <i>Enemy health left: <span class="redText">${enemy}</span></i></h3>
	<h3 style="color:red;"><i>${enemyName} ${enemyCritically}<span class="maroonText">damages you dealing </span>${enemyDamage} <span class="maroonText">damage</span></i></h3>
	<h3 style="color:red;"><i>${enemyDamage2} </i></h3>
	<h1 style="color:red;">You Were Defeated</h1>
	<a href="play" class="button">PLAY AGAIN</a>
</body>
</html>