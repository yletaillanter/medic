package com.ylt.medic

import android.view.Menu
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.ylt.medic.rest.responses.*
import io.reactivex.disposables.CompositeDisposable
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

    private val TAG: String = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider.AndroidViewModelFactory(this.application).create(SearchViewModel::class.java)

        initLayoutElement()
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
                if(s.length == 1 || s.length == 2) {
                    adapter.clear();
                }
                else if (s.length > 2) {
                    startSearching(s);
                }
                return false
            }
        })

        return true;
    }

    private fun initLayoutElement() {
        // Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Recycler view
        medocSearchRecyclerView = findViewById(R.id.medoc_search_recycler_view)
        medocSearchRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(applicationContext)
        medocSearchRecyclerView.layoutManager = layoutManager

        // Recycler view Divider
        //val dividerItemDecoration = DividerItemDecoration(medocSearchRecyclerView.context, layoutManager.orientation)
        //medocSearchRecyclerView.addItemDecoration(dividerItemDecoration)

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
        var id = model.getIdByCis(data[position].codeCis)

        // if medic not in cache
        if (id == 0L ) {
            Log.d(TAG, "caching medic in DB")
            // insert du medicament
            insertMedicByCis(data[position].codeCis)
        } else {
            Log.d(TAG, "getting medic from cache! id: $id")
            val intent: Intent = Intent(applicationContext, DetailViewPagerActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    // empty database
    private fun deleteAll() {
        model.deleteTableContent()
    }

    // TODO move in modelView
    fun startSearching(query:String) {
        compositeDisposable.add(
            model.searchMedicByName(query).subscribe { response ->
                this.data = arrayToArrayList(response)
                adapter.replace(this.data)
            }
        )
    }
    fun getByCip13(cip:String) {
        compositeDisposable.add(
            model.getMedicByCip13(cip).subscribe { response ->
                this.data = arrayToArrayList(response)
                adapter.replace(this.data)
            }
        )
    }
    private fun insertMedicByCis(cis: String) {
        compositeDisposable.add(
            model.getMedicByCis(cis).subscribe { response ->
                var id = model.insertFullMedic(arrayToArrayList(response)[0])

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
                Log.i(TAG, "${arrayToArrayList(response)[0].toString()}")
            }
        )
    }
    private fun arrayToArrayList(medocArray: Array<MedicamentResponse>): ArrayList<Medicament> {
        var result: ArrayList<Medicament> = ArrayList()
        medocArray.forEach { medoc -> result.add(medicResponseToMedic(medoc))}
        return result
    }

    // TODO move in modelView
    private fun medicResponseToMedic(medicResponse: MedicamentResponse): Medicament {
        var medicResult = Medicament()

        medicResult.codeCis = medicResponse.codeCis
        medicResult.dateAmm = medicResponse.dateAmm
        medicResult.denomination = medicResponse.denomination
        medicResult.etatCommer = medicResponse.etatCommer
        medicResult.formePharma = medicResponse.formePharma
        medicResult.numAutorEu = medicResponse.numAutorEu
        medicResult.statusBdm = medicResponse.statusBdm
        medicResult.statutAdminAmm = medicResponse.statutAdminAmm
        medicResult.survRenforcee = medicResponse.survRenforcee
        medicResult.titulaire = medicResponse.titulaire
        medicResult.typeProcedAmm = medicResponse.typeProcedAmm
        medicResult.voieAdministration = medicResponse.voieAdministration
        medicResult.ASMRs = getArrayASMR(medicResponse.ASMRs)
        medicResult.compos = getArrayCompo(medicResponse.compos)
        medicResult.conditionPrescritions = getArrayConditionPrecription(medicResponse.conditionPrescritions)
        medicResult.generiques = getArrayGenerique(medicResponse.generiques)
        medicResult.infos = getArrayInfoImportantes(medicResponse.infos)
        medicResult.presentations = getArrayPresentation(medicResponse.presentations)
        medicResult.SMRs = getArraySMR(medicResponse.SMRs)

        return medicResult
    }

    private fun getArrayPresentation(presentations: List<PresentationResponse>): ArrayList<Presentation> {
        var resultArray = ArrayList<Presentation>()
        var resultPresentation = Presentation()

        presentations.forEach{ prezResponse ->
            resultPresentation.codeCis = prezResponse.codeCis
            resultPresentation.codeCip13 = prezResponse.codeCip13
            resultPresentation.codeCip7 = prezResponse.codeCip7
            resultPresentation.libellePresentation = prezResponse.libellePresentation
            resultPresentation.statutAdminPres = prezResponse.statutAdminPres
            resultPresentation.etatCommer = prezResponse.etatCommer
            resultPresentation.dateDeclaCommer = prezResponse.dateDeclaCommer
            resultPresentation.agrementCollec = prezResponse.agrementCollec
            resultPresentation.txRemboursement = prezResponse.txRemboursement
            resultPresentation.indicDroitRemb = prezResponse.indicDroitRemb
            resultPresentation.prixMedicEuro = prezResponse.prixMedicEuro
            resultArray.add(resultPresentation)
        }
        return resultArray
    }

    private fun getArrayASMR(asmrs: List<AsmrResponse>): ArrayList<ASMR> {
        var resultArray = ArrayList<ASMR>()
        var resultAsmr  = ASMR()

        asmrs.forEach { asmrResponse ->
            resultAsmr.codeCis = asmrResponse.codeCis
            resultAsmr.codeDossierHas = asmrResponse.codeDossierHas
            resultAsmr.motifEval = asmrResponse.motifEval
            resultAsmr.dateAvisCommTransp = asmrResponse.dateAvisCommTransp
            resultAsmr.valeurAsmr = asmrResponse.valeurAsmr
            resultAsmr.libelleAsmr = asmrResponse.libelleAsmr
            resultArray.add(resultAsmr)
        }
        return resultArray
    }

    private fun getArrayCompo(compos: List<CompoResponse>): ArrayList<Compo> {
        var resultArray = ArrayList<Compo>()
        var resultCompo  = Compo()

        compos.forEach { compoResponse ->
            resultCompo.codeCis = compoResponse.codeCis
            resultCompo.designationElemPh = compoResponse.designationElemPh
            resultCompo.codeSubstance = compoResponse.codeSubstance
            resultCompo.denoSubstance = compoResponse.denoSubstance
            resultCompo.dosageSubstance = compoResponse.dosageSubstance
            resultCompo.refSubstance = compoResponse.refSubstance
            resultCompo.natureComposant = compoResponse.natureComposant
            resultCompo.numLiaisonSaFt = compoResponse.numLiaisonSaFt
            resultArray.add(resultCompo)
        }

        return resultArray
    }

    private fun getArrayConditionPrecription(condiPrescri: List<ConditionPrescriptionResponse>): ArrayList<ConditionPrescription> {
        var resultArray = ArrayList<ConditionPrescription>()
        var resultConditionPrescription  = ConditionPrescription()

        condiPrescri.forEach { condiPresResponse ->
            resultConditionPrescription.codeCis = condiPresResponse.codeCis
            resultConditionPrescription.condition = condiPresResponse.condition
            resultArray.add(resultConditionPrescription)
        }

        return resultArray
    }

    private fun getArrayGenerique(generiquesResponse: List<GeneriqueResponse>): ArrayList<Generique> {
        var resultArray = ArrayList<Generique>()
        var resultGenerique  = Generique()

        generiquesResponse.forEach { geneResponse ->
            resultGenerique.codeCis = geneResponse.codeCis
            resultGenerique.idGrpGener = geneResponse.idGrpGener
            resultGenerique.libelleGrpGener = geneResponse.libelleGrpGener
            resultGenerique.typeGener = geneResponse.typeGener
            resultGenerique.numeroTri = geneResponse.numeroTri
            resultArray.add(resultGenerique)
        }

        return resultArray
    }

    private fun getArrayInfoImportantes(infos: List<InfoImportantesResponse>): ArrayList<InfoImportantes> {
        var resultArray = ArrayList<InfoImportantes>()
        var resultInfo  = InfoImportantes()

        infos.forEach { info ->
            resultInfo.codeCis = info.codeCis
            resultInfo.dateDeb = info.dateDeb
            resultInfo.dateFin = info.dateFin
            resultInfo.textAndLink = info.textAndLink
            resultArray.add(resultInfo)
        }

        return resultArray
    }

    private fun getArraySMR(smrs: List<SmrResponse>): ArrayList<SMR> {
        var resultArray = ArrayList<SMR>()
        var resultSMR  = SMR()

        smrs.forEach { smr ->
            resultSMR.codeCis = smr.codeCis
            resultSMR.codeDossierHas = smr.codeDossierHas
            resultSMR.motifEval = smr.motifEval
            resultSMR.dateAvisCommTransp = smr.dateAvisCommTransp
            resultSMR.valeurSmr = smr.valeurSmr
            resultSMR.libelleSmr = smr.libelleSmr
            resultArray.add(resultSMR)
        }

        return resultArray
    }

    /*
    Onclick du menu
    // TODO onclick de la croix supprimer la liste des résultats
     */
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
            Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show();
        } else {
            if (result.getContents().length == 13)
                getByCip13(result.getContents())
            else
                Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show();
        }
    }
}