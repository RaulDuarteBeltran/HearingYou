package com.redb.hearingyou.Vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.facebook.stetho.Stetho
import com.redb.hearingyou.DB.AppDatabase
import com.redb.hearingyou.R

class Login : AppCompatActivity() {
    private lateinit var textRegistry: TextView
    private lateinit var btnLogin: Button
    private lateinit var etUser: EditText
    private lateinit var etPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Stetho.initializeWithDefaults(this)

        textRegistry = findViewById(R.id.editText_registry)
        btnLogin = findViewById(R.id.btn_login)
        etUser = findViewById(R.id.editText_user)
        etPass = findViewById(R.id.editText_pass)


        textRegistry.setOnClickListener{
            val intent = Intent(this, Registry::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}



