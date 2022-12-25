<?php

$cnx=mysqli_connect("localhost","id19877147_omarandnour","Passwords?123");
if(!$cnx)
{
	echo "erreur de connexion au serveur";
	
}

$db=mysqli_select_db($cnx,"id19877147_samir");
if(!$db)
{
	echo "erreur de connexion à la base";
	
}

?>
