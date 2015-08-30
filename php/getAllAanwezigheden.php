<?php


// Create connection
  $servername = "sql2.freesqldatabase.com";
$username = "sql287009";
$password = "eN4*aQ4!";
$con = $con = mysql_connect($servername, $username, $password);

if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }
mysql_select_db("sql287009", $con);

$result = mysql_query("SELECT * FROM Aanwezigheden");

while($row = mysql_fetch_assoc($result))
  {
	$output[]=$row;
  }

print(json_encode($output));

mysql_close($con);


?>