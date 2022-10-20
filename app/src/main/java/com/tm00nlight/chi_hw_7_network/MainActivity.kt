package com.tm00nlight.chi_hw_7_network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.switcher -> {
                if (supportFragmentManager.findFragmentByTag("ANIMAL") == null) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.container.id, MarvelFragment())
                        .commit()
                }
//                if (supportFragmentManager.findFragmentByTag("MARVEL") == null) {
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(binding.container.id, AnimalFragment())
//                        .commit()
//                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}