package com.example.easypark_api_front.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.navigation.NavController
import com.example.easypark_api_front.R
import com.example.easypark_api_front.Routes
import com.example.easypark_api_front.viewModal
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import org.jetbrains.annotations.Contract
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import androidx.compose.runtime.remember
import com.example.easypark_api_front.MainActivity
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun displayGeoCard(navController: NavController,viewModal: viewModal) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    val success by viewModal.success

    LaunchedEffect(Unit) {
        viewModal.getAllParkings()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
            Column(modifier=Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
                )
                {
                Row (modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                    //horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(modifier= Modifier
                        .clip(CircleShape)

                        .padding(start = 20.dp)
                        .shadow(elevation = 4.dp)
                        .size(55.dp),painter = painterResource(id = R.drawable.avatar),
                        contentDescription = null)
                    Column (modifier= Modifier


                        .padding(start=15.dp)){
                        Text(text = "Ahmed Bouroubaz",style = TextStyle(
                            color = Color(0xFF192342),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp))

                        Text(text = "Ageria",style = TextStyle(
                            color = Color(0xFF677191),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp))
                    }
                    Image(modifier= Modifier
                        .clip(CircleShape)

                        .padding(start = 35.dp, end = 10.dp)
                        .shadow(elevation = 4.dp)
                        .size(30.dp),painter = painterResource(id = R.drawable.notification),
                        contentDescription = null)
                }
                Text(text = "My Reservations",style = TextStyle(
                    color = Color(0xFF192342),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp),
                    modifier= Modifier
                        .padding(
                            start = 10.dp, top = 90.dp
                        )
                        .clickable {
                            navController.navigate(
                                Routes.pmyReservations.route
                            )
                        }
                        )


                Text(text = "History",style = TextStyle(
                    color = Color(0xFF192342),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp),
                    modifier= Modifier.padding(start=10.dp,bottom=90.dp,top=20.dp)
                       )

                Column (
                    modifier= Modifier


                        .padding(start=10.dp,top=130.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top){
                    Row(modifier=Modifier.fillMaxWidth(),

                        ) {
                        Image(modifier= Modifier


                            .shadow(elevation = 4.dp)
                            .size(20.dp),painter = painterResource(id = R.drawable.logout),
                            contentDescription = null)
                        Button(onClick = {
                            viewModal.logoutUser(context)
                        }) {
                            Text(text = "Logout",style = TextStyle(
                                color = Color(0xFF192342),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp),
                                modifier= Modifier.padding(start=10.dp)
                            )
                        }

                    }
                }
            }


            }
        },
        content = {
            var query by remember { mutableStateOf("") }
            var active by remember { mutableStateOf(false) }
            BottomSheetScaffold(
                sheetShadowElevation = 45.dp,
                sheetSwipeEnabled = true,
                sheetShape = RoundedCornerShape(20.dp),
                sheetContainerColor = Color.White,
                sheetContent ={
                    Column(
                        modifier=Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)) {
                                        append("Where to park?")
                                    }
                                })
                            },
                            modifier= Modifier
                                .padding(start = 25.dp)
                                .width(336.dp)
                                .padding(top = 50.dp)
                                .shadow(elevation = 8.dp),
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor =  Color.White,
                                unfocusedContainerColor = Color.White,
                                cursorColor = Color.White,

                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                        )


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier= Modifier
                                .fillMaxWidth()
                                .padding(20.dp)

                        ) {
                            Text(text = "RESULT",style = TextStyle(
                                color = Color(0xFF677191),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,

                                ),
                                modifier=Modifier.padding(end=220.dp)

                            )


                            Column (

                                horizontalAlignment = Alignment.End,
                                modifier=Modifier.padding(4.dp)

                            ){
                            Image(painter = painterResource(id = R.drawable.sort), contentDescription = "sort",modifier= Modifier
                                .padding(start = 20.dp)
                                .size(20.dp))}

                        }

                        if (viewModal.error.value) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Error while fetching parkings")
                            }
                        } else {
                            LazyColumn {

                                items(viewModal.data.value.filter {
                                    it.nom.contains(searchQuery, ignoreCase = true) ||
                                            it.address.contains(searchQuery, ignoreCase = true)
                                }) { parking ->
                                    Column(
                                        modifier = Modifier
                                            .clickable {
                                                var parkingId = parking.id
                                                navController.navigate(
                                                    Routes.ParkingDetail.getUrlWithId(
                                                        parkingId.toString()
                                                    )
                                                )
                                            }
                                            .fillMaxWidth()
                                            .padding(top = 20.dp, start = 5.dp)

                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.parking_sign),
                                                contentDescription = "sort",
                                                modifier = Modifier
                                                    .padding(top = 8.dp, end = 10.dp)
                                                    .size(45.dp)
                                            )
                                            Column(
                                                modifier = Modifier.padding(2.dp),
                                            ) {
                                                Text(
                                                    text = parking.nom, style = TextStyle(
                                                        color = Color(0xFF192342),
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 16.sp
                                                    )
                                                )

                                                Text(
                                                    text = parking.address, style = TextStyle(
                                                        color = Color(0xFF677191),
                                                        fontWeight = FontWeight.Medium,
                                                        fontSize = 14.sp
                                                    )
                                                )
                                            }

                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.End,


                                                ) {
                                                Text(
                                                    text = (parking.available_slots).toString() + "slots",
                                                    style = TextStyle(
                                                        color = Color(0xFF192342),
                                                        fontWeight = FontWeight.SemiBold,
                                                        fontSize = 14.sp
                                                    )
                                                    // modifier=Modifier.padding(start=11.dp,top = 17.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

            ) {
                Column(modifier= Modifier
                    .fillMaxSize() // Fills the entire available space

                    .background(Color.White),


                    horizontalAlignment = Alignment.Start ,
                    verticalArrangement = Arrangement.Top

                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 70.dp)
                    ) {
                        Image(modifier= Modifier
                            .padding(end = 90.dp)
                            .size(35.dp)
                            .clickable {
                                scope.launch { drawerState.open() }
                            }
                            ,painter = painterResource(id = R.drawable.menu_icon), contentDescription = null)

                        Image(modifier= Modifier
                            .clip(CircleShape)

                            .padding(start = 90.dp)
                            .shadow(elevation = 4.dp)
                            .size(35.dp),painter = painterResource(id = R.drawable.avatar),
                            contentDescription = null)




                    }

                    TextField(
                        value = "",

                        onValueChange = {},
                        placeholder = {
                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)) {
                                    append("Where to park?")
                                }
                            })
                        },
                        modifier= Modifier
                            .padding(start = 25.dp)
                            .width(336.dp)
                            .padding(top = 50.dp)
                            .shadow(elevation = 8.dp),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor =  Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.White,

                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp),
                    )


                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {







                        // Demandez la permission de localisation
                        val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        LaunchedEffect(permissionState) {
                            permissionState.launchPermissionRequest()
                        }

                        val scope = rememberCoroutineScope()
                        var location by remember { mutableStateOf(LatLng(0.0, 0.0)) } // Initialisez avec une valeur par défaut
                        var showMap by remember { mutableStateOf(false) }

                        // Vérifiez si la permission a été accordée
                        when (permissionState.status) {
                            PermissionStatus.Granted -> {
                                LaunchedEffect(key1 = Unit) {
                                    scope.launch {
                                        (context as MainActivity).getCurrentLocation(
                                            onGetCurrentLocationSuccess = { latLng ->
                                                location = LatLng(latLng.first, latLng.second)
                                                showMap = true
                                            },
                                            onGetCurrentLocationFailed = { exception ->
                                                // Gérez l'erreur ici
                                            }
                                        )
                                    }
                                }
                            }


                            else -> {}
                        }

                        val parkingLocations = viewModal.data.value.map { parking ->
                            val coordinates = parking.localization.split(",").map { it.toDouble() }
                            LatLng(coordinates[0], coordinates[1])
                        }

                        if (showMap) {
                            MyMap(
                                context = context,
                                latLng = location,
                               parkingLocations =parkingLocations
                                )
                        } else {
                            Column (
                                modifier=Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center

                            ){
                                Text(text = "Loading Map...")
                            }

                        }

                        var carSelected by remember { mutableStateOf(false) }
                        var busSelected by remember { mutableStateOf(false) }
                        var motoSelected by remember { mutableStateOf(false) }
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Column (
                                modifier= Modifier
                                    .size(55.dp)
                                    .background(Color.White)
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(10.dp),
                                        // Réduisez l'opacité en définissant votre propre couleur d'ombre
                                        // Ici, l'opacité est réglée sur 50%
                                        ambientColor = Color(0f, 0f, 0f, 0.5f)
                                    )
                                    .clip(RoundedCornerShape(15.dp)),

                            ){
                                Image(painter =if(carSelected){painterResource(id = R.drawable.car_selected)}else painterResource(id = R.drawable.car_not_selected), contentDescription = null,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .size(50.dp)
                                        .padding(4.dp)
                                        .clickable {
                                            if (!carSelected) {
                                                carSelected = true
                                                motoSelected = false
                                                busSelected = false
                                            }
                                        }
                                )
                            }


                            Column(
                            modifier= Modifier
                                .size(55.dp)
                                .background(Color.White)
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(10.dp),
                                    // Réduisez l'opacité en définissant votre propre couleur d'ombre
                                    // Ici, l'opacité est réglée sur 50%
                                    ambientColor = Color(0f, 0f, 0f, 0.5f)
                                )
                                .clip(RoundedCornerShape(15.dp)),

                            ){
                            Image(painter =if(busSelected){painterResource(id = R.drawable.bus_selected)}else
                            painterResource(id = R.drawable.bus), contentDescription = null,modifier = Modifier
                                .background(Color.White)
                                .size(50.dp)
                                .padding(4.dp)
                                .clickable {
                                    if (!busSelected) {
                                        busSelected = true
                                        carSelected = false
                                        motoSelected = false
                                    }
                                }
                            )}

                            Column(
                                modifier= Modifier
                                    .size(55.dp)
                                    .background(Color.White)
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(10.dp),
                                        // Réduisez l'opacité en définissant votre propre couleur d'ombre
                                        // Ici, l'opacité est réglée sur 50%
                                        ambientColor = Color(0f, 0f, 0f, 0.5f)
                                    )
                                    .clip(RoundedCornerShape(15.dp)),

                                ) {
                                Image(
                                    painter = if(motoSelected){painterResource(id = R.drawable.bike_selected)}else painterResource(id = R.drawable.bike),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .size(50.dp)
                                        .clickable {
                                            if (!motoSelected) {
                                                motoSelected = true
                                                carSelected = false
                                                busSelected = false
                                            }
                                        }
                                )

                            }


                        }


                    }//fin du box






                }







            }
        }
    )


}

@Composable
fun Loader(loading:Boolean){
    if (loading){
        CircularProgressIndicator()
    }
}
@Composable
fun DisplayToast(error:Boolean){
    val context = LocalContext.current
    if(error){
        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
    }

}


