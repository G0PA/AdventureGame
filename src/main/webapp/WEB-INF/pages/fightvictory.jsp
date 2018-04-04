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
.blueText{
color: Chartreuse;
}
.grayText{
color: green;
}
.blue{
color: blue;
}
.manasteal{
color:blue;
}
</style>
<body background= "resources/images/${resource}.jpg" class=bg>
<h1 style="background-color:Chartreuse;">${message2}</h1>
<h1 style="background-color:Chartreuse;">${cheating}</h1>
<h2 style="color:yellow"><i>${spellDamage}</i></h2>
<h3 style="color:yellow"><i>${poison}</i></h3>
	<h4 style="color:yellow"><i>${yourPetAttacks}${petCritically}${dealing}${petDamage} </i></h4>
	<h3><i><span class="blueText">You</span> <span class="blueText">${critically}</span><span class="grayText">damage the enemy dealing</span><span class="blueText"> ${damageDealt}</span> <span class="grayText">damage<span class="blueText">${lifesteal}${missedHits}</span><span class="manasteal">${manasteal}</span> </span></i></h3>
	<a href="${zone}" class="button">VICTORY</a>
	<h2 style="color:MediumBlue"><i>You regenerate <span class="blue">${hpRegen}</span> health and <span class="blue">${manaRegen}</span> mana</i></h2>
	<h2 style="color:Yellow"><i>${soul}</i></h2>
	<h1><span class="grayText">Gold earned:</span> <span class="blueText">+${gold}</span></h1>
</body>
</html>