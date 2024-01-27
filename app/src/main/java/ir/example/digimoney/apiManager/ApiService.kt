package ir.example.digimoney.apiManager

import ir.example.digimoney.apiManager.model.CoinsInfo
import ir.example.digimoney.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(API_KEY)
    @GET("v2/news/")
    fun getTopNews(

        @Query("sortOrder")sortOrder:String="popular"
    ):Call<NewsData>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoins(

        @Query("top/totalvolfull") to_symbol: String = "USD",
        @Query("limit") limit_data :Int=10

    ):Call<CoinsInfo>




}

