package com.ylt.medic

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ylt.medic.adapter.AdapterMedicSearch
import com.ylt.medic.adapter.ClickListener
import com.ylt.medic.adapter.MyPagerAdapter
import com.ylt.medic.database.model.Medicament

/**
 * Created by yoannlt on 13/06/2017.
 */
class FavoriteActivity : BaseActivity(), ClickListener {

    lateinit var tabLayout: TabLayout
    lateinit var toolbar: Toolbar
    lateinit var viewPager: ViewPager

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterMedicSearch

    lateinit var model: FavoriteViewModel
    lateinit var data:ArrayList<Medicament>

    override val contentViewId: Int
        get() = R.layout.activity_favorite

    override val navigationMenuItemId: Int
        get() = R.id.navigation_favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider.AndroidViewModelFactory(this.application).create(FavoriteViewModel::class.java)

        // TabLayout
        tabLayout = findViewById<TabLayout>(R.id.tabs)

        // Toolbar
        toolbar = findViewById<Toolbar>(R.id.toolbar_favorite)
        setSupportActionBar(toolbar)

        // ViewPager
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        val pagerAdapter: MyPagerAdapter = MyPagerAdapter()
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2);
        tabLayout.setupWithViewPager(viewPager)

        //RecyclerView
        recyclerView = findViewById<RecyclerView>(R.id.recycler_bookmarked)
        adapter = AdapterMedicSearch();
        data = ArrayList()
        adapter.replace(data)
        adapter.setContext(applicationContext)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        data = ArrayList( model.getBookmarked());
        adapter.replace(data)

    }

    override fun itemClicked(view: View, position: Int, recycler: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}