
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
<body background= "http://localhost:8081/AdventureGame/resources/images/warriorWallpaper.jpg"class=bg>
<div class="asd">
	<h1 style="color:red"><font size="10"><i>Warrior</i></font></h1>
	<h1><i>Passive: every 2 points of Armor increases Attack Damage Min and Max with 1</i></h1>
	<h1><i>Active: Cast <span class="redText">Endurance</span> dealing damage equal to 10% of your Missing Health and Healing yourself for that amount.Cannot Critically hit but ignores armor</i></h1>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<a href="http://localhost:8081/AdventureGame/greenWoods" class="button">CHOOSE WARRIOR</a>
	<a href="http://localhost:8081/AdventureGame/play" class="button">RETURN</a>
	</div>
	
</body>
</html>