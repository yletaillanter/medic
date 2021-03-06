package com.ylt.medic.rest

import io.reactivex.Flowable
import com.ylt.medic.rest.responses.MedicamentResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yoannlt on 13/06/2017.
 */
interface InterfaceRest {
    @GET("search/{query}")
    abstract fun searchMedicByName(@Path("query") query: String): Flowable<Array<MedicamentResponse>>

    @GET("all/{cis}")
    abstract fun getMedicByCis(@Path("cis") cis: String): Flowable<Array<MedicamentResponse>>

    @GET("cip13/{cip13}")
    abstract fun getMedicByCip13(@Path("cip13") cip13: String): Flowable<Array<MedicamentResponse>>
}