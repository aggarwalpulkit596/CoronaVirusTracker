package com.puldroid.coronavirustracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puldroid.coronavirustracker.R
import com.puldroid.coronavirustracker.modals.Advice
import kotlinx.android.synthetic.main.item_advice.view.*
import java.util.*

class AdviceAdapter : RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder>() {

    private var data: List<Advice> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
        return AdviceViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_advice, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<Advice>) {
        this.data = data
        notifyDataSetChanged()
    }

    class AdviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Advice) = with(itemView) {
            title.text = item.title
            subTitle.text = item.subtitle
        }
    }
}