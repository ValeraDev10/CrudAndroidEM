package com.example.crudequiposm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class emAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<emAdapter.ViewHolder> (){

    private var datos: List<EquipoMedico> = ArrayList()

    interface OnItemClickListener {

        fun onItemClick(tuModelo : EquipoMedico)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtID: TextView = itemView.findViewById(R.id.txtID)
        val txtTipoEquipo: TextView = itemView.findViewById(R.id.txtTipoEquipo)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtMarca: TextView = itemView.findViewById(R.id.txtMarca)
        val txtModelo: TextView = itemView.findViewById(R.id.txtModelo)
        val txtSerie: TextView = itemView.findViewById(R.id.txtSerie)
        val image: ImageView = itemView.findViewById(R.id.imagen)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                {
                    val tuModelo = datos[position]
                    itemClickListener.onItemClick(tuModelo)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun setDatos(datos: List<EquipoMedico>)
    {
        this.datos = datos
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = datos[position]
        holder.txtID.text = item.id.toString()
        holder.txtTipoEquipo.text = item.tipoequipo
        holder.txtNombre.text = item.nombre
        holder.txtMarca.text = item.marca
        holder.txtModelo.text = item.modelo
        holder.txtSerie.text = item.serie

        Picasso.get().load(item.url).into(holder.image)


    }


}