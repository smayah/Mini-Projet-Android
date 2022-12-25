<?php
include("db_connect.php");

$response=array();

if(isset($_GET["contact_id"])   )
{
	$contact_id=$_GET["contact_id"];

	
	$req=mysqli_query($cnx, " delete from contacts where  id = '$contact_id' ");
	
	if($req)
	{
		$response["success"]=1;
		$response["message"]="successful delete";
		
		
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