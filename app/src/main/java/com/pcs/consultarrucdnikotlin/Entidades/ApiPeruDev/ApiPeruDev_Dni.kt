package com.pcs.consultarrucdnikotlin.Entidades.ApiPeruDev

import com.google.gson.annotations.SerializedName

class ApiPeruDev_Dni {
    @SerializedName("numero")
    var numero: String = ""
    @SerializedName("nombre_completo")
    var nombre_completo: String = ""
    @SerializedName("nombres")
    var nombres: String = ""
    @SerializedName("apellido_paterno")
    var apellido_paterno: String = ""
    @SerializedName("apellido_materno")
    var apellido_materno: String = ""
    @SerializedName("codigo_verificacion")
    var codigo_verificacion: String = ""
    @SerializedName("fecha_nacimiento")
    var fecha_nacimiento: String = ""
    @SerializedName("sexo")
    var sexo: String = ""
}