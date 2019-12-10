package com.redb.hearingyou.Vistas.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redb.hearingyou.Modelos.Firebase.MensajeFB
import com.redb.hearingyou.R
import org.w3c.dom.Text

class ConversacionAdapter(private val mensajes: ArrayList<MensajeFB>,val idPropio:String ) :
    RecyclerView.Adapter<ConversacionAdapter.ConversacionViewHolder>() {

    class ConversacionViewHolder(val view: View):RecyclerView.ViewHolder(view){
        private var tvMio:TextView
        private var tvSuyo:TextView

        private lateinit var mensaje:MensajeFB

        init {
            tvMio = view.findViewById(R.id.ConversacionHolder_TextView_Mio)
            tvSuyo = view.findViewById(R.id.ConversacionHolder_TextView_Suyo)
        }

        fun bind(mensaje:MensajeFB, idPropio:String){
            this.mensaje = mensaje
            if(mensaje.emisor == idPropio)
            {
                tvMio.setText(mensaje.texto)
            }
            else
            {
                tvSuyo.setText(mensaje.texto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_mensajes_holder,
            parent,
            false
        ) as View

        return ConversacionViewHolder(view)
    }

    override fun getItemCount(): Int = mensajes.size

    override fun onBindViewHolder(holder: ConversacionViewHolder, position: Int) {
        holder.bind(mensajes[position],idPropio)
    }
}