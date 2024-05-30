package com.example.hentaipazzlever1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hentaipazzlever1.databinding.ActivityMainMenuBinding
import java.util.Locale

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("language", "en")
        if (savedLanguage != null) {
            setAppLocale(savedLanguage)
        }


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
        binding.btnGallery.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }

        binding.btnExit.setOnClickListener {
            // Закрытие приложения
            finishAffinity()
        }
    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}