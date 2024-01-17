package com.example.crudequiposm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), emAdapter.OnItemClickListener {

    private  val db = FirebaseFirestore.getInstance()
    private  val tuColeccion = db.collection("EquiposMedicos")
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: emAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rDatos)
        recyclerView.layoutManager=LinearLayoutManager(this)
        adapter = emAdapter(this)
        recyclerView.adapter = adapter

        val btn_Consultar : Button = findViewById(R.id.btn_Consultar)
        val btn_Guardar : Button = findViewById(R.id.btn_Guardar)
        val btn_Actualizar : Button = findViewById(R.id.btn_Actualizar)
        val btn_Eliminar : Button = findViewById(R.id.btn_Eliminar)

        btn_Eliminar.setOnClickListener {
            val txt_id : TextView = findViewById(R.id.txt_ID)
            var IDD : String = txt_id.text.toString()

            tuColeccion.document(IDD)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this,"Eliminación Exitosa", Toast.LENGTH_SHORT).show()
                    consultarColeccion()
                }
                .addOnFailureListener {e ->
                    Toast.makeText(this,"Error"+ e.message, Toast.LENGTH_SHORT).show()
                }


        }

        btn_Actualizar.setOnClickListener {

            val txt_id : TextView = findViewById(R.id.txt_ID)

            val txt_tipoequipo: TextView = findViewById(R.id.txt_TipoEquipo)
            val txt_nombre: TextView = findViewById(R.id.txt_Nombre)
            val txt_marca: TextView = findViewById(R.id.txt_Marca)
            val txt_modelo: TextView = findViewById(R.id.txt_Modelo)
            val txt_serie: TextView = findViewById(R.id.txt_Serie)

            var IDD : String = txt_id.text.toString()

            var tipeq : String = txt_tipoequipo.text.toString()
            var nom : String = txt_nombre.text.toString()
            var mar : String = txt_marca.text.toString()
            var mod : String = txt_modelo.text.toString()
            var ser : String = txt_serie.text.toString()

            val docActualizado = HashMap<String, Any>()


            docActualizado["TipoEquipo"]=tipeq
            docActualizado["Nombre"]=nom
            docActualizado["Marca"]=mar
            docActualizado["Modelo"]=mod
            docActualizado["Serie"]=ser
            tuColeccion.document(IDD)
                .update(docActualizado)
                .addOnSuccessListener {
                    Toast.makeText(this,"Actualización Exitosa", Toast.LENGTH_SHORT).show()
                    consultarColeccion()
                }
                .addOnFailureListener {e ->
                    Toast.makeText(this,"Error"+ e.message, Toast.LENGTH_SHORT).show()
                }

        }

        btn_Consultar.setOnClickListener()
        {
            consultarColeccion()
        }

        btn_Guardar.setOnClickListener()
        {
            val db = FirebaseFirestore.getInstance()
            val txt_tipoequipo: TextView = findViewById(R.id.txt_TipoEquipo)
            val txt_nombre: TextView = findViewById(R.id.txt_Nombre)
            val txt_marca: TextView = findViewById(R.id.txt_Marca)
            val txt_modelo: TextView = findViewById(R.id.txt_Modelo)
            val txt_serie: TextView = findViewById(R.id.txt_Serie)

            var tipeq : String = txt_tipoequipo.text.toString()
            var nom : String = txt_nombre.text.toString()
            var mar : String = txt_marca.text.toString()
            var mod : String = txt_modelo.text.toString()
            var ser : String = txt_serie.text.toString()

            val data = hashMapOf(
                "TipoEquipo" to tipeq,
                "Nombre" to nom,
                "Marca" to mar,
                "Modelo" to mod,
                "Serie" to ser,
                )
            db.collection("EquiposMedicos")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_SHORT).show()
                    consultarColeccion()
                }
                .addOnFailureListener{e ->}

        }

    }

    private fun consultarColeccion()
    {
        tuColeccion.get()
            .addOnSuccessListener { querySnapshot ->

                val listaTuModelo = mutableListOf<EquipoMedico>()
                for (document in querySnapshot)
                {

                    val ID = document.id
                    val tipoequipo = document.getString("TipoEquipo")
                    val nombre = document.getString("Nombre")
                    val marca = document.getString("Marca")
                    val modelo= document.getString("Modelo")
                    val serie = document.getString("Serie")

                    val img = document.getString("url").toString()

                    if(nombre != null && tipoequipo != null && marca != null && modelo != null && serie != null)
                    {
                        val tuModelo = EquipoMedico(ID,tipoequipo,nombre,marca, modelo, serie,img)
                        listaTuModelo.add(tuModelo)
                    }

                }

                adapter.setDatos(listaTuModelo)

            }
    }

    override fun onItemClick(tuModelo: EquipoMedico) {

        val txt_id : TextView = findViewById(R.id.txt_ID)

        val txt_tipoequipo: TextView = findViewById(R.id.txt_TipoEquipo)
        val txt_nombre: TextView = findViewById(R.id.txt_Nombre)
        val txt_marca: TextView = findViewById(R.id.txt_Marca)
        val txt_modelo: TextView = findViewById(R.id.txt_Modelo)
        val txt_serie: TextView = findViewById(R.id.txt_Serie)

        txt_id.text = tuModelo.id
        txt_tipoequipo.text = tuModelo.tipoequipo
        txt_nombre.text = tuModelo.nombre
        txt_marca.text = tuModelo.marca
        txt_modelo.text = tuModelo.modelo
        txt_serie.text = tuModelo.serie



        var IDD : String = txt_id.text.toString()

        var tipeq : String = txt_tipoequipo.text.toString()
        var nom : String = txt_nombre.text.toString()
        var mar : String = txt_marca.text.toString()
        var mod : String = txt_modelo.text.toString()
        var ser : String = txt_serie.text.toString()




    }


}