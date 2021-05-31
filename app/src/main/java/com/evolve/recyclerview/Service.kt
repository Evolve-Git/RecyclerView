package com.evolve.recyclerview

import com.evolve.recyclerview.models.FeaturedAppsModel
import com.evolve.recyclerview.models.AppListModel
import retrofit2.http.GET

interface Service {
    @GET("https://api.steampowered.com/ISteamApps/GetAppList/v2/")
    suspend fun retrieveAppList(): AppListModel

    @GET("https://store.steampowered.com/api/featured/?l=english")
    suspend fun retrieveFeaturedAppList(): FeaturedAppsModel
}