package com.evolve.recyclerview

import android.content.Context
import android.util.Log
import com.evolve.recyclerview.models.jsonModel
import com.google.gson.Gson

class LocalDataSource {
    companion object{

        fun createDataSet(context: Context): jsonModel {
            val jsonString = context.assets.open("api.steampowered.com.json").bufferedReader()
                .use { it.readText() }

            val gson = Gson()
            val result: jsonModel = gson.fromJson(jsonString, jsonModel::class.java)
            Log.e("json", result.toString())
            return result
        }
    }
}