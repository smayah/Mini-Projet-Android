<?php

include ("db_connect.php");

$response=array();

if( isset($_GET["nom"]) && isset($_GET["password"]) )
{
	$nom=$_GET["nom"];
	$password = $_GET["password"];
	
	$req=mysqli_query($cnx, " insert into user(name,password) values('$nom','$password') ");
	
	
	if($req)
	{
	$response["success"]=1;
	$response["message"]="inserted !";
	
	echo json_encode($response);
	
	}
	else
		{
			
	$response["success"]=0;
	$response["message"]="request error";
	
	echo json_encode($response);	
		
	}
	
	}
else
{
	$response["success"]=0;
	$response["message"]="required field is missing";
	
	echo json_encode($response);	
}

?>
