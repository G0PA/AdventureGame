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
.redText{
color: crimson;
}
.greenText{
color:Chartreuse;
}

.button3 {font-size: 24px;}
.button3 {
position:relative;
padding: 30px 40px;
border-radius: 2px;
background-color: #1E90FF;
    color: black;
    border: 2px solid red;
    margin-top:50px;
    bottom: -125px

}
.button3{
line-height: 5em;
}
.button3:hover {
background-color: red; /* Green */
    color: black;
}
</style>
<head>
<title>Settlement</title>
</head>
<body background= "resources/images/${resource}.jpg"class=bg>
	<h1 style="background-color:Chartreuse;">${message}</h1>
	<h2 style="color:Chartreuse;"> ${event}</h2>
	<h2 style="color:red;"> ${event2}</h2>
		
	
	
	<a href="hello" class="button">CONTINUE</a>
	
</body>
</html>