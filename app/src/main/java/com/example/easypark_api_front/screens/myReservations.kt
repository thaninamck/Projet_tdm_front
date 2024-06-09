package com.example.easypark_api_front.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavController
import com.example.easypark_api_front.R
import com.example.easypark_api_front.Routes
import com.example.easypark_api_front.viewModal

@Composable
fun myReservations(navController: NavController, viewModal: viewModal){
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModal.getMyReservations(context)
    }

    val reservations = viewModal.reservation_data.value ?: emptyList()
    Column(modifier=Modifier.fillMaxSize()) {

        Row (
            modifier= Modifier
                .padding(top=30.dp, start = 10.dp),

        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retour",
                modifier= Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Text(text = "My Reservations",style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),modifier= Modifier.padding(start = 50.dp, top = 10.dp, bottom = 15.dp)
            )

        }

        TextField(
            value = "",

            onValueChange = {},
            placeholder = {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)) {
                        append("search?")
                    }
                })
            },
            modifier= Modifier
                .padding(start = 45.dp)
                .width(336.dp)
                .height(100.dp)
                .padding(top = 50.dp)
                .shadow(elevation = 8.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor =  Color(0xFFF4F4F4),
                unfocusedContainerColor = Color(0xFFF4F4F4),
                cursorColor = Color.White,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp),
        )

        LazyColumn {
            items(reservations) { reservation ->
                Column(

                    modifier = Modifier
                        .clickable {
                            navController.navigate(Routes.ParkingDetail.getUrlWithId(reservation.parking_id.toString()))
                        }
                        .padding(start=16.dp,top=30.dp)) {
                    Row (modifier=Modifier.fillMaxWidth()){
                        Image(painter = painterResource(id = R.drawable.parking_sign), contentDescription = "sort",modifier= Modifier
                            .padding(15.dp)
                            .size(30.dp))
                        Column (modifier= Modifier
                            .padding(10.dp)){
                            Text(text =  reservation.parking_name,style = TextStyle(
                                color = Color(0xFF192342),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = reservation.date,style = TextStyle(
                                    color = Color(0xFF677191),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp)
                                )

                                Text(text = "reserved slot: " + reservation.parking_slot,style = TextStyle(
                                    color = Color(0xFF677191),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp)
                                )
                            }
                        }


                    }
                }
            }
        }

    }


}