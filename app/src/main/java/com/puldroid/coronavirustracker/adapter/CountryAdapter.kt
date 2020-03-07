package com.puldroid.coronavirustracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puldroid.coronavirustracker.R
import com.puldroid.coronavirustracker.modals.FeaturesItem
import kotlinx.android.synthetic.main.item_country.view.*
import java.util.*

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var data: List<FeaturesItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_country,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun swapData(data: List<FeaturesItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FeaturesItem) = with(itemView) {
            confirmedTv.text = item.attributes?.confirmed.toString()
            deathTv.text = item.attributes?.deaths.toString()
            recoveredTv.text = item.attributes?.recovered.toString()
            countryTv.text = item.attributes?.countryRegion.toString()
        }
    }
}