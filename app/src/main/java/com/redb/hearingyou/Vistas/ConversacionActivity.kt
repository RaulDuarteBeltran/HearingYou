package com.redb.hearingyou.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.redb.hearingyou.Modelos.Firebase.ConversacionFB
import com.redb.hearingyou.Modelos.Firebase.MensajeFB
import com.redb.hearingyou.R
import com.redb.hearingyou.Vistas.Adapters.ConversacionAdapter

const val EXTRA_IDUSUARIO ="com.example.HearingYou.Vistas.EXTRA_IDUSUARIO"
const val EXTRA_IDCONVERSACION = "com.example.HearingYou.Vistas.EXTRA_IDCONVERSACION"

class ConversacionActivity :AppCompatActivity() {

    private lateinit var rvMensajes:RecyclerView
    private lateinit var etMensajeEnviar:EditText
    private lateinit var btnEnviar:Button
    private lateinit var tvNombreContacto:TextView

    private var mensajes:ArrayList<MensajeFB> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversacion)

        val idUsuario = intent.getStringExtra(EXTRA_IDUSUARIO)
        val idConversacion = intent.getStringExtra(EXTRA_IDCONVERSACION)

        etMensajeEnviar = findViewById(R.id.Conversacion_EditText_MensajeAEnviar)
        btnEnviar = findViewById(R.id.Conversacion_Button_EnviarMensaje)
        tvNombreContacto = findViewById(R.id.Conversacion_TextView_NombreContacto)
        rvMensajes = findViewById<RecyclerView>(R.id.Conversacion_recyclerView_Mensajes).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ConversacionActivity)
            adapter = ConversacionAdapter(mensajes,idUsuario)
        }

        btnEnviar.setOnClickListener{
            val mensaje = MensajeFB(idUsuario,
                etMensajeEnviar.text.toString())

            val database = FirebaseDatabase.getInstance()
            val mensajesRef = database.getReference("App").child("mensajes").child(idConversacion)
            val mensajeKey = mensajesRef.push().key
            mensajesRef.child(mensajeKey.toString()).setValue(mensaje)

            etMensajeEnviar.setText("")
        }


        var idPsicologo=""
        tvNombreContacto.setOnClickListener{
            intent = Intent(this@ConversacionActivity,PsychologistProfileActivity::class.java)
            intent.putExtra(EXTRA_ID_PSICOLOGO_CONVERSACION,idPsicologo)
            intent.putExtra(EXTRA_ID_CONVERSACION,idConversacion)
            startActivity(intent)
        }

        val database = FirebaseDatabase.getInstance()
        val MensajesRef = database.getReference("App").child("mensajes").child(idConversacion)
        MensajesRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val mensaje:MensajeFB? = p0.getValue(MensajeFB::class.java)
                mensaje?.id = p0.key.toString()
                mensajes.add(mensaje!!)
                rvMensajes.adapter?.notifyDataSetChanged()
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        val ConversacionRef = database.getReference("App").child("conversaciones").child(idConversacion)
        ConversacionRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                val conversacion:ConversacionFB? = p0.getValue(ConversacionFB::class.java)

                tvNombreContacto.text =
                    if(conversacion!!.idPaciente == idUsuario)
                    {
                        idPsicologo = conversacion.idPsicologo
                        conversacion.psicologo
                    }
                    else
                        conversacion.paciente
            }
        })
    }
}