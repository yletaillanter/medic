package com.ylt.medic.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ylt.medic.rest.InterfaceRest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by yoannlt on 15/06/2017.
 */

class ApiRestModule {
    // Adresse du serveur localhost depuis l'emulateur
    private val BASE_URL = "http://10.0.2.2:3000/"

/*
    fun provideInterceptor(): Interceptor {

        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            var request = requestBuilder.build()

            val url = request.url().newBuilder().build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    } */

    fun provideOkHttp(interceptor: Interceptor): OkHttpClient {
        // Interceptor pour logger les requêtes HTTP si besoin
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        // Init de l'instance du retrofit (pour les requêtes REST)
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    fun provideRequestInterface(retrofit: Retrofit): InterfaceRest {
        // Création de l'instance rest retrofit
        return retrofit.create(InterfaceRest::class.java)
    }
}