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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.rating_layout, container, false)
        val button: Button = view.findViewById(R.id.rate_button)
        button.setOnClickListener {
            val appPackageName: String = context?.packageName ?: ""

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (exception: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
        val closeButton: Button = view.findViewById(R.id.later_button)
        closeButton.setOnClickListener { dismiss() }
        return view
    }

}
