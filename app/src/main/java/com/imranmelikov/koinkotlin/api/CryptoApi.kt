package com.imranmelikov.koinkotlin.api

import com.imranmelikov.koinkotlin.model.Crypto
import retrofit2.Response
import retrofit2.http.GET

interface CryptoApi {
    //https://raw.githubusercontent.com/
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData():Response<List<Crypto>>
}