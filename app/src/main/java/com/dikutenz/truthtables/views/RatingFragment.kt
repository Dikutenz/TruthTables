package com.dikutenz.truthtables.views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.dikutenz.truthtables.R


class RatingFragment : DialogFragment() {

    private lateinit var rateButton: Button
    private lateinit var closeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.rating_layout, container, false)
        initUI(view)
        return view
    }

    private fun goRate() {
        val appPackageName: String? = context?.packageName
        appPackageName.let {
            try {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
                startActivity(intent)
            } catch (exception: ActivityNotFoundException) {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
                startActivity(intent)
            }
        }
    }

    private fun initUI(view: View) {
        rateButton = view.findViewById(R.id.rate_button)
        rateButton.setOnClickListener { goRate() }

        closeButton = view.findViewById(R.id.later_button)
        closeButton.setOnClickListener { dismiss() }
    }
}
