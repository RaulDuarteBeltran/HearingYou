package com.redb.hearingyou.Vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.firebase.database.*
import com.redb.hearingyou.DB.AppDatabase
import com.redb.hearingyou.Modelos.Firebase.PacienteFB
import com.redb.hearingyou.Modelos.Firebase.PsicologoFB
import com.redb.hearingyou.Modelos.Firebase.PsicologoFavFB
import com.redb.hearingyou.R
import java.time.temporal.ValueRange

const val EXTRA_ID_PSICOLOGO_CONVERSACION="com.example.HearingYou.EXTRA_ID_PSICOLOGO_CONVERSACION"
const val EXTRA_ID_CONVERSACION="com.example.HearingYou.EXTRA_ID_CONVERSACION"

class PsychologistProfileActivity : AppCompatActivity() {

    private lateinit var textName : TextView
    private lateinit var textMail : TextView
    private lateinit var textBirthday : TextView
    private lateinit var buttonAddToFavorites:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psychologist_profile)

        textName = findViewById(R.id.perfilpsicologo_textview_nombre)
        textMail = findViewById(R.id.perfilpsicologo_textview_correo)
        textBirthday = findViewById(R.id.perfilpsicologo_textview_fechadenacimiento)
        buttonAddToFavorites = findViewById(R.id.perfilpsicologo_button_agregarafavoritos)

        var dbRef:DatabaseReference
        val database = FirebaseDatabase.getInstance()
        val idPsicologo = intent.getStringExtra(EXTRA_ID_PSICOLOGO_CONVERSACION)
        val tipoUsuario = intent.getIntExtra("EXTRA_TIPOUSUARIO",1)
        var idConversacion:String
        if (tipoUsuario == 1) {
            dbRef = database.getReference("App").child("psicologos").child(idPsicologo)
            idConversacion = intent.getStringExtra(EXTRA_ID_CONVERSACION)
        }
        else {
            dbRef = database.getReference("App").child("pacientes").child(idPsicologo)
            idConversacion = ""
        }
        val db = AppDatabase.getAppDatabase(this)
        val Aplicacion = db.getAplicacionDao().getAplicacion()

        buttonAddToFavorites.setOnClickListener{
            val psicologoFav = PsicologoFavFB(textName.text.toString(),false,idConversacion)
            database.getReference("App").child("favoritos").child(Aplicacion.idUser.toString())
                .child(idPsicologo).setValue(psicologoFav)

            database.getReference("App").child("psicologos").child(idPsicologo)
                .child("usuariosFavoritos").child(Aplicacion.idUser.toString()).setValue(true)
        }

        if (tipoUsuario == 1) {
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val psicologo: PsicologoFB? = p0.getValue(PsicologoFB::class.java)

                    textName.text = psicologo!!.nombre
                    textMail.text = psicologo.correo
                    textBirthday.text = psicologo.fechaNaciemiento

                }
            })
        }
        else
        {
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val paciente: PacienteFB? = p0.getValue(PacienteFB::class.java)

                    textName.text = paciente!!.sobrenombre
                    textMail.text = paciente.correo
                    textBirthday.text = paciente.fechaNacimiento
                    buttonAddToFavorites.isVisible=false

                }
            })
        }






    }
}
