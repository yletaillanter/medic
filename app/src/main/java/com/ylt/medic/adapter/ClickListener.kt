package com.ylt.medic.adapter

import android.view.View

/**
 * Created by yoannlt on 06/07/2017.
 */
interface ClickListener {
    abstract fun itemClicked(view: View, position: Int, recycler: String)
}