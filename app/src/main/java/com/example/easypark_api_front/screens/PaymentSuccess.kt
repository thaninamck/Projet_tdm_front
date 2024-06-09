package com.example.easypark_api_front.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.easypark_api_front.Routes

import androidx.navigation.NavController
import com.example.easypark_api_front.R
import kotlinx.coroutines.delay

@Composable
fun DisplayPaymentSucess(navController: NavController){
    Column (modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.success), contentDescription ="sucess"
            ,modifier=Modifier.size(100.dp))

        Text(text = "Payment success!",style = TextStyle(
            color = Color(0xFF192342),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        ),modifier= Modifier.padding( top = 15.dp, bottom = 3.dp)
        )

        LaunchedEffect(Unit) {
            delay(3000)  // Attend 3 seconds
            navController.navigate(Routes.parkingTicket.route)
        }
    }
}
