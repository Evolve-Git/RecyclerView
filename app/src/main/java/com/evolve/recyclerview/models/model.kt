package com.evolve.recyclerview.models

data class appModel (
        val appid: Int,
        val name: String){
}

data class appList (
        val apps: ArrayList<appModel>
        )

data class jsonModel (
        val applist: appList
        )