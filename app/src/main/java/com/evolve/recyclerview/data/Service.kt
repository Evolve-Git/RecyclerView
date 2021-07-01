package com.evolve.recyclerview.data

import com.evolve.recyclerview.data.models.*
import com.evolve.recyclerview.utility.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET(ALL_APPS_URL)
    suspend fun retrieveAppList(): AllAppsModel

    @GET(FEATURED_APPS_URL)
    suspend fun retrieveFeaturedAppList(): FeaturedAppsModel

    @GET(FEATURED_CAT_URL)
    suspend fun retrieveFeaturedCategoriesList(): FeaturedCategoriesModel

    @GET(APP_DETAILS_URL)
    suspend fun retrieveAppDetails(@Query("appids") id: Int): Map<Int, AppDetailModel>

    @GET("$USER_INFO_URL?key=$STEAM_API_KEY")
    suspend fun retrieveUserInfo(@Query("steamids") userid: String): UserInfoModel

    @GET("$OWNED_APPS_URL?key=$STEAM_API_KEY&$OWNED_APPS_URL_OPTS")
    suspend fun retrieveOwnedApps(@Query("steamid") userid: String): OwnedAppsModel

    @GET("$WISHLISTED_APPS_URL/{userid}/wishlistdata/")
    suspend fun retrieveWishlistedApps(@Path("userid") userid: String,
                                       @Query("p") page: Int): Map<Int, WishlistedAppsModel>
}