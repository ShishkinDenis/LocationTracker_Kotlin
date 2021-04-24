package com.shishkindenis.parentmodule.viewModels

import androidx.lifecycle.ViewModel
import com.shishkindenis.parentmodule.singletons.FirebaseUserSingleton

class CalendarViewModel : ViewModel() {

//    Заинжектить в конструктор
    var firebaseUserSingleton: FirebaseUserSingleton? = FirebaseUserSingleton()

    fun signOut() {
        firebaseUserSingleton?.getFirebaseAuth()?.signOut()
//        viewState.showToast(R.string.sign_out_successful)
    }
}