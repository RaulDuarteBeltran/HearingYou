package com.redb.hearingyou.Vistas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.redb.hearingyou.Modelos.Firebase.PeticionFB
import com.redb.hearingyou.Vistas.Adapters.PeticionesAdapter
import com.redb.hearingyou.R

class PsychologistMainPageActivity:AppCompatActivity() {

    private lateinit var rvPeticiones: RecyclerView

    private var peticiones: ArrayList<PeticionFB> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psycologist_main_page)

        rvPeticiones = findViewById<RecyclerView>(R.id.recyclerView_Peticiones_Consulta).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PsychologistMainPageActivity)
            adapter = PeticionesAdapter(peticiones)
        }

        val database = FirebaseDatabase.getInstance()
        val peticionesRef = database.getReference("App").child("peticionesConsulta")
        peticionesRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val peticion:PeticionFB? = p0.getValue(PeticionFB::class.java)
                peticion?.id=p0.key

                if(peticiones.indexOf(peticion)!=-1) {
                    val currentPetition = peticiones.get(peticiones.indexOf(peticion))
                    currentPetition.aceptada = peticion!!.aceptada

                    if (currentPetition.aceptada == true) {
                        peticiones.remove(peticion)
                    }

                    rvPeticiones.adapter?.notifyDataSetChanged()
                }
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val peticion:PeticionFB? = p0.getValue(PeticionFB::class.java)
                peticion?.id=p0.key
                peticiones.add(peticion!!)
                rvPeticiones.adapter?.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val peticion:PeticionFB? = p0.getValue(PeticionFB::class.java)
                peticion?.id=p0.key
                peticiones.remove(peticion!!)
                rvPeticiones.adapter?.notifyDataSetChanged()
            }
        })
    }
}