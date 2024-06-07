package com.example.mindease

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        val logBotao = findViewById<Button>(R.id.loginBotao)
        logBotao.setOnClickListener {
            val email = findViewById<EditText>(R.id.txtEmail).text.toString()
            val senha = findViewById<EditText>(R.id.txtSenha).text.toString()
            loginuser(email, senha)
        }

        val cadBotao = findViewById<Button>(R.id.cadastrarBotao)
        cadBotao.setOnClickListener {
            val email = findViewById<EditText>(R.id.txtEmail).text.toString()
            val senha = findViewById<EditText>(R.id.txtSenha).text.toString()
            cadastrarUser(email, senha)
        }
    }

    private fun loginuser(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao fazer login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cadastrarUser(email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao fazer cadastro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
