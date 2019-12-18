package com.redb.hearingyou.Vistas.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redb.hearingyou.Modelos.Firebase.ConversacionFB
import com.redb.hearingyou.Modelos.Firebase.PsicologoFavFB
import com.redb.hearingyou.R
import com.redb.hearingyou.Vistas.ConversacionActivity
import com.redb.hearingyou.Vistas.EXTRA_IDCONVERSACION
import com.redb.hearingyou.Vistas.EXTRA_IDUSUARIO

class ConversacionesAdapter(private val conversaciones: ArrayList<ConversacionFB>,val idUsuario:String) :
    RecyclerView.Adapter<ConversacionesAdapter.ConversacionesViewHolder>() {

    class ConversacionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvUserName: TextView
        private var LyConversacion:LinearLayout

        private lateinit var conversacion: ConversacionFB

        init {
            tvUserName = view.findViewById(R.id.ConversacionesHolder_textView_userName)
            LyConversacion= view.findViewById(R.id.ConversacionesHolder_LinearLayout_conversacion)

            LyConversacion.setOnClickListener{
                val intent = Intent(view.context, ConversacionActivity::class.java)
                intent.putExtra(EXTRA_IDCONVERSACION, conversacion.id)
                intent.putExtra(EXTRA_IDUSUARIO,conversacion.idPaciente)
                view.context.startActivity(intent)
            }
        }

        fun bind(conversacion: ConversacionFB, idPropio: String) {
            this.conversacion = conversacion
            if (conversacion.idPaciente == idPropio) {
                tvUserName.setText(conversacion.psicologo)
            } else {
                tvUserName.setText(conversacion.paciente)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversacionesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_conversaciones_holder,
            parent,
            false
        ) as View

        return ConversacionesViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ConversacionesAdapter.ConversacionesViewHolder,
        position: Int
    ) {
        holder.bind(conversaciones[position], idUsuario)
    }

    override fun getItemCount(): Int = conversaciones.size
}