package com.pcs.consultarrucdnikotlin.Entidades.ApisPeru

import com.google.gson.annotations.SerializedName

class ApisPeru_Dni {
    @SerializedName("dni")
    var dni: String = ""
    @SerializedName("nombres")
    var nombres: String = ""
    @SerializedName("apellidoPaterno")
    var apellidoPaterno: String = ""
    @SerializedName("apellidoMaterno")
    var apellidoMaterno: String = ""
    @SerializedName("codVerifica")
    var codVerifica: String = ""

    fun getApellidoNombre(): String {
        return "$apellidoPaterno $apellidoMaterno $nombres"
    }
}