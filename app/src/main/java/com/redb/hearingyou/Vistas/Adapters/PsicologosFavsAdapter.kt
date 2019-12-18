package com.redb.hearingyou.Vistas.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.redb.hearingyou.Modelos.Firebase.ConversacionFB
import com.redb.hearingyou.Modelos.Firebase.PeticionFB
import com.redb.hearingyou.Modelos.Firebase.PsicologoFB
import com.redb.hearingyou.Modelos.Firebase.PsicologoFavFB
import com.redb.hearingyou.R
import com.redb.hearingyou.Vistas.ConversacionActivity
import com.redb.hearingyou.Vistas.EXTRA_IDCONVERSACION
import com.redb.hearingyou.Vistas.EXTRA_IDUSUARIO

class PsicologosFavsAdapter(private val psicologos: ArrayList<PsicologoFavFB>) :
    RecyclerView.Adapter<PsicologosFavsAdapter.PsicologosFavViewHolder>() {

    class PsicologosFavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvUserName: TextView

        private lateinit var psicologo: PsicologoFavFB

        init {
            tvUserName = view.findViewById(R.id.PsicologosFavsHolder_textView_userName)
        }

        fun bind(psicologo: PsicologoFavFB) {
            this.psicologo = psicologo
            tvUserName.setText(psicologo.nombre)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsicologosFavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_psicologosfavs_holder,
            parent,
            false
        ) as View

        return PsicologosFavViewHolder(view)
    }

    override fun onBindViewHolder(holder: PsicologosFavsAdapter.PsicologosFavViewHolder, position: Int) {
        holder.bind(psicologos[position])
    }

    override fun getItemCount(): Int = psicologos.size
}