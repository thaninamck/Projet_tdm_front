package com.example.easypark_api_front.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easypark_api_front.Routes

import com.example.easypark_api_front.R
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun displayBooking4(navController: NavController){
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
        Text(text = "PAYMENT",style = TextStyle(
            color = Color(0xFF677191),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),modifier= Modifier.padding(start = 40.dp, top = 10.dp, bottom = 15.dp)
        )
        Column (
            modifier= Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){

            var number by rememberSaveable { mutableStateOf("") }

            var exp by rememberSaveable { mutableStateOf("") }
            var cvv by rememberSaveable { mutableStateOf("") }

            val keyboardController = LocalSoftwareKeyboardController.current


            OutlinedTextField(
                modifier = Modifier
                    .width(332.dp)
                    .padding(top = 10.dp),
                value = number,
                onValueChange = { number = it },
                label = { Text(text = "Card Number") },
                placeholder = { Text(text = "enter your card number here") },


                //supportingText = { Text(text = "Please use the company email address.") },
                isError = number.contains('.'),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(32.dp),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                singleLine = true
            )


            OutlinedTextField(
                modifier = Modifier
                    .width(332.dp)
                    .padding(top = 10.dp),
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text(text = "CVV") },
                placeholder = { Text(text = "Enter the cvv here") },


                //supportingText = { Text(text = "Please use the company email address.") },
                isError = cvv.contains('.'),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(32.dp),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                singleLine = true
            )


            OutlinedTextField(
                modifier = Modifier
                    .width(332.dp)
                    .padding(top = 10.dp),
                value = exp,
                onValueChange = { exp = it },
                label = { Text(text = "Expiration Date") },
                placeholder = { Text(text = "exp") },


                //supportingText = { Text(text = "Please use the company email address.") },
                isError = exp.contains('.'),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(32.dp),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                singleLine = true
            )



            Image(painter = painterResource(id = R.drawable.credit_card), contentDescription ="parking_image" ,
                modifier= Modifier
                    .padding(start = 20.dp, top = 30.dp, end = 20.dp,bottom=10.dp)
                    .width(345.dp)
                    .height(211.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.FillBounds)


            Row(
                modifier = Modifier
                    .background(Color(0xFFF3F6FF))

                    .height(50.dp)
                    .width(335.dp) ,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Divider(modifier= Modifier
                    .height(50.dp)
                    .width(8.dp)
                    ,color = Color.Black, thickness = 2.dp)
                Text(
                    text = "TOTAL", modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                )
                Column (
                    modifier=Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End){
                    Text(
                        text = "800 DZD", modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                    )
                }
            }




        }


        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()

                .padding(top = 45.dp)
        ){
            Button(modifier = Modifier



                .size(width = 332.dp, height = 56.dp)
                ,
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    disabledContainerColor = Color.Black
                )
                , onClick = { navController.navigate(Routes.paymentSuccess.route) }) {
                Text(
                    text = "Confirm & Pay",
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

