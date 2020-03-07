package com.puldroid.coronavirustracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.puldroid.coronavirustracker.AdviceActivity
import com.puldroid.coronavirustracker.R
import com.puldroid.coronavirustracker.adapter.AdviceAdapter
import com.puldroid.coronavirustracker.modals.Advice
import com.puldroid.coronavirustracker.utils.loadJsonObjectFromAsset
import kotlinx.android.synthetic.main.fragment_advice.*
import org.json.JSONObject

class AdviceFragment : Fragment() {

    val json by lazy {
        requireContext().loadJsonObjectFromAsset("who_corona_advice.json") as JSONObject?
    }
    val adviceAdapter = AdviceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adviceRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adviceAdapter
        }
        adviceAdapter.swapData(getAdvices())

        mytnBtn.setOnClickListener {
            val i = Intent(requireContext(),AdviceActivity::class.java)
            i.putExtra("type","MYTH")
            startActivity(i)
        }
        maskBtn.setOnClickListener {
            val i = Intent(requireContext(),AdviceActivity::class.java)
            i.putExtra("type","MASK")
            startActivity(i)
        }


    }

    fun getAdvices(): List<Advice> {
        val list = arrayListOf<Advice>()
        val advices = json?.getJSONArray("basics")
        for (i in 0 until advices?.length()!!) {
            val ref = advices[i] as JSONObject
            list.add(Advice("${ref.get("title")}", "${ref.get("subtitle")}"))
        }
        return list
    }

}
