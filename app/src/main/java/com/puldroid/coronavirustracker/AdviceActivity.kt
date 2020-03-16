package com.puldroid.coronavirustracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.gson.JsonObject
import com.puldroid.coronavirustracker.adapter.AdviceAdapter
import com.puldroid.coronavirustracker.modals.Advice
import com.puldroid.coronavirustracker.utils.loadJsonObjectFromAsset
import kotlinx.android.synthetic.main.activity_advice.*
import org.json.JSONObject

class AdviceActivity : AppCompatActivity() {
    private val mAppUnitId: String by lazy {
        "ca-app-pub-9111733988327203~1358846107"
    }
    val json by lazy { loadJsonObjectFromAsset("who_corona_advice.json") as JSONObject? }
    val adviceAdapter = AdviceAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice)
        adviceRv.apply {
            layoutManager = LinearLayoutManager(this@AdviceActivity)
            adapter = adviceAdapter
        }
        val type = intent.getStringExtra("type")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = if(type == "MYTH"){
            adviceAdapter.swapData(getAdvices(0))
            "Myth Busters"
        }else{
            adviceAdapter.swapData(getAdvices(1))
            "When and how to use masks"
        }
        initializeBannerAd(mAppUnitId)

        loadBannerAd()
    }

    private fun initializeBannerAd(appUnitId: String) {

        MobileAds.initialize(this, appUnitId)

    }

    private fun loadBannerAd() {

        val adRequest = AdRequest.Builder()
            .build()
        adView.loadAd(adRequest)
    }


    fun getAdvices(value: Int): List<Advice> {
        val list = arrayListOf<Advice>()
        val advices = (json?.getJSONArray("topics")?.get(value) as JSONObject).getJSONArray("questions")
        for (i in 0 until advices?.length()!!) {
            val ref = advices[i] as JSONObject
            list.add(Advice("${ref.get("title")}", "${ref.get("subtitle")}"))
        }
        return list
    }
}
