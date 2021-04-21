package com.shishkindenis.childmodule.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.childmodule.R
import com.shishkindenis.childmodule.activities.SendLocationActivity
import com.shishkindenis.locationtracker_kotlin.singletons.FirebaseUserSingleton
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class ForegroundService : Service() {

    private val LONGITUDE_FIELD = "Longitude"
    private val LATITUDE_FIELD = "Latitude"
    private val TIME_FIELD = "Time"
    private val DATE_FIELD = "Date"
    private val datePattern = "yyyy-MM-dd"
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private val TAG = "TAG"
    private val firestoreDataBase = FirebaseFirestore.getInstance()
    private val locationMap: Map<String, Any> = HashMap()

    @Inject
    var firebaseUserSingleton: FirebaseUserSingleton? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var userId: String? = null
    private var time: String? = null

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult != null) {
                getPosition(locationResult)
            }
            addData()
            Log.d(
                "Location",
                "TIME:" + DateFormat.getTimeInstance(
                    DateFormat.SHORT,
                    Locale.ENGLISH
                ).format(Date())
            )
        }
    }
    private var user: FirebaseUser? = null

    override fun onCreate() {
//        MyApplication.appComponent.inject(this)
        super.onCreate()
        isGpsEnabled()
        user = firebaseUserSingleton!!.getFirebaseAuth()!!.currentUser
        if (user != null) {
            firebaseUserSingleton!!.setUserId(user!!.uid)
            userId = firebaseUserSingleton!!.getUserId()
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this, SendLocationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification =
            NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(getString(R.string.location_tracker))
                .setSmallIcon(R.drawable.map)
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
                .setVibrate(null)
                .setContentIntent(pendingIntent)
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            if (isNetworkConnected() || isGpsEnabled()) {
                getLocation()
                notification.setContentText(getString(R.string.location_determination_in_progress))
            } else {
                notification.setContentText(getString(R.string.location_determination_is_impossible))
            }
            startForeground(1, notification.build())
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            serviceChannel.enableVibration(false)
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    fun getPosition(locationResult: LocationResult) {
        val mLastLocation: Location = locationResult.getLastLocation()
        val dateFormat: DateFormat =
            SimpleDateFormat(datePattern)
        time = DateFormat.getTimeInstance(
            DateFormat.SHORT,
            Locale.ENGLISH
        ).format(Date())
        locationMap.put(LATITUDE_FIELD, mLastLocation.latitude)
        locationMap.put(LONGITUDE_FIELD, mLastLocation.longitude)
        locationMap.put(DATE_FIELD, dateFormat.format(Date()))
        locationMap.put(TIME_FIELD, time)

    }

    fun addData() {
        firestoreDataBase.collection(userId!!)
            .add(locationMap)
            .addOnSuccessListener { documentReference: DocumentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e: Exception? ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getLocation() {
        mFusedLocationClient?.let { requestNewLocationData(it) }
    }

    @SuppressLint("MissingPermission")
    fun requestNewLocationData(mFusedLocationClient: FusedLocationProviderClient) {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //        mLocationRequest.setSmallestDisplacement(60);
//        mLocationRequest.setInterval(60000*10);
//        mLocationRequest.setFastestInterval(60000*10);
        mLocationRequest.interval = 6000
        mLocationRequest.fastestInterval = 6000
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    fun isGpsEnabled(): Boolean {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}