package com.evolve.recyclerview.models

data class FeaturedApp(
        val id: Int,
        val name: String,
        val discounted: Boolean,
        val original_price: Int,
        val final_price: Int,
        val currency: String,
        val header_image: String
        )

data class FeaturedItems(
        val items: List<FeaturedApp>
        )

data class FeaturedAppsModel (
        val large_capsules: List<FeaturedApp>,
        val featured_win: List<FeaturedApp>,
        val featured_linux: List<FeaturedApp>
        )