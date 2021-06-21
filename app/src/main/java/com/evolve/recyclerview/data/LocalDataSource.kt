package com.evolve.recyclerview.data

import android.content.Context
import com.evolve.recyclerview.data.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class LocalDataSource {
    companion object{
        var jsonString = ""

        fun loadAllAppsData(context: Context): AllAppsModel {
            val result: AllAppsModel
            if (File("${context.filesDir}/allapps.json").exists()) {
                jsonString = File("${context.filesDir}/allapps.json").readText()
                result = Gson().fromJson(jsonString, AllAppsModel::class.java)}
            else result = AllAppsModel(AppList(arrayListOf()))
            return result
        }

        fun loadFeaturedAppsData(context: Context): FeaturedItems {
            val result: FeaturedItems
            if (File("${context.filesDir}/featuredapps.json").exists()){
                jsonString = File("${context.filesDir}/featuredapps.json").readText()
                result = Gson().fromJson(jsonString, FeaturedItems::class.java)}
            else result = FeaturedItems(arrayListOf())
            return result
        }

        fun loadFavAppsData(context: Context): ArrayList<AppModel> {
            val result : ArrayList<AppModel>
            val favType = object : TypeToken<ArrayList<AppModel>>() {}.type
            if (File("${context.filesDir}/favapps.json").exists()){
                jsonString = File("${context.filesDir}/favapps.json").readText()
                result = Gson().fromJson(jsonString, favType)}
            else result = arrayListOf()
            return result
        }
    }
}