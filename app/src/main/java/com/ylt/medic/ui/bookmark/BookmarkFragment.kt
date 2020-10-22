package com.ylt.medic.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ylt.medic.R
import timber.log.Timber

class BookmarkFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bookmark, container, false)
        Timber.i("bookmarkFragment YLT-TEST")

        return root
    }
}