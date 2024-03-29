package ir.example.digimoney.apiManager

import ir.example.digimoney.apiManager.model.CoinsInfo
import ir.example.digimoney.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"
const val API_KEY =
    "authorization: Apikey 79ba5f23a6835ebc5dda3be107e72e1366669b4e72bb20ec7be108b914614969"
const val APP_NAME = "FirstApiApp"

class ApiManager {


    private val apiService: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

    }


    fun getNews(apiCallback: ApiCallback<ArrayList<Pair<String, String>>>) {

        apiService.getTopNews().enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {

                val data = response.body()!!
                val title = data.data[0].title
                val dataToSend: ArrayList<Pair<String, String>> = arrayListOf()

                data.data.forEach {

                    dataToSend.add(Pair(it.title, it.url))
                }

                apiCallback.onSuccess(dataToSend)
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }

        })
    }

    fun getCoinList(apiCallback: ApiCallback<List<CoinsInfo.Data>>){

        apiService.getTopCoins().enqueue(object :Callback<CoinsInfo>{
            override fun onResponse(call: Call<CoinsInfo>, response: Response<CoinsInfo>) {
                val data = response.body()!!
                apiCallback.onSuccess(data.data)


            }

            override fun onFailure(call: Call<CoinsInfo>, t: Throwable) {
                apiCallback.onError(t.message!!)

            }


        })



    }

    interface ApiCallback<T> {
        fun onSuccess(data: T)
        fun onError(errorMessage: String)
    }

}