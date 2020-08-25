package com.ylt.medic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.ASMR
import com.ylt.medic.database.model.Medicament
import com.ylt.medic.rest.InterfaceRest
import com.ylt.medic.rest.responses.MedicamentResponse
import com.ylt.medic.rest.responses.PresentationDeserializer
import com.ylt.medic.rest.responses.PresentationResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by yoannlt on 08/07/2017.
 */
class SearchViewModel(application:Application) : AndroidViewModel(application) {

    val TAG = "SearchViewModel.class"

    // Adresse du serveur
    //private val BASE_URL = "http://10.0.2.2:3000/"
    private val BASE_URL = "http://192.168.2.181:3000/"

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
        // On enregistre tous les elements lies au medicament
        medic.ASMRs.forEach{ asmr ->
            Log.d(TAG, "inserting $asmr")
            MedicDatabase.getInstance(getApplication()).asmrDao().insert(asmr)
        }
        medic.compos.forEach{ compo ->
            Log.d(TAG, "inserting $compo")
            MedicDatabase.getInstance(getApplication()).compoDao().insert(compo)
        }
        medic.conditionPrescritions.forEach{ condi ->
            Log.d(TAG, "inserting $condi")
            MedicDatabase.getInstance(getApplication()).conditionPrescriptionDao().insert(condi)
        }
        medic.generiques.forEach{ gener ->
            Log.d(TAG, "inserting $gener")
            MedicDatabase.getInstance(getApplication()).generiqueDao().insert(gener)
        }
        medic.infos.forEach{ info ->
            Log.d(TAG, "inserting $info")
            MedicDatabase.getInstance(getApplication()).infoImportantDao().insert(info)
        }
        medic.presentations.forEach{ prez ->
            Log.d(TAG, "inserting $prez")
            MedicDatabase.getInstance(getApplication()).presentationDao().insert(prez)
        }
        medic.SMRs.forEach{ smr ->
            Log.d(TAG, "inserting $smr")
            MedicDatabase.getInstance(getApplication()).smrDao().insert(smr)
        }

        Log.i("INSERT", "inserting $medic")
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
}

