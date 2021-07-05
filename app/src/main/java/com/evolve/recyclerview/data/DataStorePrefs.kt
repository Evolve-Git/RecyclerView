package com.evolve.recyclerview.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.evolve.recyclerview.data.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("savedData")

class DataStorePrefs (context: Context){
    private val dataStore = context.dataStore

    companion object {
        val USER_ID = stringPreferencesKey("USER_ID")
        val ALL_APPS = stringPreferencesKey("ALL_APPS")
        val FEATURED_APPS = stringPreferencesKey("FEATURED_APPS")
        val FEATURED_CATEGORIES = stringPreferencesKey("FEATURED_CATEGORIES")
        val FAV_APPS = stringPreferencesKey("FAV_APPS")
    }

    suspend fun saveData(viewModel: DataViewModel){
        dataStore.edit {
            it[USER_ID] = viewModel.userId
            it[ALL_APPS] = Gson().toJson(viewModel.allApps)
            it[FEATURED_APPS] = Gson().toJson(viewModel.featuredApps)
            it[FEATURED_CATEGORIES] = Gson().toJson(viewModel.featuredCategories)
            it[FAV_APPS] = Gson().toJson(viewModel.favApps)
        }
    }

    suspend fun saveLogin(userId: String){
        dataStore.edit { it[USER_ID] = userId }
    }

    suspend fun restoreLogin(): String{
        return dataStore.data.map { it[USER_ID]?: "" }.first()
    }

    suspend fun restoreData(viewModel: DataViewModel){
        dataStore.data.map {
            viewModel.allApps = Gson().fromJson( it[ALL_APPS],
                AllAppsModel::class.java) ?: AllAppsModel(AppList(arrayListOf()))
            viewModel.featuredApps = Gson().fromJson(it[FEATURED_APPS],
                FeaturedItems::class.java) ?: FeaturedItems(arrayListOf())
            viewModel.featuredCategories = Gson().fromJson(it[FEATURED_CATEGORIES],
                FeaturedCategoriesModel::class.java) ?: FeaturedCategoriesModel(
                FeaturedItems(arrayListOf()), FeaturedItems(arrayListOf()),
                FeaturedItems(arrayListOf()), FeaturedItems(arrayListOf()))
        }.first()
    }

    suspend fun restoreFavApps(): ArrayList<AppModel> {
        val favType = object : TypeToken<ArrayList<AppModel>>() {}.type
        return Gson().fromJson( dataStore.data.map {
            it[FAV_APPS] }.first(), favType) ?: arrayListOf()
    }
}