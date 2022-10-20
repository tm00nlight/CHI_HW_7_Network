package com.tm00nlight.chi_hw_7_network

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tm00nlight.chi_hw_7_network.data.Animal
import com.tm00nlight.chi_hw_7_network.data.Marvel
import com.tm00nlight.chi_hw_7_network.network.AnimalApiService
import com.tm00nlight.chi_hw_7_network.network.MarvelApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelFragment : Fragment() {
    val TAG = "MARVEL"
    private var columnCount = 1

    var heroes: MutableList<Marvel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_marvel_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyMarvelRecyclerViewAdapter(listOf())
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Thread {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.simplifiedcoding.net/demos/marvel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val marvelApi = retrofit.create(MarvelApiService::class.java)

            val result = marvelApi.getHeroes().execute()
            if (result.isSuccessful) {
                heroes = result.body()!!
            }
            Log.d("Retrofit", "$heroes")

            updateUI(view, heroes)
        }.start()
    }

    private fun updateUI(view: View, heroes: MutableList<Marvel>) {
        Handler(Looper.getMainLooper()).post {
            if (view is RecyclerView) {
                with(view) {
                    adapter = MyMarvelRecyclerViewAdapter(heroes)
                }
            }
        }
    }

    companion object {
        fun newInstance(columnCount: Int) = MarvelFragment()
    }
}