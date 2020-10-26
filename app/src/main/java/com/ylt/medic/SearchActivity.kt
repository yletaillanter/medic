package com.ylt.medic

import android.view.Menu
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.ylt.medic.adapter.AdapterMedicSearch
import com.ylt.medic.adapter.ClickListener
import com.ylt.medic.database.model.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.collections.ArrayList


/**
 * Created by yoannlt on 13/06/2017.
 */
class SearchActivity : BaseActivity(), ClickListener {

    lateinit var toolbar: Toolbar
    lateinit var medocSearchRecyclerView: RecyclerView
    lateinit var data: ArrayList<Medicament>
    lateinit var adapter: AdapterMedicSearch

    lateinit var model: SearchViewModel;

    var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")

        model = ViewModelProvider.AndroidViewModelFactory(this.application).create(SearchViewModel::class.java)

        checkDatabaseVersion()

        /*
        model.loadingCisBdpmData(InputStreamReader(assets.open("CIS_bdpm.txt"), "ISO-8859-1"))
        model.loadingCisCipData(InputStreamReader(assets.open("CIS_CIP_bdpm.txt"), "ISO-8859-1"))
        model.loadingCisCompoData(InputStreamReader(assets.open("CIS_COMPO_bdpm.txt"), "ISO-8859-1"))
        model.loadingCPDData(InputStreamReader(assets.open("CIS_CPD_bdpm.txt"), "ISO-8859-1"))
        model.loadingGenerData(InputStreamReader(assets.open("CIS_GENER_bdpm.txt"), "ISO-8859-1"))
        model.loadingASMRData(InputStreamReader(assets.open("CIS_HAS_ASMR_bdpm.txt"), "ISO-8859-1"))
        model.loadingSMRData(InputStreamReader(assets.open("CIS_HAS_SMR_bdpm.txt"), "ISO-8859-1"))
        */

        initLayoutElement()
    }

    private fun checkDatabaseVersion() {
        // TODO : https://developer.android.com/training/data-storage/shared-preferences

        val sharedPref: SharedPreferences = getSharedPreferences("version", 0)
        val version = model.loadingDataVersion(InputStreamReader(assets.open("version.txt"), "ISO-8859-1"))

        if (sharedPref.getString("version", "0") != version) {
            Timber.i("version diff")
        } else {
            Timber.i("version identique")
        }

    }

    override val contentViewId: Int
        get() = R.layout.activity_search

    override val navigationMenuItemId: Int
        get() = R.id.navigation_search

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        // Retrieve the SearchView and plug it into SearchManager
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                // Do nothing
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                when (s.length) {
                    in 0..2 -> adapter.clear();
                    else -> startSearching(s)
                }
                return false
            }
        })
        return true;
    }

    private fun initLayoutElement() {
        Timber.i("initLayout")

        // Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Recycler view
        medocSearchRecyclerView = findViewById(R.id.medoc_search_recycler_view)
        medocSearchRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(applicationContext)
        medocSearchRecyclerView.layoutManager = layoutManager

        // Data and adapter
        data = ArrayList()
        adapter = AdapterMedicSearch()
        adapter.replace(data)
        adapter.setContext(applicationContext)
        adapter.setClickListener(this)
        medocSearchRecyclerView.adapter = adapter
    }

    override fun itemClicked(view: View, position: Int, recycler: String) {
        // TODO : remove, for debug only
        //deleteAll()

        // check if medic already exists
        // TODO invalid du cache
        //var id = model.getIdByCis(data[position].codeCis)

        // if medic not in cache
        //if (id == 0L ) {
            //Timber.d( "caching medic in DB")
            // insert du medicament
          //  insertMedicByCis(data[position].codeCis)
        //} else {
            //Timber.d( "getting medic from cache! id: $id")
        val intent: Intent = Intent(applicationContext, DetailViewPagerActivity::class.java)
        intent.putExtra("cis", data[position].codeCis)
        startActivity(intent)
        //}
    }

    // empty database
    private fun deleteAll() {
        model.deleteTableContent()
    }

    fun startSearching(query:String) {
        this.data = ArrayList(model.searchMedicByDenomination(query))
        adapter.replace(data)
    }

    fun getByCip13(cip:String) {
        compositeDisposable.add(
            model.getMedicByCip13(cip).subscribe { response ->
                this.data = model.arrayToArrayList(response)
                adapter.replace(this.data)
            }
        )
    }
    private fun insertMedicByCis(cis: String) {
        compositeDisposable.add(
            model.getMedicByCis(cis).subscribe { response ->
                var id = model.insertFullMedic(model.arrayToArrayList(response)[0])

                //val intent = Intent(applicationContext, MedicDetailActivity::class.java)
                val intent = Intent(applicationContext, DetailViewPagerActivity::class.java)
                intent.putExtra("id", id[0])
                startActivity(intent)
            }
        )
    }
    private fun getMedicByCis(cis: String){
        compositeDisposable.add(
            model.getMedicByCis(cis).subscribe { response ->
                Timber.i( "${model.arrayToArrayList(response)[0].toString()}")
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.barcode -> {
                IntentIntegrator(this).initiateScan(); // `this` is the current Activity
            }
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
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
        Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show();
    }
}