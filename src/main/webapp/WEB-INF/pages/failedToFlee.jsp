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
.tooltip {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}
.tooltip .tooltiptext {
    visibility: hidden;
    width: 600px;
    background-color: black;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
}

.tooltip {font-size: 24px;}
.tooltip {
padding:15px;
border-radius: 2px;
background-color: yellow;
    color: black;
    border: 2px solid black;

}
.tooltip{
line-height: 3em;
}
.yellowText{
color:yellow;
}
.violetText{
color:green
}

.tooltip2 {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}

.tooltip2 .tooltiptext2 {
    visibility: hidden;
    width: 600px;
    background-color: black;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
}

.tooltip2:hover .tooltiptext2 {
    visibility: visible;
}

.tooltip2 {font-size: 24px;}
.tooltip2 {
padding:15px;
border-radius: 2px;
background-color: green;
    color: black;
    border: 2px solid black;

}
.tooltip2{
line-height: 3em;
}

.tooltip3 {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}

.tooltip3 .tooltiptext3 {
    visibility: hidden;
    width: 600px;
    background-color: black;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
}

.tooltip3:hover .tooltiptext3 {
    visibility: visible;
}

.tooltip3 {font-size: 24px;}
.tooltip3 {
padding:15px;
border-radius: 2px;
background-color: brown;
    color: black;
    border: 2px solid black;

}
.tooltip3{
line-height: 3em;
}
.brownText{
color:brown
}
</style>
<head>
</head>
<body background= "resources/images/${resource}.jpg" class=bg>
<h1 style="background-color:Chartreuse;">${message2}</h1>
	<h1>You failed to escape</h1>
	<h1>${cheater}</h1>
	<h1>${boss}</h1>
	<h2 style="color:red;">While trying to flee ${resource} damaged you <span class="maroonText">${enemyCritically}</span>dealing <span class="maroonText">${enemyDamage}</span> damage</h2>
	<h2 style="color:red;">While trying to flee ${resource} damaged you <span class="maroonText">${enemyCritically2}</span>dealing <span class="maroonText">${enemyDamage2}</span> damage</h2>
	<div onclick="location.href='http://localhost:8081/AdventureGame/fight1';" style="cursor:pointer;" class="tooltip2"><i>${skill1}</i> 
  <span class="tooltiptext2"><i><span class="violetText">${tooltip1}</span></i></span>
</div>
<div onclick="location.href='http://localhost:8081/AdventureGame/fight2';" style="cursor:pointer;" class="tooltip3"><i>${skill2}</i> 
  <span class="tooltiptext3"><i><span class="brownText">${tooltip2}</span></i></span>
</div>
	<div onclick="location.href='http://localhost:8081/AdventureGame/fightWithSpell';" style="cursor:pointer;" class="tooltip"><i>Cast ${spell}</i> 
  <span class="tooltiptext"><i><span class="yellowText">${tooltip}</span></i></span>
</div>
</body>
</html>