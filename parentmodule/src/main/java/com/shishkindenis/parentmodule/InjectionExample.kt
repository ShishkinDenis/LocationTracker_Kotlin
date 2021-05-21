package com.shishkindenis.parentmodule

import android.util.Log
import javax.inject.Inject

class InjectionExample @Inject constructor() {
   fun log(){
        Log.d("Injection", "Object was injected")
    }
}