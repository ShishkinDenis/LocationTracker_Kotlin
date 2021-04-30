package com.shishkindenis.childmodule.sendLocation.services

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
import com.shishkindenis.childmodule.R
import com.shishkindenis.childmodule.di.DaggerApplicationComponent

import com.shishkindenis.childmodule.sendLocation.data.LocationRepository
import com.shishkindenis.childmodule.sendLocation.view.SendLocationActivity
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

//TODO инжектить в конструктор service ?
//Написать inject component в OnCreate
//Почитать Dagger startAndroid
//Почитать про injection  в constructor
//Или просто Inject Repository, который подтянет уже готовый с Firestore и singleton
//https://developer.android.com/training/dependency-injection#java
//https://developer.android.com/training/dependency-injection/dagger-android
class ForegroundService : Service() {

    private val LONGITUDE_FIELD = "Longitude"
    private val LATITUDE_FIELD = "Latitude"
    private val TIME_FIELD = "Time"
    private val DATE_FIELD = "Date"
    private val datePattern = "yyyy-MM-dd"
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private val TAG = "LOCATION"
    private var locationMap: MutableMap<String, Any> = HashMap()
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var userId: String? = null
    private var time: String? = null
    private var user: FirebaseUser? = null

//    private var firestoreDataBase : FirebaseFirestore? = FirebaseFirestore.getInstance()
//    private var firebaseUserSingleton : FirebaseUserSingleton = FirebaseUserSingleton
//    private val repository = LocationRepository(firestoreDataBase,firebaseUserSingleton)

   @Inject
   lateinit var  repository : LocationRepository



    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.let {
                getPosition(locationResult)
            }
            addData(locationMap)
            Log.d(TAG, "TIME:" + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(Date()))
        }
    }

    override fun onCreate() {
    DaggerApplicationComponent
            .builder()
            .build()
            .inject(this)

        super.onCreate()
        isGpsEnabled()
        user = FirebaseUserSingleton.getFirebaseAuth()?.currentUser
        user?.let {
            FirebaseUserSingleton.setUserId(user!!.uid)
            userId = FirebaseUserSingleton.getUserId()
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this, SendLocationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification =
                NotificationCompat.Builder(this, CHANNEL_ID)
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
            val serviceChannel = NotificationChannel(
                    CHANNEL_ID, "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW)
            serviceChannel.enableVibration(false)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

//    TODO private?
    fun getPosition(locationResult: LocationResult) {
        val mLastLocation: Location = locationResult.lastLocation
        val dateFormat: DateFormat = SimpleDateFormat(datePattern)
//    TODO run

        time = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(Date())

    with(locationMap){
        put(LATITUDE_FIELD, mLastLocation.latitude)
        put(LONGITUDE_FIELD, mLastLocation.longitude)
        put(DATE_FIELD, dateFormat.format(Date()))
        put(TIME_FIELD, time.toString())
    }

        Log.d("LOCATION", time.toString())
    }
    //    TODO private?
    fun addData(locationMap: MutableMap<String, Any>) {
        repository
                .addDataToRepository(locationMap)
                ?.addOnSuccessListener { documentReference: DocumentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)
                }
                ?.addOnFailureListener { e: Exception? ->
                    Log.w(TAG, "Error adding document", e)
                }
    }

    private fun getLocation() {
        mFusedLocationClient?.let { requestNewLocationData(it) }
    }

    //    TODO private?
    @SuppressLint("MissingPermission")
    fun requestNewLocationData(mFusedLocationClient: FusedLocationProviderClient) {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

//        Set in final commit

//        with(mLocationRequest){
//            smallestDisplacement = 60F
//            interval = 60000*10
//            fastestInterval = 60000*10
//        }

        //        For testing

        with(mLocationRequest){
            interval = 30000
            fastestInterval = 30000
        }

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

//    TODO deprecated
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
                .isConnected
    }

    private fun isGpsEnabled(): Boolean {
        val locationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}