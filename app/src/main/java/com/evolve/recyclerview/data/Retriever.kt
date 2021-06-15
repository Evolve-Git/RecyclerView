package com.evolve.recyclerview.data

import android.util.Log
import com.evolve.recyclerview.data.models.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retriever {
    private val service: Service

    companion object {
        const val BASE_URL = "https://store.steampowered.com"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(Service::class.java)
    }

    suspend fun getAppList(): AllAppsModel {
        return service.retrieveAppList()
    }

    suspend fun getFeaturedAppList(): FeaturedItems {
        val fAPPlist: FeaturedAppsModel = service.retrieveFeaturedAppList()
        val parsedList = (fAPPlist.large_capsules +
                fAPPlist.featured_win + fAPPlist.featured_linux) as ArrayList
        return FeaturedItems(parsedList)
    }

    suspend fun getAppDetails(id: Int): Map<Int, AppDetailModel> {
        Log.e("ser", service.retrieveAppDetails(id).toString())
        return service.retrieveAppDetails(id)
    }
}