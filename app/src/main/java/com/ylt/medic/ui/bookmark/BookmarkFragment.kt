package com.ylt.medic.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.MainActivity
import com.ylt.medic.R
import com.ylt.medic.adapter.AdapterMedicSearch
import com.ylt.medic.adapter.ClickListener
import com.ylt.medic.database.model.Medicament
import com.ylt.medic.ui.search.SearchViewModel
import timber.log.Timber

class BookmarkFragment : Fragment(), ClickListener {

    private lateinit var recyclerView: RecyclerView
    lateinit var data:ArrayList<Medicament>
    lateinit var adapter: AdapterMedicSearch

    lateinit var model: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bookmark, container, false)
        Timber.i("onCreateView")

        model = ViewModelProvider(this).get(SearchViewModel::class.java)

        //RecyclerView
        recyclerView = root.findViewById(R.id.bookmark_recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context as MainActivity)
        recyclerView.layoutManager = layoutManager

        data = ArrayList()
        adapter = AdapterMedicSearch()
        adapter.replace(data)
        adapter.setContext(context as MainActivity)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter

        loadData()
        return root
    }

    private fun loadData() {
        data = ArrayList( model.getBookmarked())
        Timber.i("getBookmarked: $data")
        adapter.replace(data)
        adapter.notifyDataSetChanged()
    }

    override fun itemClicked(view: View, position: Int, recycler: String) {
        val id = model.getIdByCis(data[position].codeCis)
        view.findNavController().navigate(BookmarkFragmentDirections.actionNavigationBookmarkToNavigationDetailed(id.toLong(), data[position].denomination.split(",")[0]))
    }

}