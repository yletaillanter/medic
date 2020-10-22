package com.ylt.medic.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.ylt.medic.*
import com.ylt.medic.adapter.AdapterMedicSearch
import com.ylt.medic.adapter.ClickListener
import com.ylt.medic.database.model.Medicament
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class SearchFragment : Fragment(), ClickListener {

    lateinit var medocSearchRecyclerView: RecyclerView
    lateinit var data: ArrayList<Medicament>
    lateinit var adapter: AdapterMedicSearch

    lateinit var model: SearchViewModel;

    var compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        model = ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        initLayoutElement(root)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchView = SearchView((context as MainActivity).supportActionBar!!.themedContext)
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                when (newText.length) {
                    in 0..2 -> adapter.clear();
                    else -> startSearching(newText)
                }
                return false
            }
        })
    }

    private fun initLayoutElement(root: View) {
        // Recycler view
        medocSearchRecyclerView = root.findViewById(R.id.medoc_search_recycler_view)
        medocSearchRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context as MainActivity)
        medocSearchRecyclerView.layoutManager = layoutManager

        // Data and adapter
        data = ArrayList()
        adapter = AdapterMedicSearch()
        adapter.replace(data)
        adapter.setContext(context as MainActivity)
        adapter.setClickListener(this)
        medocSearchRecyclerView.adapter = adapter
    }

    override fun itemClicked(view: View, position: Int, recycler: String) {
        // TODO : remove, for debug only
        //deleteAll()

        // check if medic already exists
        // TODO invalid du cache
        val id = model.getIdByCis(data[position].codeCis)

        //var id = 0L;

        // if medic not in cache
        if (id == 0L ) {
            Timber.i("caching medic in DB")
            // insert du medicament
            insertMedicByCis(data[position].codeCis)
        } else {
            Timber.i("getting medic from cache! id: $id")
            view.findNavController().navigate(SearchFragmentDirections.actionNavigationSearchToNavigationDetailed(id))
            //val intent: Intent = Intent(activity, DetailViewPagerActivity::class.java)
            //intent.putExtra("id", id)
            //startActivity(intent)
        }
    }

    // empty database
    private fun deleteAll() {
        model.deleteTableContent()
    }

    fun startSearching(query: String) {
        Timber.i("Start searching: $query")
        compositeDisposable.add(
            model.searchMedicByName(query).subscribe { response ->
                Timber.i("response: $response")

                this.data = model.arrayToArrayList(response)
                adapter.replace(this.data)
            }
        )
    }
    fun getByCip13(cip: String) {
        Timber.i("getByCip13")

        compositeDisposable.add(
            model.getMedicByCip13(cip).subscribe { response ->
                Timber.i("response: $response")
                this.data = model.arrayToArrayList(response)
                adapter.replace(this.data)
            }
        )
    }

    private fun insertMedicByCis(cis: String) {
        Timber.i("insertMedicByCis: $cis")

        compositeDisposable.add(
            model.getMedicByCis(cis).subscribe { response ->
                Timber.i("response: $response")
                var id = model.insertFullMedic(model.arrayToArrayList(response)[0])

                //val intent = Intent(this, MedicDetailActivity::class.java)
                //val intent = Intent(context as com.ylt.medic.MainActivity, DetailViewPagerActivity::class.java)
                //intent.putExtra("id", id[0])
                //startActivity(intent)
                view?.findNavController()?.navigate(SearchFragmentDirections.actionNavigationSearchToNavigationDetailed(id[0]))
            }
        )
    }
    private fun getMedicByCis(cis: String) {
        Timber.i("getMedicByCis: $cis")

        compositeDisposable.add(
            model.getMedicByCis(cis).subscribe { response ->
                Timber.i("${model.arrayToArrayList(response)[0]}")
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.barcode -> {
                IntentIntegrator(activity).initiateScan(); // `this` is the current Activity
            }
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult = IntentIntegrator.parseActivityResult(
            requestCode,
            resultCode,
            data
        );
        if(result.getContents() == null) {
            medicNotFound()
        } else {
            if (result.getContents().length == 13)
                getByCip13(result.getContents())
            else
                medicNotFound()
        }
    }

    private fun medicNotFound() {
        Timber.i("Medic not found!")
        //Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show();
    }


}