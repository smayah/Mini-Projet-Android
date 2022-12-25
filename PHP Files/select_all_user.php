<?php

include("db_connect.php");

$response=array();
if(isset($_GET["nom"])   )
{
	$nom=$_GET["nom"];

    $req=mysqli_query($cnx, " select * from user where name = '$nom'");

    if(mysqli_num_rows($req) >  0)
    {
    	$response["success"]=1;
    	$response["found"]=1;
    	echo json_encode($response);
    
	}
    else
    {
    	$response["success"]=0;
    	$response["found"]=0;
	    echo json_encode($response);
	
    }
}
else {
    $response["success"] = 0 ;
    $response["message"] = "missing requested data" ;
    
}

?>