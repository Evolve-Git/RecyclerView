package com.evolve.recyclerview.data.models

data class PriceOverview(
        val currency: String,
        val initial: Int,
        val final: Int,
        val discount_percent: Int,
        val initial_formatted: String,
        val final_formatted: String
        )

data class Metacritic(
        val score: Int,
        val url: String
        )

data class Category(
        val id: Int,
        val description: String
        )

data class Screenshot(
        val id: Int,
        val path_thumbnail: String,
        val path_full: String
        )

data class ReleaseDate(
        val coming_soon: Boolean,
        val date: String
        )

data class Data(
        val name: String,
        val steam_appid: Int,
        val is_free: Boolean,
        val about_the_game: String,
        val short_description: String,
        val header_image: String,
        val price_overview: PriceOverview?,
        val metacritic: Metacritic?,
        val categories: ArrayList<Category>,
        val genres: ArrayList<Category>,
        val screenshots: ArrayList<Screenshot>,
        val release_date: ReleaseDate
        )

data class AppDetailModel(
        val success: Boolean,
        val data: Data
        )