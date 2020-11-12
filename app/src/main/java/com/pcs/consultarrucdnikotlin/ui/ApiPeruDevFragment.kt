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
import com.pcs.consultarrucdnikotlin.AccesoApi.GetHttpApiPeruDev
import com.pcs.consultarrucdnikotlin.Entidades.ApiPeruDev.ApiPeruDevData_Dni
import com.pcs.consultarrucdnikotlin.Entidades.ApiPeruDev.ApiPeruDevData_Ruc
import com.pcs.consultarrucdnikotlin.R
import com.pcs.consultarrucdnikotlin.Utilidades.Utilidad

class ApiPeruDevFragment : Fragment(), GetHttpApiPeruDev.ApiPeruDevListener {

    private lateinit var txtNroDoc: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtEstado: EditText
    private lateinit var txtCondicion: EditText
    private val apiPeruDev: GetHttpApiPeruDev.ApiPeruDevListener = this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_api_peru_dev, container, false)
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
                if(!prefs?.getString("token_dev", "")?.trim().equals(""))
                    GetHttpApiPeruDev(it.context, progressBar, apiPeruDev).execute(nroDoc.toString(), prefs?.getString("token_dev", "")?.trim())
                else
                    Toast.makeText(it.context, "NO tiene un TOKEN configurado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(it.context, "NRO documento no valido", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun apiPeruDevDni(entidad: ApiPeruDevData_Dni) {
        txtNroDoc.setText(entidad.data?.numero)
        txtNombre.setText(entidad.data?.nombre_completo)
        txtDireccion.setText(entidad.data?.fecha_nacimiento)
        txtEstado.setText(entidad.data?.sexo)
        txtCondicion.setText(entidad.data?.codigo_verificacion)
    }

    override fun apiPeruDevRuc(entidad: ApiPeruDevData_Ruc) {
        txtNroDoc.setText(entidad.data?.ruc)
        txtNombre.setText(entidad.data?.nombre_o_razon_social)
        txtDireccion.setText(entidad.data?.direccion)
        txtEstado.setText(entidad.data?.estado)
        txtCondicion.setText(entidad.data?.condicion)
        Utilidad.mostrarMensaje("Otros datos:",
            "Dir. completa: ${entidad.data?.direccion_completa} \n " +
                    "Nom. comercial: ${entidad.data?.nombre_comercial} ",
            context)
    }
}