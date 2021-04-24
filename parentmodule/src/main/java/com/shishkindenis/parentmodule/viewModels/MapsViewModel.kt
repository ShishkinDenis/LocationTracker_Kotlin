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
import com.shishkindenis.parentmodule.singletons.DateSingleton
import com.shishkindenis.parentmodule.singletons.FirebaseUserSingleton

class MapsViewModel: ViewModel() {

// TODO   ЗАИНЖЕКТИТЬ В КОНСТРУКТОР
    var firebaseUserSingleton: FirebaseUserSingleton? = FirebaseUserSingleton()
    var dateSingleton: DateSingleton? =  DateSingleton()

    private val DATE_FIELD = "Date"
    private val TAG = "Location"
    private var firestoreDataBase: FirebaseFirestore? = null
    private var date: String? = null
    private var userId: String? = null

//NAMING
    val backToCalendarActivityWithCancelledResult : LiveData<Any>
        get() = backToCalendarActivityWithCancelledResultLiveData
    private val backToCalendarActivityWithCancelledResultLiveData = SingleLiveEvent<Any>()

    val backToCalendarActivityWithOkResult : LiveData<Any>
        get() = backToCalendarActivityWithOkResultLiveData
    private val backToCalendarActivityWithOkResultLiveData = SingleLiveEvent<Any>()

    val getPosition : SingleLiveEvent<Any>
        get() = getPositionLiveData
    private val getPositionLiveData = SingleLiveEvent<Any>()

    val setTrack : LiveData<Any>
        get() = setTrackLiveData
    private val setTrackLiveData = SingleLiveEvent<Any>()

    fun readLocation() {
        firestoreDataBase = FirebaseFirestore.getInstance()
//        userId = firebaseUserSingleton!!.getUserId()
//        ВЫЗЫВАТЬ ИЗ СИНГЛТОНА?
        userId = firebaseUserSingleton?.getFirebaseAuth()?.currentUser?.uid

        date = dateSingleton!!.getDate()

        firestoreDataBase?.collection(userId!!)
            ?.whereEqualTo(DATE_FIELD, date)
            ?.get()
            ?.addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    if (task.result!!.isEmpty) {
//                        viewState.backToCalendarActivityWithCancelledResult()
                        callBackToCalendarActivityWithCancelledResult()
                    } else {
                        for (document in task.result!!) {
                            Log.d(TAG, document.id + " => " + document.data)
//                            viewState.backToCalendarActivityWithOkResult()
                            callBackToCalendarActivityWithOkResult()
//                            viewState.getPosition(document)
                            callGetPosition(document)
//                            viewState.setTrack()
                            callSetTrack()
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            })
    }

//    NAMING

    fun callBackToCalendarActivityWithCancelledResult(){
        backToCalendarActivityWithCancelledResultLiveData.call()
    }

    fun callBackToCalendarActivityWithOkResult(){
        backToCalendarActivityWithOkResultLiveData.call()
    }

    fun callGetPosition(document: QueryDocumentSnapshot?){
        getPositionLiveData.value = document
    }

    fun callSetTrack(){
        setTrackLiveData.call()
    }


}