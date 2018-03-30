
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
.greenText{
color:Chartreuse;
}
</style>
<head>
<title>Welcome</title>
</head>
<body background= "http://localhost:8081/AdventureGame/resources/images/ranger.jpg"class=bg>
<div class="asd">
	<h1 style="color:Chartreuse"><font size="10"><i>RANGER</i></font></h1>
	<h1 style="color:white"><i>Passive: Your pet fights with you, having 15% of your Attack Min and Max and same Critical Chance</i></h1>
	<h1 style="color:white"><i>Active: Cast <span class="greenText">Ranger Sight</span> increasing your Attack Damage Min and Max with 80% for one attack</i></h1>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<a href="http://localhost:8081/AdventureGame/greenWoods" class="button">CHOOSE RANGER</a>
	<a href="http://localhost:8081/AdventureGame/play" class="button">RETURN</a>
	</div>
	
</body>
</html>