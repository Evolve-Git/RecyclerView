package com.evolve.recyclerview.data.models

data class FeaturedApp(
        val id: Int = 0,
        val name: String = "",
        val discounted: Boolean = false,
        val original_price: Int = 0,
        val final_price: Int = 0,
        val currency: String = "",
        val header_image: String = ""
        )

data class FeaturedItems(
        val items: ArrayList<FeaturedApp>
        )

data class FeaturedAppsModel(
        val large_capsules: ArrayList<FeaturedApp>,
        val featured_win: ArrayList<FeaturedApp>,
        val featured_linux: ArrayList<FeaturedApp>
        )

data class FeaturedCategoriesModel(
        val specials: FeaturedItems,
        val coming_soon: FeaturedItems,
        val top_sellers: FeaturedItems,
        val new_releases: FeaturedItems
        )