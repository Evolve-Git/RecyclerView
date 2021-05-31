package com.evolve.recyclerview.models

data class AppModel (
        val appid: Int,
        val name: String
        )

data class AppList (
        val apps: ArrayList<AppModel>
        )

data class AppListModel (
        val applist: AppList
        )