<?php
include ("db_connect.php");

$response=array();


if( isset($_GET["nom"]) && isset($_GET["num"]) && isset($_GET["contact"]) )
	
	{
		
		$contact=$_GET["contact"];
		$nom=$_GET["nom"];
		$num=$_GET["num"];
		
		$req=mysqli_query($cnx, " update contacts set nom='$nom' , num='$num' where id='$contact' ");
		
		if($req)
		{
			$response["success"]=1;
			$response["message"]="updated successfully";
			
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