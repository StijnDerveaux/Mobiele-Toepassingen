<?php
// Create connection
  $servername = 'sql2.freesqldatabase.com';
$username = 'sql287009';
$password = 'eN4*aQ4!';
$con = $con = mysql_connect($servername, $username, $password);


mysql_select_db('sql287009', $con);

$nummer=$_POST['nummer'];
$dag=$_POST['dag'];
$maand=$_POST['maand'];
$aan=$_POST['aan'];
$ver=$_POST['ver'];


mysql_query("INSERT INTO Aanwezigheden
 VALUES (NULL,'{$nummer}', '{$dag}', '{$maand}','{$aan}','{$ver}')"); 
mysql_close();
	
	
?>