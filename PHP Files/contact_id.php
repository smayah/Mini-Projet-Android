<?php

include ("db_connect.php");

$response=array();

if(isset($_GET["nom"]) && isset($_GET["num"] ) && isset($_GET["user_id"]) )   
{
	$user_id=$_GET["user_id"];
    $num=$_GET["num"];
    $nom=$_GET["nom"];
	
	$req=mysqli_query($cnx," select * from contacts  where nom='$nom' and num = '$num' and user = '$user_id' ");
	
	if(mysqli_num_rows($req) > 0)
	{
		
		$cur=mysqli_fetch_assoc($req);
		$response["contact_id"]=$cur["id"];
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