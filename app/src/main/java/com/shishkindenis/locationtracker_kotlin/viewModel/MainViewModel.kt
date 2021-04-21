package com.shishkindenis.locationtracker_kotlin.viewModel

//class MainViewModel : ViewModel() {
//
////    Move to ViewModel
//
//    private var userID: String? = null
//    private var user: FirebaseUser? = null
//
////    fun checkIfUserLoggedIn() {
////        user = firebaseUserSingleton?.getFirebaseAuth()?.currentUser
////        if (user != null) {
////            userID = user!!.uid
////            firebaseUserSingleton?.setUserId(userID)
//////            viewState.goToCalendarActivityForResult()
//////           goToCalendarActivityForResult()
////        }
////    }
//
//
//    //            DELETE
//    fun logSomething() {
//        Log.d("abc", "1")
//    }
//
//    val toast: LiveData<String>
//        get() = toastLiveData
//
//    private val toastLiveData = SingleLiveEvent<String>()
//
//    fun doSomething() {
//        toastLiveData.value = "Hello world!"
//    }
//
//}