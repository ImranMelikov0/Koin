package com.imranmelikov.koinkotlin.di

import com.imranmelikov.koinkotlin.view.ListFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

val anotherModule= module {
    scope<ListFragment> {
        scoped(qualifier = named("Hello")) {
            "Hello"
        }
        scoped (named("Hi")){
            "Hi"
        }
    }
}