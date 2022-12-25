<?php

include ("db_connect.php");

$response=array();

if(isset($_GET["user"]) )   
{
	$user=$_GET["user"];
	
	$req=mysqli_query($cnx," select * from contacts where user='$user' order by nom  ");
	
	if(mysqli_num_rows($req) > 0)
	{
		$tmp=array();
		
		$response["contacts"]=array();
		while ($cur = mysqli_fetch_assoc($req)) {

			$tmp["user"] = $cur["user"];
			$tmp["nom"] = $cur["nom"];
			$tmp["num"] = $cur["num"];
			array_push($response["contacts"],$tmp);
		}
		
		
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