package com.example.ladm_u1_practica2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class AdaptadorComida(private val list: ArrayList<String>,  itemListener: onItemClickListenr): RecyclerView.Adapter<AdaptadorComida.ViewHolder>() {

    var mListener : onItemClickListenr = itemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val comida = list[i]
        var listacomida = comida.split(" ")

        holder.nombre.text = listacomida[0].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        holder.tipo.text = listacomida[1].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        holder.precio.text = "$ ${listacomida[2].replaceFirstChar { if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()) else it.toString() }}"
        when (listacomida[1].lowercase()){
            "Pozole"->{holder.img.setImageResource(R.drawable.icono_comida)}
            "Pizza"->{holder.img.setImageResource(R.drawable.icono_comida)}
            else->{
                holder.img.setImageResource(R.drawable.icono_comida)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(item: View, listener: onItemClickListenr):RecyclerView.ViewHolder(item){
        var tipo: TextView
        var nombre: TextView
        var precio: TextView
        var img: ImageView
        init {
            img = item.findViewById(R.id.item_imagen)
            tipo = item.findViewById(R.id.tipoplatillo)
            nombre = item.findViewById(R.id.nombreplatillo)
            precio = item.findViewById(R.id.precio)
            item.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }

    interface onItemClickListenr{
        fun onItemClick(position: Int)
    }
}