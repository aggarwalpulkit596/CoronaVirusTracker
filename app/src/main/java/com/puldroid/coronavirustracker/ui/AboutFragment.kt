package com.puldroid.coronavirustracker.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent

import com.puldroid.coronavirustracker.R
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val madeBy = SpannableString(
            "Made with ❤️.\n GitHub: github.com/aggarwalpulkit596\nLinkedIn: linkedin.com/in/aggarwalpulkit596/\n\n\n©2020 Pulkit Aggarwal. All Rights Reserved"
        )
        val privacySpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                requireContext().openChrome("https://github.com/aggarwalpulkit596")
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                ds.isUnderlineText = true
            }
        }

        val tosSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                requireContext().openChrome("https://linkedin.com/in/aggarwalpulkit596")
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                ds.isUnderlineText = true
            }
        }

        madeBy.setSpan(privacySpan, 23, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        madeBy.setSpan(tosSpan, 62, 98, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        links.apply {
            text = madeBy
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

}

fun Context.openChrome(url: String, newTask: Boolean = false, uri: Uri = Uri.EMPTY) {
    val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        CustomTabsIntent.Builder()
            .enableUrlBarHiding()
            .setToolbarColor(getColor(R.color.colorPrimaryDark))
            .setShowTitle(true)
            .setSecondaryToolbarColor(getColor(R.color.colorPrimary))
    } else {
        CustomTabsIntent.Builder()
            .enableUrlBarHiding()
            .setToolbarColor(resources.getColor(R.color.colorPrimaryDark))
            .setShowTitle(true)
            .setSecondaryToolbarColor(resources.getColor(R.color.colorPrimary))
    }
    val customTabsIntent = builder.build()
    if (newTask) {
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    if (uri.pathSegments.size > 0) {
        customTabsIntent.launchUrl(this, uri)
    } else {
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}
