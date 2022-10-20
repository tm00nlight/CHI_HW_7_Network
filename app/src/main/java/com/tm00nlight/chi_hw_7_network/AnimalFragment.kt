package com.tm00nlight.chi_hw_7_network

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tm00nlight.chi_hw_7_network.data.Animal
import okhttp3.*
import okio.IOException
import java.io.StringReader

/**
 * A fragment representing a list of Items.
 */
class AnimalFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_animal_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyAnimalRecyclerViewAdapter(listOf())
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var animals: MutableList<Animal>

        val clientOkHttp = OkHttpClient()
        val threadOkHttp = Thread {
            val request = Request.Builder()
                .url("https://zoo-animal-api.herokuapp.com/animals/rand/6")
                .build()

            clientOkHttp.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        for ((name, value) in response.headers) {
                            println("$name: $value")
                        }

                        val animalListType = object: TypeToken<ArrayList<Animal>>() {}.type

                        animals = Gson().fromJson(response.body!!.string(), animalListType)

                        Handler(Looper.getMainLooper()).post {
                            if (view is RecyclerView) {
                            with(view) {
                                adapter = MyAnimalRecyclerViewAdapter(animals)
                            }
                        } }
                    }
                }
            })
        }
        threadOkHttp.start()

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            AnimalFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}