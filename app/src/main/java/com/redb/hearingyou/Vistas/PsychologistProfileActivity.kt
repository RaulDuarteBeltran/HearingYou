package com.redb.hearingyou.Vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.redb.hearingyou.Modelos.Firebase.PsicologoFB
import com.redb.hearingyou.R
import java.time.temporal.ValueRange

class PsychologistProfileActivity : AppCompatActivity() {

    private lateinit var textName : TextView
    private lateinit var textMail : TextView
    private lateinit var textBirthday : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psychologist_profile)

        textName = findViewById(R.id.perfilpsicologo_textview_nombre)
        textMail = findViewById(R.id.perfilpsicologo_textview_correo)
        textBirthday = findViewById(R.id.perfilpsicologo_textview_fechadenacimiento)

        val database = FirebaseDatabase.getInstance()
        val dbRef = database.getReference("App").child("psicologos").child("-LvDVMHkPucblKE-Aksx")

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
               val psicologo:PsicologoFB? = p0.getValue(PsicologoFB::class.java)

                textName.text = psicologo!!.nombre
                textMail.text = psicologo.correo
                textBirthday.text = psicologo.fechaNaciemiento

            }
        })






    }
}
