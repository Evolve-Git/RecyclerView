package com.evolve.recyclerview.data

import com.evolve.recyclerview.data.models.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    suspend fun getFeaturedAppList(): FeaturedItems {
        val fAPPlist: FeaturedAppsModel = service.retrieveFeaturedAppList()
        val parsedList = (fAPPlist.large_capsules +
                fAPPlist.featured_win + fAPPlist.featured_linux) as ArrayList
        return FeaturedItems(parsedList)
    }

    suspend fun getAppDetails(id: Int): Map<Int, AppDetailModel> {
        return service.retrieveAppDetails(id)
    }

    suspend fun getUserInfo(userid: String): UserInfoModel {
        return service.retrieveUserInfo(userid)
    }

    suspend fun getOwnedApps(userid: String): OwnedAppsModel{
        return service.retrieveOwnedApps(userid)
    }

    suspend fun getWishlistedApps(userid: String, page: Int): Map<Int, WishlistedAppsModel>{
        return service.retrieveWishlistedApps(userid, page)
    }
}