package com.example.hentaipazzlever1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.hentaipazzlever1.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}