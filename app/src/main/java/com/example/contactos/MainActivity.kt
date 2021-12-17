package com.example.contactos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.contactos.modelo.ContactoInformacion
import com.example.contactos.modelo.InformacionContacto
import com.example.contactos.modelo.Solicitud
import com.example.contactos.ui.theme.ContactosTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactosTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
    ) {
        Navigation(navController = navController)
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = "Visualizar", fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = Color(0xFFBB86FC),
        contentColor = Color.Black
    )
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController) {
    val items = listOf(
        MenuNavegacion.Home,
        MenuNavegacion.Profile,
        MenuNavegacion.Settings,

        )

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.listar),
                contentDescription = "",
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            )

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {

                navController.navigate(items.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

                scope.launch {
                    scaffoldState.drawerState.close()
                }

            })
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "contatos",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}
@Composable
fun DrawerItem(item: MenuNavegacion, selected: Boolean, onItemClick: (MenuNavegacion) -> Unit) {
    val background = if (selected) R.color.purple_200 else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 16.sp,
            color = Color.Black
        )

    }

}@Composable
fun HomeScreen() {
    insert()
}
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        delete()
    }
}
@Composable
fun insert() {
    var textFieldValueNombre by remember { mutableStateOf("") }
    var textFieldValueApellido by remember{ mutableStateOf("") }
    var textFieldValueTelefono by remember { mutableStateOf("") }
    var textFieldValueCorreo by remember { mutableStateOf("") }
    var textFieldValueDescripcion by remember { mutableStateOf("") }
    val context= LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize() ,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Spacer(modifier = Modifier.padding(40.dp))
        Text(
            text = "INSERTAR",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )



        TextField(
            value = textFieldValueNombre,
            onValueChange = { nuevo ->
                textFieldValueNombre = nuevo
            },
            label = {
                Text(text = "Introducir nombre")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )


        TextField(
            value = textFieldValueApellido,
            onValueChange = { nuevo ->
                textFieldValueApellido = nuevo
            },
            label = {
                Text(text = "Introducir apellido")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        TextField(
            value = textFieldValueTelefono,
            onValueChange = { nuevo ->
                textFieldValueTelefono = nuevo
            },
            label = {
                Text(text = "Introducir telefono")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )
        TextField(
            value = textFieldValueCorreo,
            onValueChange = { nuevo ->
                textFieldValueCorreo = nuevo
            },
            label = {
                Text(text = "Introducir correo")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )
        TextField(
            value = textFieldValueDescripcion,
            onValueChange = { nuevo ->
                textFieldValueDescripcion = nuevo
            },
            label = {
                Text(text = "Introducir descripcion")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        Spacer(Modifier.height(20.dp) )


        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(width = 100.dp, height = 50.dp)
            ,


            onClick = {
                if(textFieldValueNombre.isEmpty()||textFieldValueTelefono.isEmpty()){
                    Toast.makeText(context,"Debe rellenar los campos", Toast.LENGTH_SHORT).show()

                }
                else {

                    insertar(textFieldValueNombre, textFieldValueApellido, textFieldValueTelefono, textFieldValueCorreo, textFieldValueDescripcion)
                    Toast.makeText(context,"Datos correctos", Toast.LENGTH_LONG).show()
                    textFieldValueNombre = ""
                    textFieldValueApellido = ""
                    textFieldValueTelefono = ""
                    textFieldValueCorreo = ""
                    textFieldValueDescripcion = ""
                }
            }
        ){
            Text(text = "Insert"
            )
        }


    }

}
fun insertar(nombre:String,apellido:String,telefono:String,correo:String,descripcion:String) {

    val url =
        "http://iesayala.ddns.net/breynertimana/aniadirContacto.php/?nombre=$nombre&apellido=$apellido&telefono=$telefono&correo=$correo&descripcion=$descripcion"

    leerUrl(url)
}
@Composable
fun delete() {

    var textFieldValueNombre by remember { mutableStateOf("") }
    val context= LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize() ,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Text(
            text = "SQL DELETE",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        TextField(
            value = textFieldValueNombre,
            onValueChange = { nuevo ->
                textFieldValueNombre = nuevo
            },
            label = {
                Text(text = "Introducir nombre")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        Spacer(Modifier.height(20.dp) )


        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(width = 100.dp, height = 50.dp)
            ,


            onClick = {
                if(textFieldValueNombre.isEmpty()){
                    Toast.makeText(context,"Rellene los campos", Toast.LENGTH_SHORT).show()

                }
                else {

                    eliminar( textFieldValueNombre)
                    Toast.makeText(context,"Eliminado", Toast.LENGTH_LONG).show()

                    textFieldValueNombre = ""

                }

            }
        ){
            Text(text = "Borrar"
            )
        }


    }

}
fun eliminar(nombre:String){

    val url = "http://iesayala.ddns.net/breynertimana/eliminarContacto.php/?&nombre=$nombre"

    leerUrl(url)

}
@Composable
fun cargarJson(): InformacionContacto {
    val context = LocalContext.current
    var users by remember { mutableStateOf(InformacionContacto()) }
    val user = ContactoInformacion.contactoInformacion.informacionContacto()

    user.enqueue(object : Callback<InformacionContacto> {
        override fun onResponse(
            call: Call<InformacionContacto>,
            response: Response<InformacionContacto>
        ) {
            val userInfo: InformacionContacto? = response.body()
            if (userInfo != null) {
                users = userInfo
            }
            else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<InformacionContacto>, t: Throwable)
        {
            Log.d("datos",users.toString())
        }

    })

    return users
}
fun leerUrl(urlString:String){
    GlobalScope.launch(Dispatchers.IO)   {
        val response = try {
            URL(urlString)
                .openStream()
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            "Error with ${e.message}."
            Log.d("io", e.message.toString())
        } catch (e: Exception) {
            "Error with ${e.message}."
            Log.d("io", e.message.toString())
        }
    }

    return
}
@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Llamada()
    }
}
@Composable
fun Llamada() {
    var lista = cargarJson()
    Row() {
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Nombre",
                    color = Color(0xFFBB86FC),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(20.dp),

                    )
            }
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Apellido",
                    color = Color(0xFFBB86FC),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(20.dp),

                    )
            }
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Telefono",
                    color = Color(0xFFBB86FC),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(20.dp),

                    )
            }
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Correo",
                    color = Color(0xFFBB86FC),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(20.dp),

                    )
            }
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Descripcion",
                    color = Color(0xFFBB86FC),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(20.dp),

                    )
            }
        }
        LazyColumn()

        {
            items(lista) { usu ->
                Box(
                    Modifier
                        .background(Color.White)
                        .width(370.dp)
                ) {
                    Row() {
                        Column(
                            modifier = Modifier.weight(4f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = usu.nombre.toString(),
                                color = Color.Blue,
                                textAlign = TextAlign.Center,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(25.dp),

                                )
                        }
                        Column(
                            modifier = Modifier.weight(3f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = usu.apellido.toString(),
                                color = Color.Blue,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(25.dp),
                            )
                        }
                        Column(
                            modifier = Modifier.weight(3f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = usu.telefono.toString(),
                                color = Color.Blue,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(25.dp),
                            )
                        }
                        Column(
                            modifier = Modifier.weight(3f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = usu.correo.toString(),
                                color = Color.Blue,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(25.dp),
                            )
                        }
                        Column(
                            modifier = Modifier.weight(3f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = usu.descripcion.toString(),
                                color = Color.Blue,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(25.dp),
                            )
                        }

                    }


                }
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                )


            }
        }


    }

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController, startDestination = MenuNavegacion.Home.route) {

        composable(MenuNavegacion.Home.route) {
            HomeScreen()
        }

        composable(MenuNavegacion.Profile.route) {
            ProfileScreen()
        }

        composable(MenuNavegacion.Settings.route) {
            SettingsScreen()
        }


    }

}
