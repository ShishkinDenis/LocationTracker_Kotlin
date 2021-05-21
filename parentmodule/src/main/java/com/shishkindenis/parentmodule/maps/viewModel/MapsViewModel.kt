package com.shishkindenis.parentmodule.maps.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import com.shishkindenis.parentmodule.maps.data.LocationRepository
import javax.inject.Inject

class MapsViewModel @Inject constructor(var repository: LocationRepository) : ViewModel() {

    private val TAG = "Location"

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
        repository.readLocationFromRepository().addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot> ->
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

    private fun backToCalendarActivityWithCancelledResult() {
        backToCalendarActivityWithCancelledResultLiveData.call()
    }

    private fun backToCalendarActivityWithOkResult() {
        backToCalendarActivityWithOkResultLiveData.call()
    }

    private fun getPosition(document: QueryDocumentSnapshot?) {
        getPositionLiveData.value = document
    }

    private fun setTrack() {
        setTrackLiveData.call()
    }


}