package com.example.easypark_api_front.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easypark_api_front.R
import com.example.easypark_api_front.Routes
import com.example.easypark_api_front.model.Reservation
import com.example.easypark_api_front.viewModal

@Composable
fun displayBooking1(navController:NavController,viewModal: viewModal){


    var qr_code=viewModal.qrCode.value
    Column (modifier= Modifier
        .fillMaxSize()
        .background(Color.White),


        ){
        Column (
            modifier= Modifier
                .padding(top=15.dp, start = 10.dp),
            verticalArrangement = Arrangement.Top
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retour",
                modifier= Modifier.clickable {
                    navController.popBackStack()
                }
            )

        }
        Text(text = "PICK PARKING SLOT",style = TextStyle(
            color = Color(0xFF677191),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),modifier= Modifier.padding(start = 40.dp, top = 10.dp, bottom = 15.dp)
        )
        Column (
            modifier=Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "We Picked A Slot For",style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),modifier= Modifier.padding( top = 9.dp, bottom = 3.dp)
            )
            Text(text = "You!",style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),modifier= Modifier.padding(top = 9.dp, bottom = 3.dp)
            )
            Box (modifier = Modifier.padding(40.dp)){
                Image(painter = painterResource(id = R.drawable.parking_lines), contentDescription ="car" ,modifier= Modifier
                    .height(200.dp)
                    .width(253.dp)

                )

            }



        }



















        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()

                .padding(top=85.dp)
        ){
            Button(modifier = Modifier



                .size(width = 332.dp, height = 56.dp)
                ,
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    disabledContainerColor = Color.Black
                )
                , onClick = { navController.navigate(Routes.booking2.route) }) {
                Text(
                    text = "Continue",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp

                    )
                )
            }
        }


    }
}