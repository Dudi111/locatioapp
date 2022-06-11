package com.example.locationapp.frag

import android.Manifest
import android.app.Service
import android.app.Service.START_NOT_STICKY
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.LocationListener
import java.util.*
import kotlin.collections.HashMap


class locationTrackingService : Service(), LocationListener {
    private var context: Context? = null
    var isGPSEnable = false
    var isNetworkEnable = false
    var latitude = 0.0
    var longitude = 0.0
    var locationManager: LocationManager? = null
    var location: Location? = null
    private val mHandler: Handler = Handler()
    private var mTimer: Timer? = null
    var notify_interval: Long = 30000
    var track_lat = 0.0
    var track_lng = 0.0
    var intent: Intent? = null

    companion object {
        private const val TAG = "BookingTrackingService"
        var str_receiver = "servicetutorial.service.receiver"
    }



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mTimer = Timer()
        mTimer!!.schedule(TimerTaskToGetLocation(), 5, notify_interval)
        intent = Intent(str_receiver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        context = this
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy <<")
        if (mTimer != null) {
            mTimer!!.cancel()
        }
    }

    private fun trackLocation() {
        Log.e(TAG, "trackLocation")
        val TAG_TRACK_LOCATION = "trackLocation"
        val params: MutableMap<String, String> = HashMap()
        params["latitude"] = "" + track_lat
        params["longitude"] = "" + track_lng
        Log.e(TAG, "param_track_location >> $params")
        stopSelf()
        mTimer!!.cancel()
    }

    override fun onLocationChanged(location: Location?) {}
    fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    fun onProviderEnabled(provider: String?) {}
    fun onProviderDisabled(provider: String?) {}

    /** */
    private fun fn_getlocation() {
        locationManager = application.applicationContext.getSystemService(
            LOCATION_SERVICE
        ) as LocationManager?
        isGPSEnable = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnable = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isGPSEnable && !isNetworkEnable) {
            Log.e(TAG, "CAN'T GET LOCATION")
            stopSelf()
        } else {
            if (isNetworkEnable) {
                location = null
//                locationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    1000,
//                    0,
//                    this
//                )
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    location =
                        locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (location != null) {
                        Log.e(
                            TAG,
                            """
                                isNetworkEnable latitude${location!!.getLatitude().toString()}
                                longitude${location!!.getLongitude().toString()}
                                """.trimIndent()
                        )
                        latitude = location!!.getLatitude()
                        longitude = location!!.getLongitude()
                        intent!!.putExtra("lat", latitude)
                        intent!!.putExtra("lng", longitude)
                        sendBroadcast(intent)
                        track_lat = latitude
                        track_lng = longitude

                    }
                }
            }
            if (isGPSEnable) {
                location = null
               // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this)
                if (locationManager != null) {
                    location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        Log.e(
                            TAG,
                            """
                                isGPSEnable latitude${location!!.getLatitude().toString()}
                                longitude${location!!.getLongitude().toString()}
                                """.trimIndent()
                        )
                        latitude = location!!.getLatitude()
                        longitude = location!!.getLongitude()
                        track_lat = latitude
                        track_lng = longitude
                        //                        fn_update(location);
                    }
                }
            }
            Log.e(TAG, "START SERVICE")
            trackLocation()
        }
    }

    private inner class TimerTaskToGetLocation : TimerTask() {
        override fun run() {
            mHandler.post(Runnable { fn_getlocation() })
        }
    }

}