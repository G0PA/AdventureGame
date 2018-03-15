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

.button2 {font-size: 24px;}
.button2 {
padding: 30px 40px;
border-radius: 2px;
background-color: red;
    color: black;
    border: 2px solid red;

}
.button2{
line-height: 5em;
}
.button2:hover {
background-color: #1E90FF; /* Green */
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
color: Chartreuse;
}
.blueText{
color: green;
}
</style>
<body background= "resources/images/${resource}.jpg" class=bg>
	<h1 style="background-color:Chartreuse;">${cheater}${message}</h1>
	<h2><span class="blueText">Enemy encounters left until next difficulty:</span> <span class="redText">${leftEnemies}</span></h2>
	<h2 style="color:red;">${enemyInfo}</h2>
	<a href="http://localhost:8081/AdventureGame/fight" class="button">FIGHT</a>
	<a href="http://localhost:8081/AdventureGame/flee" class="button2">ATTEMP TO FLEE</a>
</body>
</html>