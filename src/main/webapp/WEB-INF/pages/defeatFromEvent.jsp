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
	<h1 style="color:red;">You Were Defeated</h1>
	<a href="http://localhost:8081/AdventureGame/play" class="button">PLAY AGAIN</a>
</body>
</html>