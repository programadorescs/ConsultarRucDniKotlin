package com.pcs.consultarrucdnikotlin.AccesoApi

import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import com.pcs.consultarrucdnikotlin.Entidades.ApisPeru.ApisPeru_Dni
import com.pcs.consultarrucdnikotlin.Entidades.ApisPeru.ApisPeru_Ruc
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class GetHttpApisPeru(private val contexto: Context,
                      private val progressBar: ProgressBar,
                      private val apisPeruListener: ApisPeruListener):
        AsyncTask<String, Void, String>() {

    private var tipoDoc: String = ""

    override fun doInBackground(vararg p0: String): String {
        var resultado = ""

        try {
            tipoDoc = p0[0]
            val url: URL
            if(p0[0].trim().length == 8)
                url = URL("https://dniruc.apisperu.com/api/v1/dni/" + p0[0].trim() + "?token=" + p0[1].trim())
            else
                url = URL("https://dniruc.apisperu.com/api/v1/ruc/" + p0[0].trim() + "?token=" + p0[1].trim())

            val httpsURLConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.setRequestProperty("Content-Type", "application/json")
            httpsURLConnection.requestMethod = "GET"

            resultado = inputStreamToString(BufferedInputStream(httpsURLConnection.inputStream))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return resultado
    }

    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.visibility = View.VISIBLE
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        progressBar.visibility = View.GONE
        if(result.isNotEmpty()) {
            val gson = Gson()

            if(tipoDoc.trim().length == 11) {
                apisPeruListener.obtenerRuc(gson.fromJson(result, ApisPeru_Ruc::class.java))
            } else {
                apisPeruListener.obtenerDni(gson.fromJson(result, ApisPeru_Dni::class.java))
            }
        } else {
            Toast.makeText(contexto, "NO existen datos", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        val sb = StringBuilder()
        val br = BufferedReader(InputStreamReader(inputStream))

        try {
            br.forEachLine {
                sb.append(it)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

        return sb.toString()
    }

    interface ApisPeruListener {
        fun obtenerRuc(entidad: ApisPeru_Ruc)
        fun obtenerDni(entidad: ApisPeru_Dni)
    }
}