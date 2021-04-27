package com.shishkindenis.parentmodule.maps.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.shishkindenis.parentmodule.R
import com.shishkindenis.parentmodule.databinding.ActivityMapsBinding
import com.shishkindenis.parentmodule.maps.viewModel.MapsViewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    val mapsViewModel: MapsViewModel by viewModels()

    private val LONGITUDE_FIELD = "Longitude"
    private val LATITUDE_FIELD = "Latitude"
    private val TIME_FIELD = "Time"
    private var polylineOptions: PolylineOptions? = null
    private var binding: ActivityMapsBinding? = null
    private var map: GoogleMap? = null
    private var longitude: Double? = null
    private var latitude: Double? = null
    private var time: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        mapsViewModel.backToCalendarActivityWithCancelledResult.observe(this, Observer {
            backToCalendarActivityWithCancelledResult()
        })
        mapsViewModel.backToCalendarActivityWithOkResult.observe(this, Observer {
            backToCalendarActivityWithOkResult()
        })
        mapsViewModel.getPosition.observe(this, Observer {
            getPosition(it as QueryDocumentSnapshot?)
        })
        mapsViewModel.setTrack.observe(this, Observer {
            setTrack()
        })

        polylineOptions = PolylineOptions()
        mapsViewModel.readLocation()
        initMapsFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map!!.uiSettings.isZoomControlsEnabled = true
        map!!.uiSettings.isCompassEnabled = true
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        map!!.isMyLocationEnabled = true
        map!!.uiSettings.isMyLocationButtonEnabled = true
    }

    fun setTrack() {
        val someplace = LatLng(latitude!!, longitude!!)
        map!!.addMarker(MarkerOptions().position(someplace).title(time))
        map!!.moveCamera(CameraUpdateFactory.newLatLng(someplace))
        map!!.addPolyline(
            polylineOptions
                ?.color(Color.BLUE)
                ?.width(3f)
                ?.add(LatLng(latitude!!, longitude!!))
        )
    }

    fun getPosition(document: QueryDocumentSnapshot?) {
        longitude = document?.get(LONGITUDE_FIELD) as Double
        latitude = document.get(LATITUDE_FIELD) as Double
        time = document.get(TIME_FIELD) as String
    }

    fun backToCalendarActivityWithCancelledResult() {
        setResult(Activity.RESULT_CANCELED, null)
        finish()
    }

    fun initMapsFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    fun backToCalendarActivityWithOkResult() {
        setResult(Activity.RESULT_OK, null)
    }
}