package com.example.easypark_api_front.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.easypark_api_front.R
import com.example.easypark_api_front.Routes

import com.example.easypark_api_front.viewModal
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import org.osmdroid.views.MapView
import com.google.android.gms.maps.model.LatLng
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun displayGeoCard(navController: NavController,viewModal: viewModal){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }




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
                            modifier=Modifier.fillMaxWidth().padding(20.dp)

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

                        //Loader(loading = viewModal.loading.value)
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
                                        modifier=Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween) {
                                        Image(painter = painterResource(id = R.drawable.parking_sign), contentDescription = "sort",modifier= Modifier
                                            .padding(top = 8.dp, end = 10.dp)
                                            .size(45.dp))
                                        Column(
                                            modifier=Modifier.padding(2.dp),
                                        ) {
                                            Text(text = parking.nom,style = TextStyle(
                                                color = Color(0xFF192342),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp))

                                            Text(text = parking.address,style = TextStyle(
                                                color = Color(0xFF677191),
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp))
                                        }

                                        Column (
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.End,


                                        ){
                                            Text(text = (parking.available_slots).toString()+"slots",style = TextStyle(
                                                color = Color(0xFF192342),
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 14.sp)
                                               // modifier=Modifier.padding(start=11.dp,top = 17.dp)
                                         )
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

                        MapViewContainer() // Ajoutez ceci pour afficher la carte

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(painter = painterResource(id = R.drawable.car_not_selected), contentDescription = null,
                                modifier = Modifier
                                    .background(Color.White)
                                    .size(50.dp)
                            )
                            Image(painter = painterResource(id = R.drawable.bus), contentDescription = null,modifier = Modifier
                                .background(Color.White)
                                .size(50.dp)
                            )
                            Image(painter = painterResource(id = R.drawable.bike), contentDescription = null,modifier = Modifier
                                .background(Color.White)
                                .size(50.dp)
                            )
                        }
                    }






                }







            }
        }
    )

    LaunchedEffect(Unit) {
        viewModal.getAllParkings()

    }

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
@Composable
fun MapViewContainer() {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { MapView(context) },
        update = { mapView ->
            mapView.setTileSource(TileSourceFactory.MAPNIK)

            // Définir les coordonnées pour l'Algérie
            val geoPoint = GeoPoint(28.0, 2.0) // Coordonnées approximatives pour l'Algérie[^1^][2]

            mapView.controller.setCenter(geoPoint)
            mapView.controller.setZoom(6.0) // Ajustez le niveau de zoom selon vos besoins
        }
    )
}
