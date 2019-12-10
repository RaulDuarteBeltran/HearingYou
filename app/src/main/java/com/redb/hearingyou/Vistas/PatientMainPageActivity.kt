package com.redb.hearingyou.Vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase
import com.redb.hearingyou.Modelos.Firebase.PeticionFB
import com.redb.hearingyou.R

class PatientMainPageActivity : AppCompatActivity() {
    private lateinit var consultaButton:Button
    private var flagConsulta = false
    private var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    private var petitionKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_main_page)

        consultaButton = findViewById(R.id.btn_consulta)

        consultaButton.setOnClickListener{
            if(!flagConsulta){
                petitionKey = database.getReference("App").child("peticionesConsulta").push().key.toString()
                val peticion:PeticionFB = PeticionFB("-LvBQUgQ7zFpNeaP7-1R","red")
                database.getReference("App").child("peticionesConsulta").child(petitionKey.toString()).setValue(peticion)
                flagConsulta = true
                consultaButton.setText("CANCELAR")

                //AQUI VA LA PARTE DE COMENZAR A OIR PARA ABRIR LA VENTANA DE LA CONVERSACION




            }
            else
            {
                database.getReference("App").child("peticionesConsulta")
                    .child(petitionKey).setValue(null)
                flagConsulta =false
                consultaButton.setText("CONSULTAR")
            }
        }
    }
}
