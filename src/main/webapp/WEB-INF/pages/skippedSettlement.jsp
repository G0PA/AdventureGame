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
.crimsonText{
color:red;
}
</style>
<head>
</head>
<body background= "resources/images/${resource}.jpg" class=bg>
	<h1 style="color:crimson;">You Skipped the Settlement</h1>
	<h1 style="color:crimson;">Enemies left on this difficulty increased to <span class="crimsonText">${enemiesLeft}</span></h1>
	<a href="http://localhost:8081/AdventureGame/hello" class="button">CONTINUE</a>
</body>
</html>