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
.maroonText
{
color:Maroon;
}
</style>
<head>
</head>
<body background= "resources/images/${resource}.jpg" class=bg>
	<h1>You failed to escape</h1>
	<h2 style="color:red;">While trying to flee ${resource} damaged you <span class="maroonText">${enemyCritically}</span>dealing <span class="maroonText">${enemyDamage}</span> damage</h2>
	<h2 style="color:red;">While trying to flee ${resource} damaged you <span class="maroonText">${enemyCritically2}</span>dealing <span class="maroonText">${enemyDamage2}</span> damage</h2>
	<a href="http://localhost:8081/AdventureGame/fight" class="button">CONTINUE</a>
</body>
</html>