package com.shishkindenis.parentmodule.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.shishkindenis.loginmodule.SingleLiveEvent
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton
import com.shishkindenis.parentmodule.singletons.DateSingleton


class MapsViewModel : ViewModel() {

    private val DATE_FIELD = "Date"
    private val TAG = "Location"
    private var firestoreDataBase: FirebaseFirestore? = null
    private var date: String? = null
    private var userId: String? = null

    val backToCalendarActivityWithCancelledResult: LiveData<Any>
        get() = backToCalendarActivityWithCancelledResultLiveData
    private val backToCalendarActivityWithCancelledResultLiveData = SingleLiveEvent<Any>()

    val backToCalendarActivityWithOkResult: LiveData<Any>
        get() = backToCalendarActivityWithOkResultLiveData
    private val backToCalendarActivityWithOkResultLiveData = SingleLiveEvent<Any>()

    val getPosition: SingleLiveEvent<Any>
        get() = getPositionLiveData
    private val getPositionLiveData = SingleLiveEvent<Any>()

    val setTrack: LiveData<Any>
        get() = setTrackLiveData
    private val setTrackLiveData = SingleLiveEvent<Any>()

    fun readLocation() {
        firestoreDataBase = FirebaseFirestore.getInstance()
        userId = FirebaseUserSingleton.getFirebaseAuth()?.currentUser?.uid
        date = DateSingleton.getDate()
        firestoreDataBase?.collection(userId!!)
            ?.whereEqualTo(DATE_FIELD, date)
            ?.get()
            ?.addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    if (task.result!!.isEmpty) {
                        backToCalendarActivityWithCancelledResult()
                    } else {
                        for (document in task.result!!) {
                            Log.d(TAG, document.id + " => " + document.data)
                            backToCalendarActivityWithOkResult()
                            getPosition(document)
                            setTrack()
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            })
    }


    fun backToCalendarActivityWithCancelledResult() {
        backToCalendarActivityWithCancelledResultLiveData.call()
    }

    fun backToCalendarActivityWithOkResult() {
        backToCalendarActivityWithOkResultLiveData.call()
    }

    fun getPosition(document: QueryDocumentSnapshot?) {
        getPositionLiveData.value = document
    }

    fun setTrack() {
        setTrackLiveData.call()
    }


}