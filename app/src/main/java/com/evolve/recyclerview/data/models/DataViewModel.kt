package com.evolve.recyclerview.data.models

import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    var tab = 0
    var app_id = 0
    lateinit var allApps: AllAppsModel
    lateinit var featuredApps: FeaturedItems
    lateinit var favApps: ArrayList<AppModel>
    var appDetailCache = mutableMapOf<Int, AppDetailModel>()
}