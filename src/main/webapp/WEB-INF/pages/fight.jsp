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
</style>

<body background= "resources/images/${resource}.jpg" class="bg">
	<h1 style="background-color:Chartreuse;">${message2}</h1>
	<a href="http://localhost:8081/AdventureGame/fight" class="button">Attack</a>
	<h3><span class="blueText">You</span> <span class="blueText">${critically}</span><span class="grayText">damage the enemy dealing</span><span class="blueText"> ${damageDealt}</span> <span class="grayText">damage </span></h3>
	<h3 style="color:Maroon;">Enemy health left: <span class="redText">${enemy}</span></h3>
	<h3 style="color:red;"> ${enemyName} ${enemyCritically}<span class="maroonText">damages you dealing </span>${enemyDamage} <span class="maroonText">damage</span></h3>
</body>
</html>