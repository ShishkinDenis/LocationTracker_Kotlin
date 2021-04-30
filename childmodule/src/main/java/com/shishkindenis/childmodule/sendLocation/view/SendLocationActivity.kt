package com.shishkindenis.childmodule.sendLocation.view

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
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shishkindenis.childmodule.R
import com.shishkindenis.childmodule.databinding.ActivitySendLocationBinding
import com.shishkindenis.childmodule.sendLocation.services.ForegroundService
import com.shishkindenis.childmodule.sendLocation.viewModel.SendLocationViewModel
import com.shishkindenis.childmodule.sendLocation.workers.LocationWorker

class SendLocationActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, SendLocationActivity::class.java)
    }

    private val sendLocationViewModel: SendLocationViewModel by viewModels()

    private val PERMISSION_ID = 1
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var networkStateOn = false
    private var gpsStateOn = false
    private lateinit var binding: ActivitySendLocationBinding

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
                            binding.tvGpsStatus.setText(R.string.gps_status_on)
                            binding.tvProgress.setText(R.string.location_determination_in_progress)
                            gpsStateOn = true
                        } else {
                            binding.tvGpsStatus.setText(R.string.gps_status_off)
                            gpsStateOn = false
                            if (!isNetworkConnected()) {
                                stopService()
                                showAlertDialog()
                                binding.tvProgress.setText(R.string.location_determination_off)
                            }
                        }
                    }
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendLocationBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkIgnoringBatteryOptimizationsPermission()
        registerLocationSwitchStateReceiver()
        initConnectivityCallback()
        changeGpsStateIndicator()
        changeNetworkStateIndicator()
        startLocationDetermination()

        sendLocationViewModel.toast.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })
        sendLocationViewModel.service.observe(this, Observer {
            stopService()
        })
    }

    private fun checkIgnoringBatteryOptimizationsPermission() {
        if (!isIgnoringBatteryOptimizationsPermissionGiven()) {
            requestIgnoringBatteryOptimizations()
        }
    }

    private fun startLocationDetermination() {
        if (!gpsStateOn && !isNetworkConnected()) {
            showAlertDialog()
        } else {
            sendLocationToFirebase()
        }
    }

    private fun registerLocationSwitchStateReceiver() {
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        this.registerReceiver(locationSwitchStateReceiver, filter)
    }

    private fun initConnectivityCallback() {
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

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    private fun showLocationSourceSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun showNetworkSettings() {
        val intent = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
        startActivity(intent)
    }

    fun showAlertDialog() {
        AlertDialog.Builder(this)
                .setMessage(R.string.location_determination_is_impossible)
                .setPositiveButton(R.string.network) { dialog, which -> showNetworkSettings() }
                .setNegativeButton(R.string.gps) { dialog, which -> showLocationSourceSettings() }
                .show()
    }

    private fun requestIgnoringBatteryOptimizations() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:$packageName"))
        startActivity(intent)
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isIgnoringBatteryOptimizationsPermissionGiven(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
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

    private fun changeGpsStateIndicator() {
        val locationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        gpsStateOn = if (isGpsEnabled) {
            binding.tvGpsStatus.setText(R.string.gps_status_on)
            binding.tvProgress.setText(R.string.location_determination_in_progress)
            true
        } else {
            binding.tvGpsStatus.setText(R.string.gps_status_off)
            false
        }
    }

    private fun changeNetworkStateIndicator() {
        if (isNetworkConnected()) {
            binding.tvNetworkStatus.setText(R.string.network_status_on)
            binding.tvProgress.setText(R.string.location_determination_in_progress)
        } else {
            binding.tvNetworkStatus.setText(R.string.network_status_off)
        }
    }

    private fun sendLocationToFirebase() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                startLocationWorker()
            } else {
                showToast(R.string.turn_on_determination_of_location)
                showLocationSourceSettings()
            }
        } else {
            requestPermissions()
        }
    }

    private fun startLocationWorker() {
        val myWorkRequest =
                OneTimeWorkRequest.Builder(LocationWorker::class.java).build()
        WorkManager.getInstance(applicationContext).enqueue(myWorkRequest)
    }

    fun stopService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        stopService(serviceIntent)
    }

    private fun showToast(toastMessage: Int) {
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
    }

    inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
//            TODO
            val connected =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            networkStateOn = true
            startService()
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                binding.tvNetworkStatus.setText(R.string.network_status_on)
                binding.tvProgress.setText(R.string.location_determination_in_progress)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            networkStateOn = false
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                binding.tvNetworkStatus.setText(R.string.network_status_off)
                if (!gpsStateOn) {
                    stopService()
                    showAlertDialog()
                    binding.tvProgress.setText(R.string.location_determination_off)
                }
            }
        }
    }
}