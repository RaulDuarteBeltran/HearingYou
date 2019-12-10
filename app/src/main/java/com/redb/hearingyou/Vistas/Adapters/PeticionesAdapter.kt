package com.redb.hearingyou.Vistas.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.redb.hearingyou.Modelos.Firebase.PeticionFB
import com.redb.hearingyou.R
import kotlinx.android.synthetic.main.rv_peticiones_holder.view.*

class PeticionesAdapter(private val peticiones: ArrayList<PeticionFB>) :
    RecyclerView.Adapter<PeticionesAdapter.PeticionesViewHolder>() {

    class PeticionesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var tvUserName: TextView
        private var btnAceptar: Button

        private lateinit var peticion: PeticionFB

        init {
            tvUserName = view.findViewById(R.id.peticionesHolder_textView_userName)
            btnAceptar = view.findViewById(R.id.peticionesHolder_button_aceptar)

            btnAceptar.setOnClickListener {
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val peticionReference = database.getReference("App").child("peticionesConsulta")
                peticionReference.child(peticion.id!!).child("idPsicologo")
                    .setValue("-LvDVMHkPucblKE-Aksx")
                peticionReference.child(peticion.id!!).child("aceptada").setValue(true)

                val conversacionReference = database.getReference("App").child("conversaciones")
                val conversacionKey = conversacionReference.push().key
                conversacionReference.child(conversacionKey.toString()).child("psicologo")
                    .setValue("-LvDVMHkPucblKE-Aksx")
                conversacionReference.child(conversacionKey.toString()).child("paciente")
                    .setValue(peticion!!.idUsuario)

                peticionReference.child(peticion.id!!).child("idConversacion")
                    .setValue(conversacionKey)
            }
        }

        fun bind(peticion: PeticionFB) {
            this.peticion = peticion
            tvUserName.setText(peticion.sobreNombre)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeticionesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_peticiones_holder,
            parent,
            false
        ) as View

        return PeticionesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeticionesViewHolder, position: Int) {
        holder.bind(peticiones[position])
    }

    override fun getItemCount(): Int = peticiones.size
}