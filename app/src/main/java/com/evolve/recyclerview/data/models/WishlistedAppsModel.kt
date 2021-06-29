package com.evolve.recyclerview.data.models

data class WishlistedAppsModel(
        val name: String,
        val review_desc: String,
        val reviews_total: String,
        val reviews_percent: Int,
        val priority: Int,
        val added: Long,
        val rank: Int,
        val is_free_game: Boolean
        )