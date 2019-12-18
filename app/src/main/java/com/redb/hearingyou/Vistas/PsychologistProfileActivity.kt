package com.redb.hearingyou.Vistas

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.redb.hearingyou.DB.AppDatabase
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
    private lateinit var buttonAddToFavorites: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psychologist_profile)

        textName = findViewById(R.id.perfilpsicologo_textview_nombre)
        textMail = findViewById(R.id.perfilpsicologo_textview_correo)
        textBirthday = findViewById(R.id.perfilpsicologo_textview_fechadenacimiento)
        buttonAddToFavorites = findViewById(R.id.star)

        val database = FirebaseDatabase.getInstance()
        val idPsicologo = intent.getStringExtra(EXTRA_ID_PSICOLOGO_CONVERSACION)
        val idConversacion = intent.getStringExtra(EXTRA_ID_CONVERSACION)
        val dbRef = database.getReference("App").child("psicologos").child(idPsicologo)
        val db = AppDatabase.getAppDatabase(this)
        val Aplicacion = db.getAplicacionDao().getAplicacion()

        buttonAddToFavorites.setOnClickListener{
            if(buttonAddToFavorites.drawable.constantState == resources.getDrawable(R.drawable.ic_star_black_24dp).constantState)
            {
                buttonAddToFavorites.setImageResource(R.drawable.ic_star_border_black_24dp)
            }
            else
            {
                buttonAddToFavorites.setImageResource(R.drawable.ic_star_black_24dp)
            }
            
            val psicologoFav = PsicologoFavFB(textName.text.toString(),false,idConversacion)
            database.getReference("App").child("favoritos").child(Aplicacion.idUser.toString())
                .child(idPsicologo).setValue(psicologoFav)

            database.getReference("App").child("psicologos").child(idPsicologo)
                .child("usuariosFavoritos").child(Aplicacion.idUser.toString()).setValue(true)
        }

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
