package com.shishkindenis.childmodule.sendLocation.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

interface LocationRepository {
    fun addDataToRepository(locationMap: MutableMap<String, Any>): Task<DocumentReference>?
}