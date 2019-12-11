package com.redb.hearingyou.Vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

                val peticionReference = database.getReference("App").child("peticionesConsulta").child(petitionKey)
                peticionReference.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()) {
                            var peticion: PeticionFB? = p0.getValue(PeticionFB::class.java)
                            peticion?.id = p0.key

                            if (peticion!!.aceptada == true) {
                                val intent = Intent(
                                    this@PatientMainPageActivity,
                                    ConversacionActivity::class.java
                                )
                                intent.putExtra(EXTRA_IDCONVERSACION, peticion.idConversacion)
                                intent.putExtra(EXTRA_IDUSUARIO, "-LvBQUgQ7zFpNeaP7-1R")
                                database.getReference("App").child("peticionesConsulta")
                                    .child(petitionKey).setValue(null)
                                startActivity(intent)
                            }
                        }
                    }
                })


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
