package com.ylt.medic.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.ylt.medic.MainActivity
import com.ylt.medic.R
import com.ylt.medic.adapter.AdapterMedicSearch
import com.ylt.medic.adapter.ClickListener
import com.ylt.medic.database.model.Medicament
import com.ylt.medic.databinding.FragmentSearchBinding
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class SearchFragment : Fragment(), ClickListener {

    lateinit var data: ArrayList<Medicament>
    lateinit var adapter: AdapterMedicSearch

    lateinit var model: SearchViewModel;
    private lateinit var searchView: SearchView

    var compositeDisposable = CompositeDisposable()

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView SearchFragment-cycle")
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        initLayoutElement()

        if (model.searchQuery != "")
            startSearching(model.searchQuery)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true);
        model = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        Timber.i("onCreateOptionsMenu")

        val mSearch = menu.findItem(R.id.search)
        searchView = mSearch.actionView as SearchView

        /*
        searchView = SearchView((context as MainActivity).supportActionBar!!.themedContext)

        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        */

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                when (newText.length) {
                    in 0..2 -> adapter.clear();
                    else -> {
                        Timber.i("onQueryTextChange $newText")
                        model.searchQuery = newText
                        startSearching(newText)
                    }
                }
                return false
            }
        })
    }

    private fun initLayoutElement() {
        Timber.d("initLayoutElement")

        // Recycler view
        binding.medocSearchRecyclerView.setHasFixedSize(true)
        binding.medocSearchRecyclerView.layoutManager = LinearLayoutManager(context as MainActivity)

        // Data and adapter
        data = ArrayList()
        adapter = AdapterMedicSearch()
        adapter.replace(data)
        adapter.setContext(context as MainActivity)
        adapter.setClickListener(this)
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.medocSearchRecyclerView.adapter = adapter
    }

    override fun itemClicked(view: View, position: Int, recycler: String) {
        // TODO : remove, for debug only
        //deleteAll()

        // check if medic already exists
        // TODO invalid du cache
        val id = model.getIdByCis(data[position].codeCis)

        // if medic not in cache
        if (id == 0L ) {
            Timber.i("Medicament not in db yet, cis: ${data[position].codeCis}")
            // insert du medicament
            insertMedicByCis(data[position].codeCis)
        } else {
            view.findNavController().navigate(
                SearchFragmentDirections.actionNavigationSearchToNavigationDetailed(
                    id, data[position].denomination.split(
                        ","
                    )[0]
                )
            )
        }
    }

    // empty database
    private fun deleteAll() {
        model.deleteTableContent()
    }

    fun startSearching(query: String) {
        Timber.i("Start searching: $query")

        compositeDisposable.add(
            model.searchMedicByName(query)
                .doOnError {
                    Toast.makeText(
                        context,
                        "Impossible de faire la recherche, vérifier votre connexion",
                        Toast.LENGTH_LONG
                    ).show()
                }.subscribe { response ->
                    this.data = response
                    adapter.replace(this.data)
                },
        )
    }

    fun getByCip13(cip: String) {
        Timber.i("getByCip13")

        compositeDisposable.add(
            model.getMedicByCip13(cip).subscribe { response ->
                Timber.d("response: $response")
                this.data = model.medicToArrayList(response)
                Timber.d("format: $this.data")
                adapter.replace(this.data)
            }
        )
    }

    private fun insertMedicByCis(cis: String) {
        Timber.d("insertMedicByCis: $cis")

        compositeDisposable.add(
            model.getMedicByCis(cis).subscribe { response ->
                val medic = response
                var id = model.insertFullMedic(medic)
                view?.findNavController()?.navigate(
                    SearchFragmentDirections.actionNavigationSearchToNavigationDetailed(
                        id[0], medic.denomination.split(",")[0]
                    )
                )
            }
        )
    }
    private fun getMedicByCis(cis: String) {
        Timber.i("getMedicByCis: $cis")

        compositeDisposable.add(
            model.getMedicByCis(cis).subscribe { response ->
                Timber.i("$response")
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //R.id.barcode -> IntentIntegrator(activity).initiateScan();
            R.id.barcode -> IntentIntegrator.forSupportFragment(this).initiateScan();

        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.i("onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult = IntentIntegrator.parseActivityResult(
            requestCode,
            resultCode,
            data
        );

        Timber.i("return codebar ${result.contents}")

        if(result.contents == null) {
            medicNotFound()
        } else {
            if (result.contents.length == 13)
                getByCip13(result.contents)
            else
                medicNotFound()
        }
    }

    private fun medicNotFound() {
        Timber.i("Medic not found!")
    }
}