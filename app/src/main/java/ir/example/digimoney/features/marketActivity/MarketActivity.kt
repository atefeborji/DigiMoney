package ir.example.digimoney.features.marketActivity

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.example.digimoney.R
import ir.example.digimoney.apiManager.ApiManager
import ir.example.digimoney.apiManager.model.CoinsInfo
import ir.example.digimoney.databinding.ActivityMarketBinding
import ir.example.digimoney.features.CoinActivity

class MarketActivity : AppCompatActivity() , MarketAdapter.RecyclerCallback {
    private lateinit var binding: ActivityMarketBinding
    val apiManager = ApiManager()
    lateinit var dataNews: ArrayList<Pair<String, String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMarketBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.layoutToolbar.toolbarPage1.title = "Market"

        initUi()
    }

    private fun initUi() {
        getNewsFormApi()
        getTopCoinsFromApi()
    }


    private fun getNewsFormApi() {

        apiManager.getNews(object : ApiManager.ApiCallback<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {

                dataNews = data
                refreshNews()
            }

            override fun onError(errorMessage: String) {

                Toast.makeText(this@MarketActivity, "error:" + errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun refreshNews() {

        val randomAccess = (0..49).random()
        binding.layoutNews.txtNews.text = dataNews[randomAccess].first
        binding.layoutNews.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[randomAccess].second))
            startActivity(intent)
        }
        binding.layoutNews.txtNews.setOnClickListener {

            refreshNews()
        }


    }

    private fun getTopCoinsFromApi() {

        apiManager.getCoinList(object : ApiManager.ApiCallback<List<CoinsInfo.Data>> {

            override fun onSuccess(data: List<CoinsInfo.Data>) {

                showDataInRecycler(data)
            }

            override fun onError(errorMessage: String) {
                Log.e("3169", "onError: $errorMessage", )
                Toast.makeText(this@MarketActivity, "error:" + errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun showDataInRecycler(data:List<CoinsInfo.Data>){

        val markerAdapter = MarketAdapter(ArrayList(data),this)
        binding.layoutWatchlist.recyclerPage1.adapter = markerAdapter
        binding.layoutWatchlist.recyclerPage1.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

    }

    override fun onCoinItemClicked(dataCoins: CoinsInfo.Data) {
        val intent = Intent(this,CoinActivity::class.java)
        intent.putExtra("dataToSend",dataCoins)
        startActivity(intent)

    }


}