package com.ylt.medic.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ylt.medic.R
import mehdi.sakout.aboutpage.AboutPage


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AboutPage(context)
            .isRTL(false)
            .setDescription(getString(R.string.app_description))
            .addGroup(getString(R.string.contact_group))
            .addEmail("ylt.medicament@gmail.com", "Email")
            .create()
    }
}