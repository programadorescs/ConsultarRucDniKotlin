package com.pcs.consultarrucdnikotlin.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pcs.consultarrucdnikotlin.R
import com.pcs.consultarrucdnikotlin.Utilidades.Utilidad

class ConfiguracionFragment : Fragment() {

    private lateinit var txtTokenApisPeru: EditText
    private lateinit var txtTokenApiPeruDev: EditText
    private lateinit var editor: SharedPreferences.Editor
    private val TOKEN_APIS = "token_apis"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fabGrabar = view.findViewById<FloatingActionButton>(R.id.fab_grabar)
        txtTokenApiPeruDev = view.findViewById(R.id.et_token_apiperu_dev)
        txtTokenApisPeru = view.findViewById(R.id.et_token_apisperu)

        fabGrabar.setOnClickListener {
            if (!Utilidad.existeConexionInternet(it.context)) {
                Toast.makeText(it.context, "NO existe conexion a Internet", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            editor = activity?.getSharedPreferences(TOKEN_APIS, Context.MODE_PRIVATE)?.edit()!!
            editor.putString("token_dev", txtTokenApiPeruDev.text.toString())
            editor.putString("token_apisperu", txtTokenApisPeru.text.toString())
            editor.apply()
            editor.commit()

            Toast.makeText(it.context, "Operacion exitosa", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val prefs = activity?.getSharedPreferences(TOKEN_APIS, Context.MODE_PRIVATE)
        txtTokenApiPeruDev.setText(prefs?.getString("token_dev", ""))
        txtTokenApisPeru.setText(prefs?.getString("token_apisperu", ""))
    }
}