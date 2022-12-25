<?php

include ("db_connect.php");

$response=array();

if( isset($_GET["nom"]) && isset($_GET["password"]) )
{
	$nom=$_GET["nom"];
	$password = $_GET["password"];
	
	$req=mysqli_query($cnx, "select * from user where name = '$nom' and password = '$password' ");
	
	
	if(mysqli_num_rows($req) > 0)
	{
	$response["success"]=1;
	$response["message"]="log in";
	
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