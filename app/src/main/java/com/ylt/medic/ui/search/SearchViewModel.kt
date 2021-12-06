package com.ylt.medic.ui.search

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
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

    private lateinit var prefs: SharedPreferences

    private var retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var interfaceRest: InterfaceRest = retrofit.create(InterfaceRest::class.java)

    var searchQuery: String = ""

    private fun getBaseUrl(): String {
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())

        Timber.i("using URL from preferences: ${prefs.getString("protocol", null)!!}://${prefs.getString("address", null)!!}:${prefs.getString("port", null)!!}/")
        return "${prefs.getString("protocol", null)!!}://${prefs.getString("address", null)!!}:${prefs.getString("port", null)!!}/"
    }

    fun reloadRetrofit() {
        Timber.d("recreating retrofit with url: ${getBaseUrl()}")

        retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        interfaceRest = retrofit.create(InterfaceRest::class.java)
    }

    /* REST CALL */
    fun getMedicByCis(cis: String): Flowable<Medicament> {
        return interfaceRest.getMedicByCis(cis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchMedicByName(query: String): Flowable<ArrayList<Medicament>> {
        return interfaceRest.searchMedicByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Timber.e("error searchMedicByName $it") }
                .onErrorReturn {ArrayList()}
    }

    fun getMedicByCip13(cip: String): Flowable<Medicament> {
        return interfaceRest.getMedicByCip13(cip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { e -> Timber.e("error while getting medicByCip13: ${e.message}") }
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

    internal fun getBookmarked(): List<Medicament> {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getBookmarked()
    }

    internal fun deleteTableContent() {
        MedicDatabase.getInstance(getApplication()).asmrDao().deleteTable()
        MedicDatabase.getInstance(getApplication()).compoDao().deleteTable()
        MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().deleteTable()
        MedicDatabase.getInstance(getApplication()).generiqueDao().deleteTable()
        MedicDatabase.getInstance(getApplication()).infoImportantDao().deleteTable()
        MedicDatabase.getInstance(getApplication()).medicamentDao().deleteTable()
        MedicDatabase.getInstance(getApplication()).presentationDao().deleteTable()
        MedicDatabase.getInstance(getApplication()).smrDao().deleteTable()
    }
}