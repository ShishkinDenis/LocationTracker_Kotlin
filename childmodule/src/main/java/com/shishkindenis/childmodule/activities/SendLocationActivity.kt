package com.shishkindenis.childmodule.activities

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shishkindenis.childmodule.R
import com.shishkindenis.childmodule.databinding.ActivitySendLocationBinding
import com.shishkindenis.childmodule.services.ForegroundService
import com.shishkindenis.childmodule.viewModels.SendLocationViewModel

class SendLocationActivity : AppCompatActivity() {

    val sendLocationViewModel: SendLocationViewModel by viewModels()

    private val PERMISSION_ID = 1
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var networkStatusOn = false
    private var gpsStatusOn = false
    private var binding: ActivitySendLocationBinding? = null

    private val locationSwitchStateReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.action) {
                    val locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val isGpsEnabled =
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    if (isGpsEnabled) {
                        startService()
                        binding?.tvGpsStatus?.setText(R.string.gps_status_on)
                        binding?.tvProgress?.setText(R.string.location_determination_in_progress)
                        gpsStatusOn = true
                    } else {
                        binding?.tvGpsStatus?.setText(R.string.gps_status_off)
                        gpsStatusOn = false
                        if (!isNetworkConnected()) {
                            stopService()
                            showAlertDialog()
                            binding?.tvProgress?.setText(R.string.location_determination_off)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendLocationBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        setSupportActionBar(binding!!.toolbar)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkIgnoringBatteryOptimizationsPermission()
        registerLocationSwitchStateReceiver()
        initConnectivityCallback()
        setGpsStatus()
        setNetworkStatus()
        startLocationDetermination()

        sendLocationViewModel.toast.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })
        sendLocationViewModel.service.observe(this, Observer {
            stopService()
        })

    }

    fun checkIgnoringBatteryOptimizationsPermission() {
        if (!isIgnoringBatteryOptimizationsPermissionGiven()) {
            requestIgnoringBatteryOptimizations()
        }
    }

    fun startLocationDetermination() {
        if (!gpsStatusOn && !isNetworkConnected()) {
            showAlertDialog()
        } else {
            sendLocationToFirebase()
        }
    }

    fun registerLocationSwitchStateReceiver() {
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        this.registerReceiver(locationSwitchStateReceiver, filter)
    }

    fun initConnectivityCallback() {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(ConnectivityCallback())
    }

    fun startService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        sendLocationViewModel.signOut()
        setResult(RESULT_OK, null)
        finish()
        return super.onOptionsItemSelected(item)
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    fun showLocationSourceSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    fun showNetworkSettings() {
        val intent = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
        startActivity(intent)
    }

    fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.location_determination_is_impossible)
            .setPositiveButton(R.string.network, { dialog, which -> showNetworkSettings() })
            .setNegativeButton(R.string.gps, { dialog, which -> showLocationSourceSettings() })
            .show()
    }

    fun requestIgnoringBatteryOptimizations() {
        val intent = Intent(
            Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isIgnoringBatteryOptimizationsPermissionGiven(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(): Boolean {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    fun setGpsStatus() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        gpsStatusOn = if (isGpsEnabled) {
            binding?.tvGpsStatus?.setText(R.string.gps_status_on)
            binding?.tvProgress?.setText(R.string.location_determination_in_progress)
            true
        } else {
            binding?.tvGpsStatus?.setText(R.string.gps_status_off)
            false
        }
    }

    fun setNetworkStatus() {
        if (isNetworkConnected()) {
            binding?.tvNetworkStatus?.setText(R.string.network_status_on)
            binding?.tvProgress?.setText(R.string.location_determination_in_progress)
        } else {
            binding?.tvNetworkStatus?.setText(R.string.network_status_off)
        }
    }

    fun sendLocationToFirebase() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                sendLocationViewModel.startLocationWorker()
            } else {
                showToast(R.string.turn_on_determination_of_location)
                showLocationSourceSettings()
            }
        } else {
            requestPermissions()
        }
    }

    fun stopService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        stopService(serviceIntent)
    }

    fun showToast(toastMessage: Int) {
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
    }

    inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val connected =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            networkStatusOn = true
            startService()
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                binding?.tvNetworkStatus?.setText(R.string.network_status_on)
                binding?.tvProgress?.setText(R.string.location_determination_in_progress)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            networkStatusOn = false
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                binding?.tvNetworkStatus?.setText(R.string.network_status_off)
                if (!gpsStatusOn) {
                    stopService()
                    showAlertDialog()
                    binding?.tvProgress?.setText(R.string.location_determination_off)
                }
            }
        }
    }
}