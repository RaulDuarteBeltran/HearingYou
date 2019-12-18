package com.redb.hearingyou.Vistas

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import com.redb.hearingyou.Modelos.Firebase.PacienteFB
import com.redb.hearingyou.Modelos.Firebase.PsicologoFB
import com.redb.hearingyou.Modelos.Firebase.UsuarioFB
import com.redb.hearingyou.R
import java.util.*

class RegistryActivity : AppCompatActivity() {

    private lateinit var TAG: String
    private lateinit var btnDate: Button
    private lateinit var dateListener: DatePickerDialog.OnDateSetListener
    private lateinit var btnRegistry : Button
    private lateinit var etNickName : EditText
    private lateinit var etUserName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPass : EditText
    private lateinit var etVPass : EditText

    private val database:FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)


        TAG = "RegistryActivity"
        btnDate = findViewById(R.id.btn_date)
        btnRegistry = findViewById(R.id.btn_registry)
        etNickName= findViewById(R.id.editText_nickName)
        etUserName= findViewById(R.id.editText_userName)
        etEmail= findViewById(R.id.editText_email)
        etPass = findViewById(R.id.editText_userPass)
        etVPass= findViewById(R.id.editText_validPass)


        btnDate.setOnClickListener(View.OnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                this@RegistryActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateListener,
                year, month, day
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        })


        dateListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                Log.d(TAG, "onDateSet: mm/dd/yyy: $month/$day/$year")

                val date = "$month/$day/$year"
                btnDate.setText(date)  // el date es lo que vas a guardar en la BD como la fecha ejemplo del string que imprime 11/5/2019
            }

        btnRegistry.setOnClickListener {
            val userKey = database.getReference("App").child("usuarios").push().key
            val menu =
                arrayOf("Soy Paciente", "Soy Psicólogo") // Aqui es son las opciones que contendra el menu, qui las puedes agregar i quitar
            val builder = AlertDialog.Builder(this@RegistryActivity)
            builder.setTitle("Registro")
            builder.setItems(menu,{_, which ->

                when (menu[which]){       //Este es el switch donde al ser clicleado alguna opcion nos llevara a la sección adecuada
                    "Soy Psicólogo" -> {
                        //Aqui se registra al psicologo
                        val psicologoToPush:PsicologoFB=PsicologoFB(
                           correo =  etEmail.text.toString(),
                            fechaNaciemiento = btnDate.text.toString(),
                            nombre = etUserName.text.toString()
                        )
                        val userToPush:UsuarioFB= UsuarioFB(
                            correo = etEmail.text.toString(),
                            contraseña = etPass.text.toString(),
                            tipo = 1
                        )
                        database.getReference("App").child("usuarios").child(userKey!!).setValue(userToPush)
                        database.getReference("App").child("psicologos").child(userKey!!).setValue(psicologoToPush)

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    "Soy Paciente" -> {
                        //Aqui se registra al paciente
                        val patientToPush:PacienteFB= PacienteFB(
                            sobrenombre = etNickName.text.toString(),
                            nombre = etUserName.text.toString(),
                            correo = etEmail.text.toString(),
                            fechaNacimiento = btnDate.text.toString()
                        )
                        val userToPush:UsuarioFB= UsuarioFB(
                            correo = etEmail.text.toString(),
                            contraseña = etPass.text.toString()
                        )
                        database.getReference("App").child("usuarios").child(userKey!!).setValue(userToPush)
                        database.getReference("App").child("pacientes").child(userKey!!).setValue(patientToPush)

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
            builder.show()

        }

    }
}
