package com.evolve.recyclerview

import com.evolve.recyclerview.models.jsonModel
import retrofit2.http.GET

interface Service {
    @GET("ISteamApps/GetAppList/v0002/?key="+ SteamAPIkey+ "&format=json")
    suspend fun retrieveAppList(): jsonModel
}