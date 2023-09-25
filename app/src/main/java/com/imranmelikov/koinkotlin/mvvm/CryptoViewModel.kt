package com.imranmelikov.koinkotlin.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imranmelikov.koinkotlin.api.CryptoApi
import com.imranmelikov.koinkotlin.model.Crypto
import com.imranmelikov.koinkotlin.repo.CryptoRepoInterface
import com.imranmelikov.koinkotlin.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel(
   private val repo: CryptoRepoInterface
):ViewModel() {

    val cryptoList=MutableLiveData<Resource<List<Crypto>>>()
    val cryptoError=MutableLiveData<Resource<Boolean>>()
    val cryptoLoading=MutableLiveData<Resource<Boolean>>()


    private var job: Job?=null
    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value= Resource.error("Error", data = true)
    }

   fun load() {
       cryptoLoading.value= Resource.loading(data = true)

       job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

           val resource=repo.getData()
           withContext(Dispatchers.Main) {
               resource.data?.let{
                   cryptoList.value=resource
                   cryptoLoading.value=Resource.loading(false)
                   cryptoError.value=Resource.error("",false)
               }
           }

       }
   }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
   }
//without repo,resource
/*
class CryptoViewModel(
):ViewModel() {

     val cryptoList=MutableLiveData<List<Crypto>>()
    val cryptoError=MutableLiveData<Boolean>()
    val cryptoLoading=MutableLiveData<Boolean>()

    private var job: Job?=null
    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value=true
    }

    fun load() {
       cryptoLoading.value=true

        val BASE_URL="https://raw.githubusercontent.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
           val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cryptoError.value=false
                    cryptoLoading.value=false

                    response.body()?.let {
                        println(it)
                        cryptoList.value=it
                    }
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

 */

//without repo
/*
class CryptoViewModel(
):ViewModel() {

    val cryptoList=MutableLiveData<Resource<List<Crypto>>>()
    val cryptoError=MutableLiveData<Resource<Boolean>>()
    val cryptoLoading=MutableLiveData<Resource<Boolean>>()

    private var job: Job?=null
    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value= Resource.error("Error", data = true)
    }

    fun load() {
        cryptoLoading.value= Resource.loading(data = true)

        val BASE_URL="https://raw.githubusercontent.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

           val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cryptoError.value= Resource.error("", data = false)
                    cryptoLoading.value= Resource.loading(data = false)

                    response.body()?.let {
                        println(it)
                      cryptoList.value= Resource.success(it)
                    }
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

 */

