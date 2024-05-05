package com.example.easypark_api_front.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import com.example.easypark_api_front.Routes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easypark_api_front.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun displaySignIn() {
    Column(modifier = Modifier
        .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.SpaceEvenly

    ) {


        Column (
            //modifier=Modifier.,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Text(
                text = "Log in", style = TextStyle(
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,

                    ),
                //modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire

            )

            Text(text = "Hi Welcome! ", style = TextStyle(
                color = Color(0xFFC4C4C4),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp

            ),
                //modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire

            )




            Column (modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ){

                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                val keyboardController = LocalSoftwareKeyboardController.current





                OutlinedTextField(
                    modifier = Modifier
                        .width(332.dp)
                        .padding(top = 10.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    placeholder = { Text(text = "Enter your email") },


                    //supportingText = { Text(text = "Please use the company email address.") },
                    isError = email.contains('.'),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
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
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "birthday") },
                    placeholder = { Text(text = "Enter your birthday") },


                    //supportingText = { Text(text = "Please use the company email address.") },
                    isError = password.contains('.'),
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

                Button(modifier = Modifier

                    .padding(top = 30.dp)

                    .size(width = 332.dp, height = 56.dp)
                    ,
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.Black
                    )
                    , onClick = { /*TODO*/ }) {
                    Text(
                        text = "Sign in",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp

                        )
                    )
                }
            }



            Row (horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier

                    .padding(top = 20.dp)
            ){
                Divider(modifier= Modifier
                    .width(105.dp)
                    .padding(start = 5.dp, end = 5.dp),color = Color.Black, thickness = 0.5.dp)


                Text(text = "Or Sign in with", style = TextStyle(
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp

                ),
                    modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire

                )


                Divider(modifier= Modifier
                    .width(105.dp)
                    .padding(start = 5.dp, end = 5.dp),color = Color.Black, thickness = 0.5.dp)

            }
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(1.dp)
                ,
            )

            Text(text = "Don't have an account ? ", style = TextStyle(
                color = Color(0xFFC4C4C4),
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp

            ),
                modifier = Modifier

                    .padding(top = 40.dp)

            )

            Text(
                text = "Create an account", style = TextStyle(
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,

                    ),

                //modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire
            )


        }
    }
}