package br.senac.lojaretrofit.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {

    private val retrofit: Retrofit
        get() {
            return Retrofit.Builder().baseUrl("https://oficinacordova.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build()


        }

    val produto: ProdutoService
            get() {
                return retrofit.create(ProdutoService::class.java)
            }
}