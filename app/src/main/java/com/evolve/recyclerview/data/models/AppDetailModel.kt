package com.evolve.recyclerview.data.models

data class PriceOverview(
        val currency: String = "",
        val initial: Int = 0,
        val final: Int = 0,
        val discount_percent: Int = 0,
        val initial_formatted: String = "",
        val final_formatted: String = ""
        )

data class Metacritic(
        val score: Int = 0,
        val url: String = ""
        )

data class Category(
        val id: Int = 0,
        val description: String = ""
        )

data class Screenshot(
        val id: Int = 0,
        val path_thumbnail: String = "",
        val path_full: String = ""
        )

data class ReleaseDate(
        val coming_soon: Boolean = false,
        val date: String = ""
        )

data class Data(
        val name: String = "",
        val steam_appid: Int = 0,
        val is_free: Boolean = false,
        val about_the_game: String = "",
        val short_description: String = "",
        val header_image: String = "",
        val price_overview: PriceOverview?,
        val metacritic: Metacritic?,
        val categories: ArrayList<Category>?,
        val genres: ArrayList<Category>?,
        val screenshots: ArrayList<Screenshot>?,
        val release_date: ReleaseDate
        )

data class AppDetailModel(
        val success: Boolean = false,
        val data: Data
        )