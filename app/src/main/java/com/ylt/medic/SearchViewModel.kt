package com.ylt.medic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.ylt.medic.database.MedicDatabase
import com.ylt.medic.database.model.Medicament
import com.ylt.medic.rest.InterfaceRest
import com.ylt.medic.rest.responses.MedicamentResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by yoannlt on 08/07/2017.
 */
class SearchViewModel(application:Application) : AndroidViewModel(application) {

    // Adresse du serveur
    private val BASE_URL = "http://10.0.2.2:3000/"

    private val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private val interfaceRest: InterfaceRest = retrofit.create(InterfaceRest::class.java)

    fun getFullMedic(cis: String): Flowable<Array<MedicamentResponse>> {
        return interfaceRest.getMedicByCis(cis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    fun searchByMedic(query: String): Flowable<Array<Medicament>> {
        return interfaceRest.searchMedicByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    fun getMedicByCip13(cip: String): Flowable<Array<Medicament>> {
        return interfaceRest.getMedicByCip13(cip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    internal fun insertMedic(medic: Medicament?): List<Long> {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().insert(medic!!)
    }

    internal fun getExistingByCisAndDenomination(cis: String?, denomination: String?): Long {
        return MedicDatabase.getInstance(getApplication()).medicamentDao().getIdOfExistingMedic(cis!!, denomination!!)
    }

    internal fun deleteTableContent() {
        MedicDatabase.getInstance(getApplication()).medicamentDao().deleteTable();
    }
}

