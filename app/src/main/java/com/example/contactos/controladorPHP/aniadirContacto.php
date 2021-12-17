<?php

$servidor = "localhost";
$usuario = "root";
$contrasenia = "clave";
$baseDatos = "BDBreyner";

//abrimos la conexion con el servidor
$conexion = mysqli_connect($servidor, $usuario, $contrasenia,$baseDatos)
or die("Error al realizar la conexion");

//generamos la consulta
$nombre = $_GET["nombre"];
$apellido = $_GET["apellido"];
$telefono = $_GET["telefono"];
$correo = $_GET["correo"];
$descripcion = $_GET["descripcion"];

  $sql = "INSERT INTO CONTACTOS (nombre, apellido, telefono, correo, descripcion) VALUES ('$nombre', '$apellido', '$telefono', '$correo', '$descripcion')";
echo $sql;

mysqli_set_charset($conexion, "utf8"); //codificacion utf-8 para evitar errores
if (mysqli_query($conexion, $sql)) {
      echo "New record create succesfully";
} else {
      echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
}

//desconectamos la base de datos
$close = mysqli_close($conexion)
or die("Error al realizar la conexion");




?>