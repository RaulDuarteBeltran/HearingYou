package com.redb.hearingyou.Vistas

import android.os.Bundle
import android.renderscript.Sampler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.redb.hearingyou.DB.AppDatabase
import com.redb.hearingyou.Modelos.Firebase.ConversacionFB
import com.redb.hearingyou.Modelos.Firebase.PacienteFB
import com.redb.hearingyou.Modelos.Firebase.UsuarioFB
import com.redb.hearingyou.R
import com.redb.hearingyou.Vistas.Adapters.ConversacionesAdapter

class ConversacionesActivity:AppCompatActivity() {

    private lateinit var rvConversaciones:RecyclerView
    private var conversaciones:ArrayList<ConversacionFB> = arrayListOf()

    private val db = AppDatabase.getAppDatabase(this)
    val Aplicacion = db.getAplicacionDao().getAplicacion()
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversaciones)

        rvConversaciones = findViewById<RecyclerView>(R.id.Conversaciones_RecyclerView_Conversaciones).apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this@ConversacionesActivity)
            adapter = ConversacionesAdapter(conversaciones,Aplicacion.idUser.toString())
        }

        val pacienteReference = database.getReference("App").child("pacientes")
            .child(Aplicacion.idUser.toString())
        var listaConversaciones = listOf<String>()
        pacienteReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                val paciente :PacienteFB? = p0.getValue(PacienteFB::class.java)
                listaConversaciones = paciente!!.conversaciones.keys.toList()

                for(idconversacion in listaConversaciones)
                {
                    val conversacionReference = database.getReference("App").child("conversaciones")
                        .child(idconversacion)
                    conversacionReference.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            val currentConversation:ConversacionFB? = p0.getValue(ConversacionFB::class.java)
                            currentConversation!!.id = p0.key

                            conversaciones.add(currentConversation)
                            rvConversaciones.adapter?.notifyDataSetChanged()
                        }
                    })
                }
            }
        })


    }
}