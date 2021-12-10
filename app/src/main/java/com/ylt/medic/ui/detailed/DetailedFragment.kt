package com.ylt.medic.ui.detailed

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ylt.medic.Constants
import com.ylt.medic.R
import com.ylt.medic.ViewPagerAdapter
import com.ylt.medic.database.model.Medicament
import timber.log.Timber
import com.google.android.material.snackbar.Snackbar


class DetailedFragment : Fragment() {

    companion object {
        fun newInstance() = DetailedFragment()
    }

    private val categories = listOf(
        Constants.INFO_GENERALES,
        Constants.BOITES,
        Constants.GENERIQUES,
        Constants.COMPOSITION,
        Constants.CONDI_PRESCRIPTION,
        Constants.AUTRES
    )

    private lateinit var currentMedicament: Medicament

    lateinit var model: DetailedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_detailed, container, false)

        model = ViewModelProvider(this)[DetailedViewModel::class.java]

        val appBarLayout = activity?.findViewById(R.id.appbar_layout) as AppBarLayout
        appBarLayout.setExpanded(true, false)

        activity!!.actionBar?.setDisplayHomeAsUpEnabled(true)

        val tabLayout: TabLayout = root.findViewById(R.id.tabLayout)
        val viewPager2: ViewPager2 = root.findViewById(R.id.viewPager2)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //Get the medic
        val args: DetailedFragmentArgs by navArgs()
        currentMedicament = model.getLiveDataMedic(args.id).value!!

        activity?.title = currentMedicament.denomination

        // viewpager adapter
        val localAdapter = ViewPagerAdapter(context)
        localAdapter.setMedic(categories, currentMedicament)
        viewPager2.adapter = localAdapter

        model.notice.observe(viewLifecycleOwner, Observer<String> {
                Timber.i("Call model.notice")
                localAdapter.setNotice(it)
                localAdapter.setProgressBarStatus(false)
                localAdapter.notifyDataSetChanged()
            }
        )

        if (model.notice.value.isNullOrEmpty())
            model.getLiveDataNotice(currentMedicament.codeCis)

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

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_bookmark, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark -> {
                val state = !currentMedicament.isBookmarked
                model.setBookmarked(currentMedicament.id, state)
                when (state) {
                    true -> {
                        item.setIcon(R.drawable.ic_bookmark_black_24dp)
                        Snackbar.make(activity!!.findViewById(android.R.id.content), resources.getString(R.string.add_bookmarked), Snackbar.LENGTH_SHORT).show()
                    }
                    false -> {
                        item.setIcon(R.drawable.ic_baseline_bookmark_border_24)
                        Snackbar.make(activity!!.findViewById(android.R.id.content), resources.getString(R.string.del_bookmarked), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        when(currentMedicament.isBookmarked) {
            true -> {
                menu.findItem(R.id.bookmark).setIcon(R.drawable.ic_bookmark_black_24dp)
            }
        }
    }
}