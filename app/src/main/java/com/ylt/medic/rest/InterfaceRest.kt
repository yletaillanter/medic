package com.ylt.medic.rest

import com.ylt.medic.database.model.Medicament
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yoannlt on 13/06/2017.
 */
interface InterfaceRest {
    @GET("search/{query}")
    fun searchMedicByName(@Path("query") query: String): Flowable<ArrayList<Medicament>>

    @GET("all/{cis}")
    fun getMedicByCis(@Path("cis") cis: String): Flowable<Medicament>

    @GET("cip13/{cip13}")
    fun getMedicByCip13(@Path("cip13") cip13: String): Flowable<Medicament>
}