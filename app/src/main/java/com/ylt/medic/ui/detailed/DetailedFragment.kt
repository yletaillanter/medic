package com.ylt.medic.ui.detailed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ylt.medic.*
import com.ylt.medic.database.model.Medicament
import timber.log.Timber

class DetailedFragment : Fragment() {

    companion object {
        fun newInstance() = DetailedFragment()
    }

    val categories = listOf(
        Constants.INFO_GENERALES,
        Constants.BOITES,
        Constants.GENERIQUES,
        Constants.COMPOSITION,
        Constants.CONDI_PRESCRIPTION,
        Constants.INFO_IMPORTANTES,
        Constants.SMR,
        Constants.ASMR
    )

    lateinit var currentMedicament: Medicament

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root =  inflater.inflate(R.layout.fragment_detailed, container, false)
        Timber.i("onCreateView")

        val model = ViewModelProvider(this).get(DetailedViewModel::class.java)

        val tabLayout: TabLayout = root.findViewById(R.id.tabLayout)
        val viewPager2: ViewPager2 = root.findViewById(R.id.viewPager2)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //Get the medic
        val args: DetailedFragmentArgs by navArgs()
        currentMedicament = model.getByCis(args.id)

        Timber.i("getting safeargs ${args.id}")

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

        return root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}