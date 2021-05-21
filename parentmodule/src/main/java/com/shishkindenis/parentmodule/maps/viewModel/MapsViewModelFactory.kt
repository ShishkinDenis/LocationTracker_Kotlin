package com.shishkindenis.parentmodule.maps.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkindenis.parentmodule.InjectionExample

class MapsViewModelFactory (var injectionExample: InjectionExample): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return MapsViewModel(injectionExample) as T
    }
}