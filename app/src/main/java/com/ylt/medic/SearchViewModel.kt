package com.ylt.medic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.*
import com.ylt.medic.rest.InterfaceRest
import com.ylt.medic.rest.responses.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.channels.Selector.open

/**
 * Created by yoannlt on 08/07/2017.
 */
class SearchViewModel(application:Application) : AndroidViewModel(application) {

    val TAG = "SearchViewModel.class"

    // Adresse du serveur
    private val BASE_URL = "http://10.0.2.2:3000/"
    //private val BASE_URL = "http://192.168.1.10:3000/"

    private val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private val interfaceRest: InterfaceRest = retrofit.create(InterfaceRest::class.java)

    /* REST CALL */
    fun getMedicByCis(cis: String): Flowable<Array<MedicamentResponse>> {
        return interfaceRest.getMedicByCis(cis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
    fun searchMedicByName(query: String): Flowable<Array<MedicamentResponse>> {
        return interfaceRest.searchMedicByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    fun getMedicByCip13(cip: String): Flowable<Array<MedicamentResponse>> {
        return interfaceRest.getMedicByCip13(cip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /* DATABASE */
    internal fun insertFullMedic(medic: Medicament): List<Long> {
        Timber.d( "insert: $medic")

        // On enregistre tous les elements lies au medicament
        medic.ASMRs.forEach{ asmr ->
            Timber.d( "inserting $asmr")
            MedicDatabase.getInstance(getApplication()).asmrDao().insert(asmr)
        }
        medic.compos.forEach{ compo ->
            Timber.d( "inserting $compo")
            MedicDatabase.getInstance(getApplication()).compoDao().insert(compo)
        }
        medic.conditionPrescritions.forEach{ condi ->
            Timber.d( "inserting $condi")
            MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().insert(condi)
        }
        medic.generiques.forEach{ gener ->
            Timber.d( "inserting $gener")
            MedicDatabase.getInstance(getApplication()).generiqueDao().insert(gener)
        }
        medic.infos.forEach{ info ->
            Timber.d( "inserting $info")
            MedicDatabase.getInstance(getApplication()).infoImportantDao().insert(info)
        }
        medic.presentations.forEach{ prez ->
            Timber.d( "inserting $prez")
            MedicDatabase.getInstance(getApplication()).presentationDao().insert(prez)
        }
        medic.SMRs.forEach{ smr ->
            Timber.d( "inserting $smr")
            MedicDatabase.getInstance(getApplication()).smrDao().insert(smr)
        }

        Timber.i("inserting $medic")
        // puis le medicament
        return MedicDatabase.getInstance(getApplication()).medicamentDao().insert(medic)
    }
    internal fun getExistingByCisAndDenomination(cis: String?, denomination: String?): Long {
        // TODO construct full medic
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getIdOfExistingMedic(cis!!, denomination!!)
    }

    // Construct Full Medic From database
    internal fun getIdByCis(cis: String): Long {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getIdByCis(cis)
    }


    fun arrayToArrayList(medocArray: Array<MedicamentResponse>): ArrayList<Medicament> {
        val result: ArrayList<Medicament> = ArrayList()
        medocArray.forEach { medoc -> result.add(medicResponseToMedic(medoc))}
        return result
    }

    fun medicResponseToMedic(medicResponse: MedicamentResponse): Medicament {
        val medicResult = Medicament()

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

    fun getArrayPresentation(presentations: List<PresentationResponse>): ArrayList<Presentation> {
        val resultArray = ArrayList<Presentation>()
        val resultPresentation = Presentation()

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

    fun getArrayASMR(asmrs: List<AsmrResponse>): ArrayList<ASMR> {
        val resultArray = ArrayList<ASMR>()
        val resultAsmr  = ASMR()

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

    fun getArrayCompo(compos: List<CompoResponse>): ArrayList<Compo> {
        val resultArray = ArrayList<Compo>()
        val resultCompo  = Compo()

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

    fun getArrayConditionPrecription(condiPrescri: List<ConditionPrescriptionResponse>): ArrayList<ConditionPrescription> {
        val resultArray = ArrayList<ConditionPrescription>()
        val resultConditionPrescription  = ConditionPrescription()

        condiPrescri.forEach { condiPresResponse ->
            resultConditionPrescription.codeCis = condiPresResponse.codeCis
            resultConditionPrescription.condition = condiPresResponse.condition
            resultArray.add(resultConditionPrescription)
        }

        return resultArray
    }

    fun getArrayGenerique(generiquesResponse: List<GeneriqueResponse>): ArrayList<Generique> {
        val resultArray = ArrayList<Generique>()
        val resultGenerique  = Generique()

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

    fun getArrayInfoImportantes(infos: List<InfoImportantesResponse>): ArrayList<InfoImportantes> {
        val resultArray = ArrayList<InfoImportantes>()
        val resultInfo  = InfoImportantes()

        infos.forEach { info ->
            resultInfo.codeCis = info.codeCis
            resultInfo.dateDeb = info.dateDeb
            resultInfo.dateFin = info.dateFin
            resultInfo.textAndLink = info.textAndLink
            resultArray.add(resultInfo)
        }

        return resultArray
    }

    fun getArraySMR(smrs: List<SmrResponse>): ArrayList<SMR> {
        val resultArray = ArrayList<SMR>()
        val resultSMR  = SMR()

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

    internal fun deleteTableContent() {
        MedicDatabase.getInstance(getApplication()).asmrDao().deleteTable();
        MedicDatabase.getInstance(getApplication()).compoDao().deleteTable();
        MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().deleteTable();
        MedicDatabase.getInstance(getApplication()).generiqueDao().deleteTable();
        MedicDatabase.getInstance(getApplication()).infoImportantDao().deleteTable();
        MedicDatabase.getInstance(getApplication()).medicamentDao().deleteTable();
        MedicDatabase.getInstance(getApplication()).presentationDao().deleteTable();
        MedicDatabase.getInstance(getApplication()).smrDao().deleteTable();
    }

    internal fun loadingCisBdpmData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val medic: Medicament = Medicament()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    medic.codeCis = array.get(0)
                    medic.denomination = array.get(1)
                    medic.formePharma = array.get(2)
                    medic.voieAdministration = array.get(3)
                    medic.statutAdminAmm = array.get(4)
                    medic.typeProcedAmm = array.get(5)
                    medic.etatCommer = array.get(6)
                    medic.dateAmm = array.get(7)
                    medic.statusBdm = array.get(8)
                    medic.numAutorEu = array.get(9)
                    medic.titulaire = array.get(10)
                    medic.survRenforcee = array.get(11)

                    insertFullMedic(medic)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }


    internal fun loadingCisCipData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val presentation: Presentation = Presentation()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    presentation.codeCis = array.get(0)
                    presentation.codeCip7 = array.get(1)
                    presentation.libellePresentation = array.get(2)
                    presentation.statutAdminPres = array.get(3)
                    presentation.etatCommer = array.get(4)
                    presentation.dateDeclaCommer = array.get(5)
                    presentation.codeCip13 = array.get(6)
                    presentation.agrementCollec = array.get(7)
                    presentation.txRemboursement = array.get(8)
                    presentation.prixMedicEuro = array.get(9)
                    presentation.indicDroitRemb = array.get(10)

                    MedicDatabase.getInstance(getApplication()).presentationDao().insert(presentation)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }

    internal fun loadingCisCompoData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val compo: Compo = Compo()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    compo.codeCis = array.get(0)
                    compo.designationElemPh = array.get(1)
                    compo.codeSubstance = array.get(2)
                    compo.denoSubstance = array.get(3)
                    compo.dosageSubstance = array.get(4)
                    compo.refSubstance = array.get(5)
                    compo.natureComposant = array.get(6)
                    compo.numLiaisonSaFt = array.get(7)

                    MedicDatabase.getInstance(getApplication()).compoDao().insert(compo)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }

    internal fun loadingSMRData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val smr: SMR = SMR()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    smr.codeCis = array.get(0)
                    smr.codeDossierHas = array.get(1)
                    smr.motifEval = array.get(2)
                    smr.dateAvisCommTransp = array.get(3)
                    smr.valeurSmr = array.get(4)
                    smr.libelleSmr = array.get(5)

                    MedicDatabase.getInstance(getApplication()).smrDao().insert(smr)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }


    internal fun loadingASMRData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val asmr: ASMR = ASMR()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    asmr.codeCis = array.get(0)
                    asmr.codeDossierHas = array.get(1)
                    asmr.motifEval = array.get(2)
                    asmr.dateAvisCommTransp = array.get(3)
                    asmr.valeurAsmr = array.get(4)
                    asmr.libelleAsmr = array.get(5)

                    MedicDatabase.getInstance(getApplication()).asmrDao().insert(asmr)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }

    internal fun loadingGenerData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val generique: Generique = Generique()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    generique.idGrpGener = array.get(0)
                    generique.libelleGrpGener = array.get(1)
                    generique.codeCis = array.get(2)
                    generique.typeGener = array.get(3)
                    generique.numeroTri = array.get(4)

                    MedicDatabase.getInstance(getApplication()).generiqueDao().insert(generique)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }

    internal fun loadingCPDData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val conditionPrescription: ConditionPrescription = ConditionPrescription()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    conditionPrescription.codeCis = array.get(0)
                    conditionPrescription.condition = array.get(1)

                    MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().insert(conditionPrescription)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }

    internal fun loadingInfoImportantesData(isr: InputStreamReader) {
        Timber.i("loadingData")

        val job = Job()
        CoroutineScope(job).launch {
            var reader : BufferedReader? = null
            try {
                reader = BufferedReader(isr);

                val infoImportantes: InfoImportantes = InfoImportantes()
                // do reading, usually loop until end of file reading
                var mLine:String
                for (line in reader.lines()) {
                    //Timber.i(line)
                    val array = line.split("\t")

                    infoImportantes.codeCis = array.get(0)
                    infoImportantes.dateDeb = array.get(1)
                    infoImportantes.dateFin = array.get(2)
                    infoImportantes.textAndLink = array.get(3)

                    MedicDatabase.getInstance(getApplication()).infoImportantDao().insert(infoImportantes)
                }
            } catch (e: IOException) {
                Timber.i("error: ${e.message}")
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (e: IOException) {
                        //log the exception
                    }
                }
            }
        }
    }
}

