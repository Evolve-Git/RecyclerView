package com.evolve.recyclerview

import com.evolve.recyclerview.models.jsonModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retriever {
        private val service: Service

        companion object {
            const val BASE_URL = "https://api.steampowered.com/"
        }

        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(Service::class.java)
        }

        suspend fun getAppList(): jsonModel {
            return service.retrieveAppList()
        }
}