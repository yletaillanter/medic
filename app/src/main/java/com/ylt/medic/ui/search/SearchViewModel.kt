package com.ylt.medic.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.*
import com.ylt.medic.rest.InterfaceRest
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Created by yoannlt on 08/07/2017.
 */
class SearchViewModel(application:Application) : AndroidViewModel(application) {

    // Adresse du serveur
    //private val BASE_URL = "http://10.0.2.2:3000/"
    private val BASE_URL = "http://192.168.1.17:9001/"

    private val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private val interfaceRest: InterfaceRest = retrofit.create(InterfaceRest::class.java)

    var searchQuery: String = ""

    /* REST CALL */
    fun getMedicByCis(cis: String): Flowable<Medicament> {
        return interfaceRest.getMedicByCis(cis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    fun searchMedicByName(query: String): Flowable<ArrayList<Medicament>> {
        return interfaceRest.searchMedicByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { it -> Timber.e("error onsearchMedicByName $it") }
                .onErrorReturn {ArrayList()}
    }

    fun getMedicByCip13(cip: String): Flowable<Medicament> {
        return interfaceRest.getMedicByCip13(cip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { e -> Timber.e("error while getting medicByCip13: ${e.message}") };
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

    // Construct Full Medic From database
    internal fun getIdByCis(cis: String): Long {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getIdByCis(cis)
    }

    fun medicToArrayList(medicament: Medicament): ArrayList<Medicament> {
        val result: ArrayList<Medicament> = ArrayList()
        result.add(medicament)
        return result
    }

    internal fun getBookmarked(): List<Medicament> {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getBookmarked()
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

    private val _medic = MutableLiveData<ArrayList<Medicament>>()
    val medic: LiveData<ArrayList<Medicament>>
        get() = _medic
}