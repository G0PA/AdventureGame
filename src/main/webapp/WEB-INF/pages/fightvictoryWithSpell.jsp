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
</style>
<body background= "resources/images/${resource}.jpg" class=bg>
<h1 style="background-color:Chartreuse;">${message2}</h1>
<h1 style="background-color:Chartreuse;">${cheating}</h1>
<h2 style="color:yellow"><i>${spellDamage}</i></h2>
<h2 style="color:yellow"><i>${hit1}</i></h2>
	<h2 style="color:yellow"><i>${hit2}</i></h2>
	<h2 style="color:yellow"><i>${hit3}</i></h2>
	<h2 style="color:yellow"><i>${hit4}</i></h2>
	<h3 style="color:yellow"><i>${poison}</i></h3>
	<h4 style="color:yellow"><i>${yourPetAttacks}${petCritically}${dealing}${petDamage} </i></h4>
	<a href="http://localhost:8081/AdventureGame/${zone}" class="button">VICTORY</a>
	<h2 style="color:MediumBlue"><i>You regenerate <span class="blue">${hpRegen}</span> health and <span class="blue">${manaRegen}</span> mana</i></h2>
	<h2 style="color:Yellow"><i>${soul}</i></h2>
	<h1><span class="grayText">Gold earned:</span> <span class="blueText">+${gold}</span></h1>
</body>
</html>