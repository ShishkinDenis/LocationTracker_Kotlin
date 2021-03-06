package com.shishkindenis.parentmodule.maps.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
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
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MapsActivity : DaggerAppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var mapsViewModel: MapsViewModel

    private val LONGITUDE_FIELD = "Longitude"
    private val LATITUDE_FIELD = "Latitude"
    private val TIME_FIELD = "Time"
    private var polylineOptions: PolylineOptions? = null
    private lateinit var binding: ActivityMapsBinding
    private lateinit var map: GoogleMap
    private var longitude: Double? = null
    private var latitude: Double? = null
    private var time: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        observeMapsViewModel()
        polylineOptions = PolylineOptions()
        mapsViewModel.readLocation()
        initMapsFragment()
    }

    private fun observeMapsViewModel() {
        with(mapsViewModel) {
            backToCalendarActivityWithCancelledResult.observe(this@MapsActivity, Observer {
                backToCalendarActivityWithCancelledResult()
            })
            backToCalendarActivityWithOkResult.observe(this@MapsActivity, Observer {
                backToCalendarActivityWithOkResult()
            })
            getPosition.observe(this@MapsActivity, Observer {
                getPosition(it as QueryDocumentSnapshot?)
            })
            setTrack.observe(this@MapsActivity, Observer {
                setTrack()
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        with(map) {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isCompassEnabled = true
            isMyLocationEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return
        }
    }

    private fun setTrack() {
        val someplace = LatLng(latitude!!, longitude!!)
        with(map) {
            addMarker(MarkerOptions().position(someplace).title(time))
            moveCamera(CameraUpdateFactory.newLatLng(someplace))
            addPolyline(polylineOptions
                    ?.color(Color.BLUE)
                    ?.width(3f)
                    ?.add(LatLng(latitude!!, longitude!!)))
        }
    }

    private fun getPosition(document: QueryDocumentSnapshot?) = with(document) {
        longitude = this?.get(LONGITUDE_FIELD) as Double
        latitude = get(LATITUDE_FIELD) as Double
        time = get(TIME_FIELD) as String
    }

    private fun backToCalendarActivityWithCancelledResult() {
        setResult(Activity.RESULT_CANCELED, null)
        finish()
    }

    private fun initMapsFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun backToCalendarActivityWithOkResult() {
        setResult(Activity.RESULT_OK, null)
    }
}