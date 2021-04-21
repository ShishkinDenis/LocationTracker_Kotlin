package com.shishkindenis.locationtracker_kotlin.viewModel

//class EmailAuthViewModel() : ViewModel() {
//
////    baseActivity
//
//    private var userId: String? = null
//    lateinit var firebaseUserSingleton: FirebaseUserSingleton
//
//    //    DELETE
//    private val auth = FirebaseAuth.getInstance()
//
//    @Inject
//    constructor(firebaseUserSingleton: FirebaseUserSingleton) : this() {
//        this.firebaseUserSingleton = firebaseUserSingleton
//    }
//
//    //    TODO
////    Можно ли проще?
////    get?
//    val toast: LiveData<Int>
//        get() = toastLiveData
//    private val toastLiveData = SingleLiveEvent<Int>()
//
//    val toastwithEmail: LiveData<String>
//        get() = toastWithEmailLiveData
//    private val toastWithEmailLiveData = SingleLiveEvent<String>()
//
//    val startCalendarActivity: LiveData<Any>
//        get() = startLiveData
//    private val startLiveData = SingleLiveEvent<Any>()
//
//    fun createAccount(email: String, password: String?) {
//
////    firebaseUserSingleton.getFirebaseAuth()?.createUserWithEmailAndPassword(email, password!!)
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task: Task<AuthResult?> ->
//                if (task.isSuccessful) {
//                    showToastWithEmail("User with email: $email was signed up ")
//                } else {
//                    showToast(R.string.signing_up_failed)
//                }
//            }
//    }
//
//    fun signIn(email: String, password: String) {
////        TODO DELETE
////        var firebaseAuth : FirebaseAuth? = firebaseUserSingleton.getFirebaseAuth();
//
////    firebaseUserSingleton.getFirebaseAuth()?.signInWithEmailAndPassword(email!!, password!!)
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task: Task<AuthResult?> ->
//                if (task.isSuccessful) {
//                    showToast(R.string.authentication_successful)
//                    //                val user = firebaseUserSingleton!!.getFirebaseAuth()?.currentUser
//                    //                userId = user!!.uid
//                    //                    firebaseUserSingleton!!.setUserId(userId)
//                    //                    viewState.goToCalendarActivity()
//                    goToMainActivity()
//                } else {
//                    showToast(R.string.authentication_failed)
//                }
//            }
//    }
//
//    fun showToast(toastMessage: Int) {
//        toastLiveData.value = toastMessage
//    }
//
//    fun showToastWithEmail(toastMessage: String) {
//        toastWithEmailLiveData.value = toastMessage
//    }
//
//    //    TODO Calendar Activity
//    fun goToMainActivity() {
//        startLiveData.call()
//    }
//
//
//}