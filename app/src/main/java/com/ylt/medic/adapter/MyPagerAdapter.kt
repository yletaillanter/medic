package com.ylt.medic.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ylt.medic.R

/**
 * Created by yoannlt on 15/07/2017.
 */
class MyPagerAdapter: PagerAdapter() {
    override fun getCount(): Int {
        return 2
    }

    override fun instantiateItem(collection: View, position: Int): Any {

        val inflater = collection.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var resId = 0
        when (position) {
            0 -> resId = R.layout.list_favorite_medic
            1 -> resId = R.layout.list_scheduled_medic
        }

        val view = inflater.inflate(resId, null)

        (collection as ViewPager).addView(view, 0)

        return view
    }

    override fun destroyItem(arg0: View, arg1: Int, arg2: Any) {
        (arg0 as ViewPager).removeView(arg2 as View)

    }


    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1 as View

    }

    override fun saveState(): Parcelable? {
        return null
    }
}