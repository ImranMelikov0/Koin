package com.imranmelikov.koinkotlin.repo

import com.imranmelikov.koinkotlin.model.Crypto
import com.imranmelikov.koinkotlin.util.Resource

interface CryptoRepoInterface {
   suspend fun getData(): Resource<List<Crypto>>
}