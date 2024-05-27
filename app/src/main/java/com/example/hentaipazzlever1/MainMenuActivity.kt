package com.example.hentaipazzlever1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hentaipazzlever1.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener {
            // Переход на экран выбора пазлов
            val intent = Intent(this, PuzzleSelectionActivity::class.java)
            startActivity(intent)
        }

        binding.btnSettings.setOnClickListener {
            // Переход на экран настроек
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.btnExit.setOnClickListener {
            // Закрытие приложения
            finishAffinity()
        }
    }
}