package com.evolve.recyclerview.data.models

import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    var network = false
    var userId = ""
    var tab = 0
    var appId = 0
    lateinit var allApps: AllAppsModel
    lateinit var featuredApps: FeaturedItems
    lateinit var favApps: ArrayList<AppModel>
    var appDetailCache = mutableMapOf<Int, AppDetailModel>()
}