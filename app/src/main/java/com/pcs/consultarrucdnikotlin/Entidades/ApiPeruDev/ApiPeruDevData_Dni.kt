package com.pcs.consultarrucdnikotlin.Entidades.ApiPeruDev

import com.google.gson.annotations.SerializedName

class ApiPeruDevData_Dni {
    @SerializedName("success")
    var success: String = ""
    @SerializedName("data")
    var data: ApiPeruDev_Dni? = null
}