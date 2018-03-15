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
</style>
<body background= "http://localhost:8081/AdventureGame/resources/images/boundEntity.jpg">
	<h1>${message2}</h1>
	<a href="http://localhost:8081/AdventureGame/fight" class="button">Attack</a>
	
	<h3> ${damageDealt} </h3>
	<h3> ${enemy}</h3>
</body>
</html>