package com.tm00nlight.chi_hw_7_network.network

import com.tm00nlight.chi_hw_7_network.data.Animal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimalApiService {
    @GET("{count}")
    fun getAnimals(@Path("count") count: String): Call<MutableList<Animal>>
}