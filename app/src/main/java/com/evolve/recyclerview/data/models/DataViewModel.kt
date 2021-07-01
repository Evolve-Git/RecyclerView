package com.evolve.recyclerview.data.models

import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    var network = false
    var userId = ""
    var tab = 0
    var appId = 0
    lateinit var allApps: AllAppsModel
    lateinit var featuredApps: FeaturedItems
    lateinit var featuredCategories: FeaturedCategoriesModel
    lateinit var favApps: ArrayList<AppModel>
    lateinit var userInfo: UserInfo
    lateinit var ownedApps: Map<Int, OwnedAppModel>
    var wislistedApps = mutableMapOf<Int, WishlistedAppsModel>()
    var appDetailCache = mutableMapOf<Int, AppDetailModel>()
}