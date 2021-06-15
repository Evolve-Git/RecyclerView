package com.evolve.recyclerview.data

import com.evolve.recyclerview.data.models.AppDetailModel
import com.evolve.recyclerview.data.models.FeaturedAppsModel
import com.evolve.recyclerview.data.models.AllAppsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("https://api.steampowered.com/ISteamApps/GetAppList/v2/")
    suspend fun retrieveAppList(): AllAppsModel

    @GET("https://store.steampowered.com/api/featured/?l=english")
    suspend fun retrieveFeaturedAppList(): FeaturedAppsModel

    @GET("https://store.steampowered.com/api/appdetails")
    suspend fun retrieveAppDetails(@Query("appids") id: Int): Map<Int, AppDetailModel>
}