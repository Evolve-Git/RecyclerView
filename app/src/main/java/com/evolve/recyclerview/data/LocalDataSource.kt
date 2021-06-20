package com.evolve.recyclerview.data

import android.content.Context
import com.evolve.recyclerview.data.models.AllAppsModel
import com.google.gson.Gson

class LocalDataSource {
    companion object{

        fun loadAllAppsData(context: Context): AllAppsModel {
            val jsonString = context.assets.open("api.steampowered.com.json").bufferedReader()
                .use { it.readText() }

            val gson = Gson()
            val result: AllAppsModel = gson.fromJson(jsonString, AllAppsModel::class.java)
            return result
        }
    }
}