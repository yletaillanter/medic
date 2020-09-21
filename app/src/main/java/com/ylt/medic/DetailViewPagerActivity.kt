package com.ylt.medic

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ylt.medic.database.model.Medicament
import com.ylt.medic.databinding.ActivityMedicDetailBinding
import kotlinx.android.synthetic.main.detail_viewpager.*

class DetailViewPagerActivity : AppCompatActivity() {

    val categories = listOf(
        "Informations générales",
        "Boites",
        "Composition",
        "Générique",
        "Smr",
        "Asmr",
        "Informations importantes",
        "Conditions de prescription"
    )

    private val TAG = "DetailViewPagerActivity"
    private lateinit var binding: ActivityMedicDetailBinding
    lateinit var currentMedicament: Medicament
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreateViewpager")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_viewpager)

        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL

        val model = ViewModelProvider.AndroidViewModelFactory(this.application).create(DetailViewModel::class.java)

        //Get the medic
        val id:Long = getIntent().getLongExtra("id", 0L)
        currentMedicament = model.getByCis(id)

        // viewpager adapter
        val localAdapter = ViewPagerAdapter()
        localAdapter.setMedic(categories, currentMedicament)
        viewPager2.adapter = localAdapter

        // TabLayout mediator
        TabLayoutMediator(tabLayout, viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {tab.text = categories[0]}
                    1 -> {tab.text = categories[1]}
                    2 -> {tab.text = categories[2]}
                    3 -> {tab.text = categories[3]}
                    4 -> {tab.text = categories[4]}
                    5 -> {tab.text = categories[5]}
                    6 -> {tab.text = categories[6]}
                    7 -> {tab.text = categories[7]}
                }
            }).attach()
    }

}