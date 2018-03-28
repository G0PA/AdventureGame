
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
.violetText{
color:violet;
}
</style>
<head>
<title>Welcome</title>
</head>
<body background= "http://localhost:8081/AdventureGame/resources/images/berserkWallpaper.jpg"class=bg>
<div class="asd">
	<h1 style="color:violet"><font size="10"><i>Berserk</i></font></h1>
	<h1><i>Passive: Every 25 missing health Increase Damage Min and Max with 1</i></h1>
	<h1><i>Active: Cast <span class="violetText">Bloodlust</span> making your next attack a critical and Increasing it's Attack Damage Min and Max by 1.5% for each point of critical strike you have</i></h1>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<a href="http://localhost:8081/AdventureGame/greenWoods" class="button">CHOOSE BERSERK</a>
	<a href="http://localhost:8081/AdventureGame/play" class="button">RETURN</a>
	</div>
	
</body>
</html>