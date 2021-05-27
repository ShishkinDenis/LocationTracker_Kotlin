package com.shishkindenis.parentmodule.maps.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface LocationRepository {
    fun readLocationFromRepository(): Task<QuerySnapshot>
}