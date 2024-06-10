package com.example.easypark_api_front.screens
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.easypark_api_front.R
import com.example.easypark_api_front.Routes
import com.example.easypark_api_front.ui.theme.LineType
import com.example.easypark_api_front.viewModal
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


@Composable
fun MyMap(
    context: Context,
    latLng: LatLng,
    parkingLocations: List<Pair<LatLng, Int>>,
    mapProperties: MapProperties = MapProperties(),
    navController: NavController,
    viewModal: viewModal
) {
    val latlangList = remember {
        mutableStateListOf(latLng)
    }
    var selectedParking by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }


    // Dans le composant MyMap
    LaunchedEffect(viewModal.selectedParkingForNavigation.value) {
        if (viewModal.selectedParkingForNavigation.value != null) {
            // Mettez à jour la position de la caméra pour pointer vers le parking sélectionné
            cameraPositionState.position = CameraPosition.fromLatLngZoom(viewModal.selectedParkingForNavigation.value!!, 15f)
        }
    }





    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapClick = {

            }
        ) {
            latlangList.toList().forEach {
                Marker(
                    state = MarkerState(position = it),
                    title = "Location",
                    snippet = "Marker in current location",
                    icon = bitmapDescriptor(context, R.drawable.car_marker)
                )
            }

            parkingLocations.forEach { (latLng, parkingId) ->
                Marker(
                    state = MarkerState(position = latLng),
                    title = "Parking Location",
                    snippet = "Marker for parking location with ID: $parkingId",
                    icon = bitmapDescriptor(context, R.drawable.parking_sign),
                    onClick = {
                        //selectedParking = latLng
                        navController.navigate(
                            Routes.ParkingDetail.getUrlWithId(
                                parkingId.toString()
                            )
                        )
                        true
                    }
                )
            }

            if (selectedParking != null) {
                Polyline(
                    points = listOf(latLng, selectedParking!!),
                    color = Color.Yellow,
                    width = 5f
                )
                val distance = calculateDistance(latLng, selectedParking!!)
                Text(
                    text = "Distance: $distance km",
                    modifier = Modifier
                        .background(Color.White)
                        .padding(8.dp),
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
















    }





}







fun bitmapDescriptor(
    context: Context,
    resId: Int
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, resId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun calculateDistance(start: LatLng, end: LatLng): Double {
    val radius = 6371 // radius of the earth in km
    val latDiff = Math.toRadians(end.latitude - start.latitude)
    val lonDiff = Math.toRadians(end.longitude - start.longitude)
    val a = sin(latDiff / 2).pow(2.0) + cos(Math.toRadians(start.latitude)) * cos(Math.toRadians(end.latitude)) * sin(lonDiff / 2).pow(2.0)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return radius * c
}