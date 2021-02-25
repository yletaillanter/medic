package com.ylt.medic.ui.detailed

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ylt.medic.Constants
import com.ylt.medic.R
import com.ylt.medic.adapter.ViewPagerAdapter
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
        Constants.AUTRES
    )

    lateinit var currentMedicament: Medicament

    lateinit var model: DetailedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_detailed, container, false)
        Timber.i("onCreateView")

        model = ViewModelProvider(this).get(DetailedViewModel::class.java)

        val appBarLayout = activity?.findViewById(R.id.appbar_layout) as AppBarLayout
        appBarLayout.setExpanded(true, false)
        val toolbar = activity?.findViewById(R.id.toolbar) as Toolbar

        val tabLayout: TabLayout = root.findViewById(R.id.tabLayout)
        val viewPager2: ViewPager2 = root.findViewById(R.id.viewPager2)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //Get the medic
        val args: DetailedFragmentArgs by navArgs()
        currentMedicament = model.getByCis(args.id)
        Timber.i("getting safeargs ${args.id}")
        activity?.setTitle(currentMedicament.denomination)

        // viewpager adapter
        val localAdapter = ViewPagerAdapter(context)
        localAdapter.setMedic(categories, currentMedicament)
        viewPager2.adapter = localAdapter

        // TabLayout mediator
        TabLayoutMediator(tabLayout, viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = categories[0] }
                    1 -> { tab.text = categories[1] }
                    2 -> { tab.text = categories[2] }
                    3 -> { tab.text = categories[3] }
                    4 -> { tab.text = categories[4] }
                    5 -> { tab.text = categories[5] }
                    6 -> { tab.text = categories[6] }
                    7 -> { tab.text = categories[7] }
                }
            }).attach()

        return root;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_bookmark, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark -> {
                Timber.i("click on medic bookmark state: ${!currentMedicament.isBookmarked}")
                val state = !currentMedicament.isBookmarked
                model.setBookmarked(currentMedicament.id, state)
                when (state) {
                    true -> item.setIcon(R.drawable.ic_bookmark_black_24dp)
                    false -> item.setIcon(R.drawable.ic_baseline_bookmark_border_24)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        when(currentMedicament.isBookmarked) {
            // faulse by default
            true -> {
                Timber.i("when true")
                menu.findItem(R.id.bookmark).setIcon(R.drawable.ic_bookmark_black_24dp)
            }
        }
    }
}