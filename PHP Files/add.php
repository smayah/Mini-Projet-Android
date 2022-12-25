<?php

include ("db_connect.php");

$response=array();

if( isset($_GET["nom"]) && isset($_GET["num"]) && isset($_GET["user"]) )
{
	$nom=$_GET["nom"];
	$num = $_GET["num"];
	$user = $_GET["user"];
	
	$req=mysqli_query($cnx, " insert into contacts(nom,num,user) values('$nom',$num,$user) ");
	
	
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
