package com.tm00nlight.chi_hw_7_network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tm00nlight.chi_hw_7_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.container.id, AnimalFragment())
            .commit()
    }
}