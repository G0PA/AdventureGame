
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
close-image{
padding: 5px 5px;
border: 2px solid black;
width:50px;
height:13px;
}
.redText{
color:Crimson;
font-size:40px;
}
.blueText{
color:blue;
font-size:40px;
}
.greenText{
color:Chartreuse;
font-size:40px;
}
.brownText{
color:brown;
font-size:40px;
}
.header{
text-align: center;
font-size:40px;
}
.images:hover{
background-color: red; /* Green */
    color: red;
    
}
.images{
width: 24%;
}


</style>
<script>
function redirect(){
	window.location = "http://localhost:8081/AdventureGame/mage";
}
function redirect2(){
	window.location = "http://localhost:8081/AdventureGame/hello";
}
function redirect3(){
	window.location = "http://localhost:8081/AdventureGame/hello";
}
function redirect4(){
	window.location = "http://localhost:8081/AdventureGame/hello";
}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-color:black">
<h1 class="header"style="color:yellow">Choose a Class</h1>

<input class="images" type="image" onClick="redirect()" src="https://i.gyazo.com/e7168fd9d60a735c04e3728d7fc1a8d1.png" alt="Submit" width="470" height="1000">



<input class="images" type="image" onClick="redirect2()" src="https://i.pinimg.com/736x/f6/03/e0/f603e0de490ed5454c8f58e98c254f1e.jpg" alt="Submit" width="470" height="1000">


<input class="images" type="image" onClick="redirect3()" src="https://pbs.twimg.com/media/BgrdApmCMAEOAzM.jpg:large" alt="Submit" width="470" height="1000">

<input class="images" type="image" onClick="redirect4()" src="https://68.media.tumblr.com/2ea7a52b82323f0239756ec71d6edf3d/tumblr_oponspCcCW1vf5c5jo1_1280.jpg" alt="Submit" width="460" height="1000">
</body>
</html>