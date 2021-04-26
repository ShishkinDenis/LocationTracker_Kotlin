package com.shishkindenis.loginmodule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.SingleLiveEvent
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton


class EmailAuthViewModel() : ViewModel() {

//    baseActivity

    private var userId: String? = null
//    lateinit var firebaseUserSingletonO: FirebaseUserSingletonO

    //    DELETE
//    private val auth = FirebaseAuth.getInstance()

//    @Inject
//    constructor(firebaseUserSingletonO: FirebaseUserSingletonO) : this() {
//        this.firebaseUserSingletonO = firebaseUserSingletonO
//    }

    //    TODO
//    Можно ли проще?
//    get?
    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val toastwithEmail: LiveData<String>
        get() = toastWithEmailLiveData
    private val toastWithEmailLiveData = SingleLiveEvent<String>()

//    val startSendLocationActivity: LiveData<Any>
//        get() = startLiveData
//    private val startLiveData = SingleLiveEvent<Any>()

    val module: LiveData<Any>
        get() = moduleLiveData
    private val moduleLiveData = SingleLiveEvent<Any>()

    fun createAccount(email: String, password: String?) {

//        firebaseUserSingleton.getFirebaseAuth()?.createUserWithEmailAndPassword(email, password!!)
        FirebaseUserSingleton.getFirebaseAuth()?.createUserWithEmailAndPassword(email, password)
//        auth.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        showToastWithEmail("User with email: $email was signed up ")
                    } else {
                        showToast(R.string.signing_up_failed)
                    }
                }
    }

    fun signIn(email: String, password: String) {
//        TODO DELETE
//        var firebaseAuth : FirebaseAuth? = firebaseUserSingleton.getFirebaseAuth();

//    firebaseUserSingleton.getFirebaseAuth()?.signInWithEmailAndPassword(email!!, password!!)
        FirebaseUserSingleton.getFirebaseAuth()?.signInWithEmailAndPassword(email, password)
//        auth.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        showToast(R.string.authentication_successful)
                        //                val user = firebaseUserSingleton!!.getFirebaseAuth()?.currentUser
                        //                userId = user!!.uid
                        //                    firebaseUserSingleton!!.setUserId(userId)
                        goToSpecificModule()
                    } else {
                        showToast(R.string.authentication_failed)
                    }
                }
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    fun showToastWithEmail(toastMessage: String) {
        toastWithEmailLiveData.value = toastMessage
    }

    fun goToSpecificModule() {
        moduleLiveData.call()
    }


}