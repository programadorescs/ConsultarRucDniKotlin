package com.pcs.consultarrucdnikotlin.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pcs.consultarrucdnikotlin.AccesoApi.GetHttpApisPeru
import com.pcs.consultarrucdnikotlin.Entidades.ApisPeru.ApisPeru_Dni
import com.pcs.consultarrucdnikotlin.Entidades.ApisPeru.ApisPeru_Ruc
import com.pcs.consultarrucdnikotlin.R
import com.pcs.consultarrucdnikotlin.Utilidades.Utilidad

class ApisPeruFragment : Fragment(), GetHttpApisPeru.ApisPeruListener {

    private lateinit var txtNroDoc: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtEstado: EditText
    private lateinit var txtCondicion: EditText
    private val apisPeruListener: GetHttpApisPeru.ApisPeruListener = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apis_peru, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<ProgressBar>(R.id.pb_progreso)
        val fabBuscar = view.findViewById<FloatingActionButton>(R.id.fab_buscar)
        txtNroDoc = view.findViewById(R.id.et_nrodoc)
        txtNombre = view.findViewById(R.id.et_nombre)
        txtDireccion = view.findViewById(R.id.et_direccion)
        txtEstado = view.findViewById(R.id.et_estado)
        txtCondicion = view.findViewById(R.id.et_condicion)

        fabBuscar.setOnClickListener {
            val nroDoc = txtNroDoc.text.trim()
            Utilidad.ocultarTeclado(it.context, it)
            Utilidad.limpiarEditText(view)

            if(nroDoc.length == 8 || nroDoc.length == 11) {
                val prefs = activity?.getSharedPreferences("token_apis", Context.MODE_PRIVATE)
                if(!prefs?.getString("token_apisperu", "")?.trim().equals(""))
                    GetHttpApisPeru(it.context, progressBar, apisPeruListener).execute(nroDoc.toString(), prefs?.getString("token_apisperu", "")?.trim())
                else
                    Toast.makeText(it.context, "NO tiene un TOKEN configurado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(it.context, "NRO documento no valido", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun obtenerRuc(entidad: ApisPeru_Ruc) {
        txtNroDoc.setText(entidad.ruc)
        txtNombre.setText(entidad.razonSocial)
        txtDireccion.setText(entidad.getDireccionCompleta())
        txtEstado.setText(entidad.estado)
        txtCondicion.setText(entidad.condicion)
        Utilidad.mostrarMensaje("Otros datos:",
            "Profesion: ${entidad.profesion} \n " +
                    "Nom. comercial: ${entidad.nombreComercial} ",
            context)
    }

    override fun obtenerDni(entidad: ApisPeru_Dni) {
        txtNroDoc.setText(entidad.dni)
        txtNombre.setText(entidad.getApellidoNombre())
        Utilidad.mostrarMensaje("Otros datos:",
                "Cod. verificacion: ${entidad.codVerifica} \n " +
                        "DNI: ${entidad.dni} ",
                context)
    }
}