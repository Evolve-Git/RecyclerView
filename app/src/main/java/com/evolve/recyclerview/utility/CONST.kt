package com.evolve.recyclerview.data

const val ALL_APPS =            0
const val FEATURED_APPS =       1
const val FAV_APPS =            2
const val BASE_URL =            "https://store.steampowered.com"
const val STEAM_API_KEY =       ""
const val ALL_APPS_URL =        "https://api.steampowered.com/ISteamApps/GetAppList/v2/"
const val FEATURED_APPS_URL =   "https://store.steampowered.com/api/featured/?l=english"
const val APP_DETAILS_URL =     "https://store.steampowered.com/api/appdetails"
const val USER_INFO_URL =       "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/"
const val OWNED_APPS_URL =      "https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/"
const val OWNED_APPS_URL_OPTS = "include_appinfo=true&include_played_free_games=true"
const val WISHLISTED_APPS_URL = "https://store.steampowered.com/wishlist/profiles/"