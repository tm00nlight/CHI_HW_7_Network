package com.tm00nlight.chi_hw_7_network

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tm00nlight.chi_hw_7_network.data.Animal
import com.tm00nlight.chi_hw_7_network.network.AnimalApiService
import okhttp3.*
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnimalFragment : Fragment() {

    var animals: MutableList<Animal> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_animal_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyAnimalRecyclerViewAdapter(listOf())
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Thread {
            loadWithOkHttp()
            loadWithRetrofit("2")
            Log.d("AnimalFragment", "$animals")
            updateUI(view, animals)
        }.start()
    }

    private fun loadWithOkHttp() {
        val clientOkHttp = OkHttpClient()
        val request = Request.Builder()
            .url("https://zoo-animal-api.herokuapp.com/animals/rand/6")
            .build()

        clientOkHttp.newCall(request).execute().use { response ->
            val animalListType = object : TypeToken<ArrayList<Animal>>() {}.type
            animals = Gson().fromJson(response.body!!.string(), animalListType)
            Log.d("OkHttp", "$animals")
        }
    }

    private fun loadWithRetrofit(count: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://zoo-animal-api.herokuapp.com/animals/rand/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val animalApi = retrofit.create(AnimalApiService::class.java)

        val result = animalApi.getAnimals(count).execute()
        if (result.isSuccessful) {
            animals += result.body()!!
        }
        Log.d("Retrofit", "$animals")
    }

    private fun updateUI(view: View, animals: MutableList<Animal>) {
        Handler(Looper.getMainLooper()).post {
            if (view is RecyclerView) {
                with(view) {
                    adapter = MyAnimalRecyclerViewAdapter(animals)
                }
            }
        }
    }

    companion object {
        fun newInstance() = AnimalFragment()
    }
}