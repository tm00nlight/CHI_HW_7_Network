package com.tm00nlight.chi_hw_7_network.network

import com.tm00nlight.chi_hw_7_network.data.Animal
import com.tm00nlight.chi_hw_7_network.data.Marvel
import retrofit2.Call
import retrofit2.http.GET

interface MarvelApiService {
    @GET(".")
    fun getHeroes(): Call<MutableList<Marvel>>
}