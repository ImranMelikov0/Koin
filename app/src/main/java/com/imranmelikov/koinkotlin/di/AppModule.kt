package com.imranmelikov.koinkotlin.di

import com.imranmelikov.koinkotlin.api.CryptoApi
import com.imranmelikov.koinkotlin.mvvm.CryptoViewModel
import com.imranmelikov.koinkotlin.repo.CryptoRepo
import com.imranmelikov.koinkotlin.repo.CryptoRepoInterface
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule= module {
    single {
        val BASE_URL="https://raw.githubusercontent.com/"
          Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }

    single <CryptoRepoInterface>{
        CryptoRepo(get())
    }
    viewModel {
        CryptoViewModel(get())
    }
    factory {

    }
}