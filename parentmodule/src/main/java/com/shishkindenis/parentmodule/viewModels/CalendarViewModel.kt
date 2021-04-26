package com.shishkindenis.parentmodule.viewModels

import androidx.lifecycle.ViewModel
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton

class CalendarViewModel : ViewModel() {

    //    Заинжектить в конструктор
//    var firebaseUserSingleton: FirebaseUserSingleton? = FirebaseUserSingleton()

    fun signOut() {
//        firebaseUserSingleton?.getFirebaseAuth()?.signOut()
        FirebaseUserSingleton.getFirebaseAuth()?.signOut()
//        TODO
//        viewState.showToast(R.string.sign_out_successful)
    }
}