package com.evolve.recyclerview.data.models

data class AppModel (
        val appid: Int,
        val name: String
        )

data class AppList (
        val apps: ArrayList<AppModel>
        )

data class AllAppsModel (
        val applist: AppList
        )