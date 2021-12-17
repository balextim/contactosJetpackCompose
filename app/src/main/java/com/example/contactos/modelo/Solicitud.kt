package com.example.contactos.modelo

import com.example.contactos.modelo.InformacionContacto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val urlBase = "http://iesayala.ddns.net/breynertimana/"

interface Solicitud {
    @GET("contactoJson.php")
    fun informacionContacto (): Call<InformacionContacto>
}

object ContactoInformacion {
    val contactoInformacion: Solicitud

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        contactoInformacion = retrofit.create(Solicitud::class.java)
    }


}