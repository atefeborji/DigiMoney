package ir.example.digimoney.features.marketActivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.example.digimoney.apiManager.BASE_URL_IMAGE
import ir.example.digimoney.apiManager.model.CoinsInfo
import ir.example.digimoney.databinding.ItemRecyclerPage1Binding

class MarketAdapter(
    private val data: ArrayList<CoinsInfo.Data>,
    private val recyclerCallback: RecyclerCallback
) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    lateinit var binding: ItemRecyclerPage1Binding

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindViews(dataCoins: CoinsInfo.Data) {

            binding.txtCoinName.text = dataCoins.coinInfo.fullName
            binding.txtPrice.text = "$" + dataCoins.rAW.uSD.pRICE.toString()
            binding.txtMarketCap.text = dataCoins.rAW.uSD.mKTCAP.toString()

            val taghir = dataCoins.rAW.uSD.cHANGEPCT24HOUR
            if (taghir > 0) {
                binding.txtChange.setTextColor(Color.parseColor("#50b12a")
                )
                binding.txtChange.text =
                    dataCoins.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4) + "%"
            } else if (taghir < 0) {
                binding.txtChange.setTextColor(
                    Color.parseColor("#f0655e")
                )
                binding.txtChange.text =
                    dataCoins.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 5) + "%"
            } else {
                binding.txtChange.text = "0%"
            }

            val marketCap = dataCoins.rAW.uSD.mKTCAP / 1000000000
            val indexDot = marketCap.toString().indexOf('.')
            binding.txtMarketCap.text = "$" + marketCap.toString().substring(0, indexDot + 3) + " B"


            Glide
                .with(itemView)
                .load(BASE_URL_IMAGE + dataCoins.coinInfo.imageUrl)
                .into(binding.imgItem)

            itemView.setOnClickListener() {
                recyclerCallback.onCoinItemClicked(dataCoins)

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRecyclerPage1Binding.inflate(inflater, parent, false)
        return MarketViewHolder(binding.root)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bindViews(data[position])

    }

    interface RecyclerCallback {
        fun onCoinItemClicked(dataCoins: CoinsInfo.Data)
    }

}