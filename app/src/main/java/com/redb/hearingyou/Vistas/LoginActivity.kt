package com.redb.hearingyou.Vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.facebook.stetho.Stetho
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.redb.hearingyou.Modelos.Firebase.UsuarioFB
import com.redb.hearingyou.R
import com.google.firebase.database.ValueEventListener



class LoginActivity : AppCompatActivity() {
    private lateinit var textRegistry: TextView
    private lateinit var btnLogin: Button
    private lateinit var etUser: EditText
    private lateinit var etPass: EditText

    private val database:FirebaseDatabase= FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Stetho.initializeWithDefaults(this)

        textRegistry = findViewById(R.id.editText_registry)
        btnLogin = findViewById(R.id.btn_login)
        etUser = findViewById(R.id.editText_user)
        etPass = findViewById(R.id.editText_pass)


        textRegistry.setOnClickListener{
            val intent = Intent(this, RegistryActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val loginReference = database.getReference("App").child("usuarios").orderByChild("correo").equalTo(etUser.text.toString())
            loginReference.addChildEventListener(object : ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    //user = p0.getValue(UsuarioFB::class.java)
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val user:UsuarioFB? = p0.getValue(UsuarioFB::class.java)

                    if (user!!.validado == false)
                        Snackbar.make(it,"El usuario aún no ha sido aprobado",Snackbar.LENGTH_LONG).show()
                    else if (user!!.contraseña != etPass.text.toString())
                        Snackbar.make(it,"Contraseña incorrecta",Snackbar.LENGTH_LONG).show()
                    else
                    {
                        if(user!!.tipo==0) {
                            val intent =
                                Intent(this@LoginActivity, PatientMainPageActivity::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            val intent =
                                Intent(this@LoginActivity, PsychologistMainPageActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    //loginReference.removeEventListener(this)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
            loginReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (!dataSnapshot.exists()) {
                        Snackbar.make(it,"El usuario no existe",Snackbar.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            });
        }

    }
}



