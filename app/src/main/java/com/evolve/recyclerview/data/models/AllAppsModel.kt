package com.evolve.recyclerview.data.models

data class AppModel (
        val appid: Int = 0,
        val name: String = "",
        var owned: Int = 0
        )

data class AppList (
        val apps: ArrayList<AppModel>
        )

data class AllAppsModel (
        val applist: AppList
        )