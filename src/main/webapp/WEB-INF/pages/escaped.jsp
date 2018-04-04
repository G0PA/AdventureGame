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
.blue{
color: blue;
}
</style>
<head>
</head>
<body background= "resources/images/escaped.jpg" class=bg>
	<h1>You Have Escaped</h1>
	<h2 style="color:CornflowerBlue "><i>You regenerate <span class="blue">${hpRegen}</span> health and <span class="blue">${manaRegen}</span> mana</i></h2>
	<a href="${zone}" class="button">CONTINUE</a>
</body>
</html>