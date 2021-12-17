<?php

$servidor = "localhost";
$usuario = "root";
$contrasenia = "clave";
$baseDatos = "BDBreyner";

//Creamos la conexión con la base de datos del servidor
$conexion = mysqli_connect($servidor, $usuario, $contrasenia,$baseDatos)
or die("Error inesperado");

//generamos la consulta
$nombre = $_GET["nombre"];

  $sql = "DELETE FROM CONTACTOS WHERE NOMBRE='$nombre'";
echo $sql;

mysqli_set_charset($conexion, "utf8"); //codificación de los datos utf-8 para evitar errores
if (mysqli_query($conexion, $sql)) {
      echo "New record create succesfully";
} else {
      echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
}

//cerramos la conexion con la base de datos del servidor
$close = mysqli_close($conexion)
or die("Error a la hora de conectarse");




?>