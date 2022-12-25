<?php

include ("db_connect.php");

$response=array();

if(isset($_GET["user"]) )   
{
	$user=$_GET["user"];
	
	$req=mysqli_query($cnx," select * from user where name='$user'  ");
	
	if(mysqli_num_rows($req) > 0)
	{
		
		$cur=mysqli_fetch_array($req);
		$response["id"]=$cur["id"];
		$response["success"]=1;
	    echo json_encode($response);
	} 
	else
	{
		$response["success"]=0;
		$response["message"]="no data found";
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