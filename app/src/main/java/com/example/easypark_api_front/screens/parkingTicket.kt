package com.example.easypark_api_front.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.example.easypark_api_front.Routes

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun displayTicket(navController: NavController){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxSize()
            .background(
                Color(0xFFDADADA)
            )
    ) {

        Text(text = "Your Parking Ticket",style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
        ),modifier=Modifier.padding(start = 20.dp, top = 40.dp, bottom = 45.dp)
        )

        Column (

            modifier= Modifier
                .background(Color.White)

                .border(5.dp, Color(0xFFFFFFFF), RoundedCornerShape(20.dp))

                .height(420.dp)
                .width(350.dp)
                ,

        ){
            Text(text = "VEHICLE",style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            ),modifier=Modifier.padding(start = 20.dp, top = 15.dp, bottom = 2.dp)
            )
            Text(text = "Logan",style = TextStyle(
                color = Color(0xFF677191),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            ),modifier=Modifier.padding(start = 20.dp, bottom = 2.dp)
            )


            Text(text = "DURATION",style = TextStyle(
                color = Color(0xFF192342),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            ),modifier=Modifier.padding(start = 20.dp, top = 43.dp, bottom = 2.dp)
            )
            Row() {
                Text(text = "4hr ",style = TextStyle(
                    color = Color(0xFF677191),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                ),modifier=Modifier.padding(start = 20.dp, bottom = 2.dp)
                )
                Text(text = "- ",style = TextStyle(
                    color = Color(0xFF677191),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                ),modifier=Modifier.padding(start = 20.dp, bottom = 2.dp)
                )
                Text(text = "23 /2/2000",style = TextStyle(
                    color = Color(0xFF677191),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                ),modifier=Modifier.padding(start = 20.dp, bottom = 2.dp)
                )
            }


                Row (modifier=Modifier.padding(top=23.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Slot",style = TextStyle(
                        color = Color(0xFF192342),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    ),modifier=Modifier.padding(start = 20.dp, top = 9.dp, bottom = 2.dp)
                    )
                    Text(text = "A01",style = TextStyle(
                        color = Color(0xFF192342),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    ),modifier=Modifier.padding(start = 20.dp, top = 9.dp, bottom = 2.dp)
                    )
                }



                Image(painter = painterResource(id = R.drawable.line), contentDescription ="line"
                ,modifier= Modifier
                        .width(900.dp)
                        .height(90.dp))

            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Image(painter = painterResource(id = R.drawable.qr_code), contentDescription ="line"
                    ,modifier=Modifier.width(400.dp).height(90.dp))
            }



        }

        Column (modifier = Modifier



            .padding(top=40.dp)

            ,){
            Button(modifier = Modifier



                .size(width = 332.dp, height = 56.dp)

                ,
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2C2D30),
                    disabledContainerColor = Color.Black
                )
                , onClick = { navController.navigate(Routes.booking2.route) }) {
                Text(
                    text = "Download",
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