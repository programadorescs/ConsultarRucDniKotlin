package com.pcs.consultarrucdnikotlin.AccesoApi

import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import com.pcs.consultarrucdnikotlin.Entidades.ApiPeruDev.ApiPeruDevData_Dni
import com.pcs.consultarrucdnikotlin.Entidades.ApiPeruDev.ApiPeruDevData_Ruc
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class GetHttpApiPeruDev(private val contexto: Context,
                        private val progressBar: ProgressBar,
                        private val apiPeruDevListener: ApiPeruDevListener):
    AsyncTask<String, Void, String>() {

    private var tipoDoc: String = ""

    override fun doInBackground(vararg p0: String): String {
        var resultado = ""

        try {
            tipoDoc = p0[0]
            val url: URL
            if(p0[0].trim().length == 8)
                url = URL("https://apiperu.dev/api/dni/" + p0[0].trim())
            else
                url = URL("https://apiperu.dev/api/ruc/" + p0[0].trim())

            val httpsURLConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.setRequestProperty("Content-Type", "application/json")
            httpsURLConnection.setRequestProperty("Authorization", "Bearer " + p0[1])
            httpsURLConnection.requestMethod = "GET"

            resultado = inputStreamToString(BufferedInputStream(httpsURLConnection.inputStream))
        } catch (e: Exception) {
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
                val cliente = gson.fromJson(result, ApiPeruDevData_Ruc::class.java)
                if(cliente.success.toLowerCase() == "true"){
                    apiPeruDevListener.apiPeruDevRuc(cliente)
                } else {
                    Toast.makeText(contexto, "NO existen datos", Toast.LENGTH_SHORT).show()
                }
            } else {
                val cliente = gson.fromJson(result, ApiPeruDevData_Dni::class.java)
                if(cliente.success.toLowerCase() == "true"){
                    apiPeruDevListener.apiPeruDevDni(cliente)
                } else {
                    Toast.makeText(contexto, "NO existen datos", Toast.LENGTH_SHORT).show()
                }
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

    interface ApiPeruDevListener {
        fun apiPeruDevDni(entidad: ApiPeruDevData_Dni)
        fun apiPeruDevRuc(entidad: ApiPeruDevData_Ruc)
    }
}