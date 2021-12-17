package com.example.contactos

sealed class MenuNavegacion(var route: String, var icon: Int, var title: String) {
    object Home : MenuNavegacion("home", R.drawable.aniadir, "Añadir")
    object Profile : MenuNavegacion("profile", R.drawable.eliminar, "Eliminar")
    object Settings : MenuNavegacion("settings", R.drawable.listar, "Listar")
}
