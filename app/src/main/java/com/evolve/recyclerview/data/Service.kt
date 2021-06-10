package com.evolve.recyclerview.data

import com.evolve.recyclerview.data.models.FeaturedAppsModel
import com.evolve.recyclerview.data.models.AppListModel
import retrofit2.http.GET

interface Service {
    @GET("https://api.steampowered.com/ISteamApps/GetAppList/v2/")
    suspend fun retrieveAppList(): AppListModel

    @GET("https://store.steampowered.com/api/featured/?l=english")
    suspend fun retrieveFeaturedAppList(): FeaturedAppsModel
}