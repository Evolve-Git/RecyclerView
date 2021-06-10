package com.evolve.recyclerview.data.models

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
        val items: ArrayList<FeaturedApp>
        )

data class FeaturedAppsModel (
        val large_capsules: ArrayList<FeaturedApp>,
        val featured_win: ArrayList<FeaturedApp>,
        val featured_linux: ArrayList<FeaturedApp>
        )