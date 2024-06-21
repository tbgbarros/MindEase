package com.example.mindease

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class activity_meditacao : AppCompatActivity() {

    private lateinit var botaoStartMeditacao: Button
    private lateinit var imageMeditacao: ImageView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meditacao)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        botaoStartMeditacao = findViewById(R.id.buttonMeditacao)
        imageMeditacao = findViewById(R.id.imageViewMeditacao)
        mediaPlayer = MediaPlayer.create(this, R.raw.meditacao_audio)
        mediaPlayer.isLooping = false // Define se deve repetir o áudio automaticamente

        botaoStartMeditacao.setOnClickListener {
            startMeditacao()
            registrarMeditacao()
        }

    }

    private fun startMeditacao() {
        try {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao reproduzir áudio: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun registrarMeditacao() {
        val userId = auth.currentUser?.uid ?: return
        val meditacaoRef = database.getReference("users").child(userId).child("meditacoes")

        val dataAtual = Calendar.getInstance().time
        val dataFormatada = android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss", dataAtual).toString()

        val meditacao = mapOf(
            "timestamp" to dataFormatada
            // Você pode adicionar mais informações se necessário, como duração da meditação, etc.
        )

        // Salva a meditação no Firebase Realtime Database
        meditacaoRef.push().setValue(meditacao)
            .addOnSuccessListener {
                // Sucesso ao salvar no banco de dados
                Toast.makeText(this, "Meditação registrada com sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Falha ao salvar no banco de dados
                Toast.makeText(this, "Erro${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
