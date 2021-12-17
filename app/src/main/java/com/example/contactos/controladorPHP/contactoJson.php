<?php

$servidor = "localhost";
$usuario = "root";
$contrasenia = "clave";
$baseDatos = "BDBreyner";

//Creamos la conexión
$conexion = mysqli_connect($servidor, $usuario, $contrasenia,$baseDatos)
or die("Error al realizar la conexion");

//generamos la consulta
$sql = "SELECT * FROM CONTACTOS";
mysqli_set_charset($conexion, "utf8");

if(!$result = mysqli_query($conexion, $sql)) die();

$contactos = array(); //creamos un array para recibir la informacion

while($row = mysqli_fetch_array($result))
{
    $nombre=$row['NOMBRE'];
    $apellido=$row['APELLIDO'];
    $telefono=$row['TELEFONO'];
    $correo=$row['CORREO'];
    $descripcion=$row['DESCRIPCION'];


    $contactos[] = array('NOMBRE'=> $nombre, 'APELLIDO'=> $apellido, 'TELEFONO'=> $telefono, 'CORREO'=> $correo, 'DESCRIPCION'=> $descripcion);

}

//desconectamos la base de datos
$close = mysqli_close($conexion)
or die("Ha sucedido un error inexperado en la desconexion de la base de datos");


//Creamos el JSON
$json_string = json_encode($contactos);
echo $json_string;
?>