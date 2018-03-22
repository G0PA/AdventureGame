
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
.redText
{
color: Crimson;
}
</style>
<head>
<title>Settlement</title>
</head>
<body background= "resources/images/${resource}.jpg"class=bg>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<h1>${cheating}</h1>
	<h1 style="color:Chartreuse;">SETTLEMENT</h1>
	<h1 style="color:Chartreuse;"> You came across ${settlementName}</h1>
	<a href="http://localhost:8081/AdventureGame/settlement" class="button">GO IN</a>
	<a href="http://localhost:8081/AdventureGame/skipSettlement" class="button2">SKIP SETTLEMENT(+1 enemy encounter until Boss)</a>
</body>
</html>