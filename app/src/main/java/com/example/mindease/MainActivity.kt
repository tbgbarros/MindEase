package com.example.mindease

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

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
        database = FirebaseDatabase.getInstance()

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
                // Login realizado com sucesso, direcionar para DashboardActivity
                Toast.makeText(this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                user?.let {
                    updateInfomacoesUsuario(it.uid)
                }
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
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

    private fun updateInfomacoesUsuario(userId: String) {
        val userRef = database.getReference("users").child(userId)
        val userUpdates = HashMap<String, Any>()
        auth.currentUser?.let { user ->
            userUpdates["email"] = user.email ?: ""
            userUpdates["name"] = user.displayName ?: ""
        }
        userRef.updateChildren(userUpdates)
            .addOnSuccessListener {
                Toast.makeText(this, "Informações do usuário atualizadas", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao atualizar informações do usuário: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}
//continua o codigo dfgdfg ghfghfg