package com.example.rideapp.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.rideapp.viewmodel.RideViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import java.util.*
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.rideapp.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory


@Composable
fun MapScreen(
    navController: NavController,
    viewModel: RideViewModel
) {

    val context = LocalContext.current

    var locationText by remember { mutableStateOf("Fetching current location...") }
    var currentLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState()


    LaunchedEffect(Unit) {

        val fused = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fused.lastLocation.addOnSuccessListener { loc ->
                loc?.let {

                    val latLng = LatLng(it.latitude, it.longitude)
                    currentLatLng = latLng

                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(latLng, 16f)

                    val geo = Geocoder(context, Locale.getDefault())
                    val address = geo.getFromLocation(it.latitude, it.longitude, 1)

                    if (!address.isNullOrEmpty()) {
                        locationText = address[0].getAddressLine(0)
                        viewModel.pickup.value = locationText
                    }
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        if (currentLatLng != null) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            )
        }

        Image(
            painter = painterResource(R.drawable.redpin3),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )

        // 🔵 TOP pickup bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .align(Alignment.TopCenter)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Adjust,
                        contentDescription = null,
                        tint = Color(0xFF3B87E3),
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(text = locationText, color = Color.Black)
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp, end = 16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            Card(
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .size(55.dp)
                    .shadow(10.dp, RoundedCornerShape(50))
                    .clickable {
                        currentLatLng?.let {
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(it, 16f)
                        }
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.GpsFixed,
                        contentDescription = null,
                        tint = Color(0xFF3B87E3),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(10.dp, RoundedCornerShape(16.dp))
                    .clickable { navController.navigate("drop") },
                shape = RoundedCornerShape(16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "Select Destination",
                        color = Color.Gray
                    )
                }
            }
        }
    }
}