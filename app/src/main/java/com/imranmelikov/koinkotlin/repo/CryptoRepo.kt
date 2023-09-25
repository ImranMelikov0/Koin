package com.imranmelikov.koinkotlin.repo

import com.imranmelikov.koinkotlin.api.CryptoApi
import com.imranmelikov.koinkotlin.model.Crypto
import com.imranmelikov.koinkotlin.util.Resource

class CryptoRepo(
  private val cryptoApi: CryptoApi
): CryptoRepoInterface {
    override suspend fun getData(): Resource<List<Crypto>> {
        return try {
            val response=cryptoApi.getData()
            if (response.isSuccessful){
            response.body()?.let {
              return@let Resource.success(it)
            }?: Resource.error("Error", null)
            }else{
                Resource.error("Error", null)
            }
        } catch (e:Exception){
            Resource.error("Error", null)
        }
    }
}