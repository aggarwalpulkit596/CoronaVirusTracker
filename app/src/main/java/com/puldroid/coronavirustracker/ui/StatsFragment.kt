package com.puldroid.coronavirustracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.puldroid.coronavirustracker.R
import com.puldroid.coronavirustracker.adapter.CountryAdapter
import com.puldroid.coronavirustracker.network.Client
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class StatsFragment : Fragment() {

    val countryAdapter = CountryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updatedTv.text = "Updated: ${getCurrentDate()}"
        countryRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter

        }
        fetchResults()
    }

    private fun fetchResults() {
        GlobalScope.launch {
            val res = awaitAll(getDeathsAsync(), getConfirmedAsync(), getRecoveredAsync())
            val death = res[0].body()?.features?.first()?.attributes?.value ?: 0
            val confirmed = res[1].body()?.features?.first()?.attributes?.value ?: 0
            val recovered = res[2].body()?.features?.first()?.attributes?.value ?: 0
            val sick: Int = confirmed - death - recovered
            val recoveryRate: Double = (recovered.toDouble() / confirmed.toDouble()) * 100
            val fatalityRate: Double = (death.toDouble() / confirmed.toDouble()) * 100
            val regionRes = withContext(Dispatchers.IO) { Client.api.getCases() }
            GlobalScope.launch(Dispatchers.Main) {
                sickTv.text = sick.toString()
                recoveredRateTv.text = recoveryRate.toRoundString()
                fatalRateTv.text = fatalityRate.toRoundString()
                deathTv.text = death.toString()
                confirmedTv.text = confirmed.toString()
                recoveredTv.text = recovered.toString()
                if (regionRes.isSuccessful) {
                    regionRes.body()?.features?.let {
                        countryAdapter.swapData(it.sortedByDescending {
                            it.attributes?.confirmed
                        }.distinctBy {
                            it.attributes?.countryRegion
                        })
                    }
                }
            }


        }
    }

    private suspend fun getRecoveredAsync() = GlobalScope.async { Client.api.getRecovered() }

    private suspend fun getConfirmedAsync() = GlobalScope.async { Client.api.getConfirmed() }

    private suspend fun getDeathsAsync() = GlobalScope.async { Client.api.getDeaths() }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("d MMM, HH:mm ", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.timeZone = TimeZone.getTimeZone("IST")
        return dateFormat.format(calendar.time)
    }

}

private fun Double.toRoundString(decimals: Int = 2): String =
    "%.${decimals}f".format(this).toDouble().toString() + "%"


