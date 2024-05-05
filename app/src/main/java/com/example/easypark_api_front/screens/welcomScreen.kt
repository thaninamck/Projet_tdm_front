package com.example.easypark_api_front.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.easypark_api_front.Routes

import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easypark_api_front.R

@Composable

fun WelcomeScreen(navController:NavController ) {

    val yellow = colorResource(id = R.color.yellow)
    val black = colorResource(id = R.color.black)
    val gradient = Brush.linearGradient(
        0.0f to yellow,
        500.0f to black,
        start = Offset.Zero,
        end = Offset.Infinite
    )




    Box(                modifier = Modifier.fillMaxSize(),
        ) {
        Image(
            painter = painterResource(id = R.drawable.parking),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(1f),
            contentScale = ContentScale.FillBounds // Redimensionne l'image pour remplir le conteneur sans étirement

        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally


        ) {
            Text(
                text = "Your way",
                style = TextStyle(
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 60.sp,
                    shadow = Shadow(
                        color = black, // Couleur de l'ombre
                        offset = Offset(4f, 4f), // Décalage de l'ombre (horizontal, vertical)
                        blurRadius = 8f // Rayon du flou de l'ombre
                    )

                ),
                modifier= Modifier.padding(top = 120.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
            )

            Text(
                text = "To Park",
                style = TextStyle(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.SemiBold,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Yellow, // Couleur de départ
                            yellow, // Couleur de fin
                        ),
                        start = Offset.Zero, // Start at top-left
                        end = Offset(x = 1f, y = 0f)
                    )
                )
            )

            Text(
                text = "Easily",
                style = TextStyle(
                    color = yellow,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp

                ),
            )

            Column (
                verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.padding(top=20.dp, bottom = 10.dp,start=0.dp, end = 0.dp)
            ){
                Button(modifier = Modifier
                    .size(width = 210.dp, height = 48.dp)
                    ,
                    shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = yellow
                    ), onClick = {
                        navController.navigate(Routes.SignInScreen.route)
                    }) {
                    Text(
                        text = "Sign in",
                        style = TextStyle(
                            color = black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp

                        )
                    )
                }
                
                Spacer(modifier = Modifier.size(15.dp))
                    Button(modifier = Modifier
                        .size(width = 210.dp, height = 48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = yellow
                        )
                        , onClick = {
                            navController.navigate(Routes.SignUp.route)
                        }) {
                        Text(
                            text = "Sign up",
                            style = TextStyle(
                                color = black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp

                            )
                        )
            }
                }

            Column (modifier = Modifier.padding(20.dp)){
                Row (horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically, // Alignement vertical au centre
                    ){
                    Divider(modifier= Modifier
                        .width(115.dp)
                        .padding(start = 5.dp, end = 5.dp),color = Color.White, thickness = 1.dp)


                    Text(text = "Or", style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp

                    ),
                        modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire

                    )


                    Divider(modifier= Modifier
                        .width(115.dp)
                        .padding(start = 5.dp, end = 5.dp),color = Color.White, thickness = 1.dp)

                }



                Button(
                    modifier = Modifier
                        .size(width = 317.dp, height = 48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(1.dp),
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Espacement entre l'image et le texte

                        Text(
                            text = "Continue With Google",
                            style = TextStyle(
                                color = black.copy(alpha = 0.5f),
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier.padding(1.dp) // Ajouter une marge autour du texte si nécessaire
                        )
                    }
                }
            }




        }




        }


    }