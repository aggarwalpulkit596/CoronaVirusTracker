package com.puldroid.coronavirustracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.puldroid.coronavirustracker.R
import com.puldroid.coronavirustracker.network.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        GlobalScope.launch() {

            val regionRes = withContext(Dispatchers.IO) { Client.api.getCases() }

            regionRes.body()?.features?.distinctBy {
                it.attributes?.countryRegion
            }?.forEach {
                GlobalScope.launch(Dispatchers.Main) {
                    val marker = it.attributes?.long?.let { it1 ->
                        it.attributes.lat?.let { it2 ->
                            LatLng(
                                it2, it1
                            )
                        }
                    }
                    marker?.let { it1 ->
                        mMap.addMarker(
                        MarkerOptions().position(it1)
                            .title("${it.attributes.countryRegion} C${it.attributes.confirmed} D${it.attributes.deaths} R${it.attributes.recovered} "))
                    }
                    mMap.uiSettings.isMapToolbarEnabled = false
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.0,77.0),4f))
                }
            }

        }
    }

}
