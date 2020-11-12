package com.pcs.consultarrucdnikotlin.Utilidades

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class Utilidad {

    companion object {
        fun cerrarApp(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("¿Esta seguro de salir de la app?")
            builder.setCancelable(false)
            builder.setPositiveButton("Aceptar") { _, _ -> (context as Activity).finish() }
            builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
            builder.create().show()
        }

        fun mostrarMensaje(titulo: String?, mensaje: String?, context: Context?) {
            val builder = AlertDialog.Builder(context!!)
            builder.setMessage(mensaje)
                .setTitle(titulo)
                .setCancelable(false)
                .setPositiveButton("Aceptar"
                ) { dialog, _ -> dialog.cancel() }
            builder.create().show()
        }

        fun ocultarTeclado(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun limpiarEditText(view: View) {
            val it: Iterator<View> = view.touchables.iterator()
            while (it.hasNext()) {
                val v = it.next()
                if (v is EditText) v.setText("")
            }
        }
    }

}