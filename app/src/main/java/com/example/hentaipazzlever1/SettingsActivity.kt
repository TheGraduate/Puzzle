package com.example.hentaipazzlever1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.hentaipazzlever1.databinding.ActivitySettingsBinding
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val btnChangeLanguage = findViewById<Button>(R.id.btn_change_language)
        btnChangeLanguage.setOnClickListener {
            toggleLanguage()
        }

        val savedLanguage = sharedPreferences.getString("language", "en")
        if (savedLanguage != null) {
            setAppLocale(savedLanguage)
        }

        val isNightMode = getSharedPreferences("ThemePrefs", MODE_PRIVATE).getBoolean("isNightMode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        binding.btnSwitchTheme.setOnClickListener {
            val newMode = if (isNightMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.setDefaultNightMode(newMode)

            // Сохранить новое значение в SharedPreferences
            val editor = getSharedPreferences("ThemePrefs", MODE_PRIVATE).edit()
            editor.putBoolean("isNightMode", !isNightMode)
            editor.apply()
        }

        binding.btnBackToMenuFromSettings.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

    }

    private fun toggleLanguage() {
        val currentLanguage = sharedPreferences.getString("language", "en")
        val newLanguage = if (currentLanguage == "en") "ru" else "en"

        // Сохраняем новый язык в SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("language", newLanguage)
        editor.apply()
        // Применяем новый язык
        setAppLocale(newLanguage)
        // Пересоздаем активити для применения изменений языка
        recreate()
    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}