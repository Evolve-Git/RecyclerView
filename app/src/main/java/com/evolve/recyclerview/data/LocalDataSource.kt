package com.evolve.recyclerview.data

import android.content.Context
import com.evolve.recyclerview.data.models.AppListModel
import com.google.gson.Gson

class LocalDataSource {
    companion object{

        fun createDataSet(context: Context): AppListModel {
            val jsonString = context.assets.open("api.steampowered.com.json").bufferedReader()
                .use { it.readText() }

            val gson = Gson()
            val result: AppListModel = gson.fromJson(jsonString, AppListModel::class.java)
            return result
        }
    }
}