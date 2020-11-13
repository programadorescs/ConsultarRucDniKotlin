package com.pcs.consultarrucdnikotlin.Entidades.ApiPeruDev

import com.google.gson.annotations.SerializedName

class ApiPeruDevData_Ruc {
    @SerializedName("success")
    var success: String = ""
    @SerializedName("data")
    var data: ApiPeruDev_Ruc? = null

    inner class ApiPeruDev_Ruc {
        @SerializedName("ruc")
        var ruc: String = ""
        @SerializedName("nombre_o_razon_social")
        var nombre_o_razon_social: String = ""
        @SerializedName("nombre_comercial")
        var nombre_comercial: String = ""
        @SerializedName("estado")
        var estado: String = ""
        @SerializedName("condicion")
        var condicion: String = ""
        @SerializedName("direccion")
        var direccion: String = ""
        @SerializedName("direccion_completa")
        var direccion_completa: String = ""
    }
}