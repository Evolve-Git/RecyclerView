package com.evolve.recyclerview.data.models

data class OwnedAppModel (
    val appid: Int = 0,
    val name: String = "",
    val playtime_forever: Int
)

data class OwnedAppsResponse(
    val game_count: Int,
    val games: ArrayList<OwnedAppModel>
)

data class OwnedAppsModel (
    val response: OwnedAppsResponse
)