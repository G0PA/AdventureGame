
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
</style>
<head>
<title>Welcome</title>
</head>
<body background= "http://localhost:8081/AdventureGame/resources/images/start.jpg"class=bg>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<a href="http://localhost:8081/AdventureGame/hello" class="button">Travel</a>
</body>
</html>