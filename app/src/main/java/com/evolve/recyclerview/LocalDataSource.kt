package com.evolve.recyclerview

import android.content.Context
import android.util.Log
import com.evolve.recyclerview.models.AppListModel
import com.google.gson.Gson

class LocalDataSource {
    companion object{

        fun createDataSet(context: Context): AppListModel {
            val jsonString = context.assets.open("api.steampowered.com.json").bufferedReader()
                .use { it.readText() }

            val gson = Gson()
            val result: AppListModel = gson.fromJson(jsonString, AppListModel::class.java)
            Log.e("json", result.toString())
            return result
        }
    }
}