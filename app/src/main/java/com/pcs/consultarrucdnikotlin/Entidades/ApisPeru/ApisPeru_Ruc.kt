package com.pcs.consultarrucdnikotlin.Entidades.ApisPeru

import com.google.gson.annotations.SerializedName

class ApisPeru_Ruc {
    @SerializedName("ruc")
    var ruc: String = ""
    @SerializedName("razonSocial")
    var razonSocial: String = ""
    @SerializedName("nombreComercial")
    var nombreComercial: String = ""
    @SerializedName("direccion")
    var direccion: String = ""
    @SerializedName("estado")
    var estado: String = ""
    @SerializedName("condicion")
    var condicion: String = ""
    @SerializedName("departamento")
    var departamento: String = ""
    @SerializedName("provincia")
    var provincia: String = ""
    @SerializedName("distrito")
    var distrito: String = ""
    @SerializedName("profesion")
    var profesion: String = ""

    fun getDireccionCompleta(): String {
        return "$direccion $departamento $provincia $distrito"
    }
}