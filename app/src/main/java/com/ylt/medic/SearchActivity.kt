package com.ylt.medic

import android.annotation.SuppressLint
import android.view.Menu
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ylt.medic.adapter.AdapterMedicSearch
import com.ylt.medic.adapter.ClickListener
import com.ylt.medic.database.model.Medicament
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider.AndroidViewModelFactory(this.application).create(SearchViewModel::class.java)

        initLayoutElement()
        //getByCis("60474889")
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
                //startSearching(s);
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if(s.length == 1 || s.length == 2 || s.length == 3) {
                    adapter.clear();
                }
                else if (s.length > 3) {
                    startSearching(s);
                }
                return false
            }
        })

        return true;
    }

    fun startSearching(query:String) {
        model.searchByMedic(query).subscribe { response ->
            this.data = arrayToArrayList(response)
            adapter.replace(this.data)
        };
    }

    fun getByCip13(cip:String) {
        model.getMedicByCip13(cip).subscribe { response ->
            this.data = arrayToArrayList(response)
            adapter.replace(this.data)
        };
    }

    fun getByCis(cis: String) {
        model.getFullMedic(cis).subscribe { response ->
            //Log.i("testylt", response.toString())
            response.forEach { medoc ->  Log.i("testylt", medoc.toString())}
        }
    }

    private fun arrayToArrayList(medocArray: Array<Medicament>): ArrayList<Medicament> {
        var result: ArrayList<Medicament> = ArrayList<Medicament>()
        for(medoc in medocArray) {
            result.add(medoc)
        }

        return result
    }

    private fun initLayoutElement() {
        // Toolbar
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Recycler view
        medocSearchRecyclerView = findViewById<RecyclerView>(R.id.medoc_search_recycler_view)
        medocSearchRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(getApplicationContext())
        medocSearchRecyclerView.setLayoutManager(layoutManager)

        // Recycler view Divider
        val dividerItemDecoration = DividerItemDecoration(medocSearchRecyclerView.getContext(), layoutManager.getOrientation())
        medocSearchRecyclerView.addItemDecoration(dividerItemDecoration)

        // Data and adapter
        data = ArrayList<Medicament>()
        adapter = AdapterMedicSearch()
        adapter.replace(data)
        adapter.setContext(applicationContext)
        adapter.setClickListener(this)
        medocSearchRecyclerView.setAdapter(adapter)
    }

    override fun itemClicked(view: View, position: Int, recycler: String) {
        //model.deleteTableContent()

        // On check si medic déjà en base via cis+denomination
        var id:Long = model.getExistingByCisAndDenomination(data.get(position).codeCis, data[position].denomination)

        // insertion en base
        if (id == 0L ){
            val ids:List<Long> = model.insertMedic(data.get(position))
            Log.d("itemClicked", data.get(position).toString())
            id = ids.get(0);
        }

        val intent: Intent = Intent(applicationContext, MedicDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            /*
            R.id.barcode -> {
                IntentIntegrator(this).initiateScan(); // `this` is the current Activity
            }
           */
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*
        val result:IntentResult  = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result.getContents() == null) {
            Toast.makeText(this, "Médicament non trouvé, utilisez la rechercher par nom.", Toast.LENGTH_LONG).show();
        } else {

            if (result.getContents().length == 7)
                getByCip7(result.getContents())
            else if (result.getContents().length == 13)
                getByCip13(result.getContents())
            else
                Toast.makeText(this, "Médicament non trouvé, utilisez la rechercher par nom.", Toast.LENGTH_LONG).show();
        }
         */

    }
}

// db stuff

//db.medicamentLiteDao().insert(response.get(0))
//message.setText(db.medicamentLiteDao().getAll().size)