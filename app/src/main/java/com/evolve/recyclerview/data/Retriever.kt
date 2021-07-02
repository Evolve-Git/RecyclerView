package com.evolve.recyclerview.data

import com.evolve.recyclerview.data.models.*
import com.evolve.recyclerview.utility.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class Retriever {
    private val service: Service

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(Service::class.java)
    }

    suspend fun getAppList(): AllAppsModel {
        return service.retrieveAppList()
    }

    suspend fun getFeaturedAppList(): FeaturedItems{
        val fAPPlist: FeaturedAppsModel = service.retrieveFeaturedAppList()
        val parsedList = (fAPPlist.large_capsules +
                fAPPlist.featured_win + fAPPlist.featured_linux) as ArrayList
        return FeaturedItems(parsedList)
    }

    suspend fun getFeaturedCategoriesList(currency: Int): FeaturedCategoriesModel{
        return service.retrieveFeaturedCategoriesList(currency)
    }

    suspend fun getAppDetails(id: Int): Map<Int, AppDetailModel>{
        return service.retrieveAppDetails(id)
    }

    suspend fun getUserInfo(userid: String): UserInfo{
        val info: UserInfoModel = service.retrieveUserInfo(userid)
        return info.response.players[0]
    }

    suspend fun getOwnedApps(userid: String): Map<Int, OwnedAppModel>{
        val owned: OwnedAppsModel = service.retrieveOwnedApps(userid)
        val parsedList = mutableMapOf<Int, OwnedAppModel>()
        owned.response.games.forEach { parsedList[it.appid] = it }
        parsedList[0] = OwnedAppModel(0, "Games count", owned.response.game_count)
        return parsedList
    }

    suspend fun getWishlistedApps(userid: String, page: Int): Map<Int, WishlistedAppsModel>{
        return service.retrieveWishlistedApps(userid, page)
    }
}