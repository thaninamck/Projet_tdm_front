package com.example.easypark_api_front.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavController
import com.example.easypark_api_front.R
import com.example.easypark_api_front.viewModal
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.stevdzasan.onetap.GoogleUser
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.getUserFromTokenId
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun displaySignIn(navController: NavController, viewModal: viewModal) {
    var context = LocalContext.current;

    val loginSuccess by viewModal.loginSuccess

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            viewModal.loginSuccess.value = false;
            navController.navigate(Routes.GeoCardSCreen.route) {
                popUpTo(0)
            }
        }
    }
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

                var fullname by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                var emptyFieldsError by remember { mutableStateOf(false) }
                val keyboardController = LocalSoftwareKeyboardController.current





                OutlinedTextField(
                    modifier = Modifier
                        .width(332.dp)
                        .padding(top = 10.dp),
                    value = fullname,
                    onValueChange = { fullname = it },
                    label = { Text(text = "Full name") },
                    placeholder = { Text(text = "Enter your Full name") },


                    //supportingText = { Text(text = "Please use the company email address.") },
                    isError = fullname.contains('.'),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
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
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Enter your password") },


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
                    , onClick = {
                            if (fullname.isEmpty() || password.isEmpty()) {
                                emptyFieldsError = true
                            } else {
                                emptyFieldsError = false
                                viewModal.loginUser(fullname, password, context)
                            }
                    }) {
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


            val state = rememberOneTapSignInState()
            var user: GoogleUser? by remember { mutableStateOf(null) }
            OneTapSignInWithGoogle(
                state = state,
                clientId = "887801240888-arqdr0c18hd0am9gb4fsgg03ui8ulmuk.apps.googleusercontent.com",
                rememberAccount = false,
                onTokenIdReceived = {
                    user = getUserFromTokenId(tokenId = it)
                    Log.d("MainActivity", user.toString())
                },
                onDialogDismissed = {
                    Log.d("MainActivity", it)
                }
            )

            Button(onClick = { state.open() }) {
//
                    Text(text = "Sign in")
                }
//            Image(
//                painter = painterResource(id = R.drawable.google_logo),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(30.dp)
//                    .padding(1.dp)
//                    .clickable {
//                OneTapSignInState.open()
//
//
//
//
//                    }
//                ,
//            )

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
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.SignUp.route)
                    }
                    //modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire
                )

        }
    }

    successCheckLogin(sucess = viewModal.success.value, navController = navController)
}


fun successCheckLogin(sucess:Boolean,navController: NavController){
    if (sucess){
        navController.navigate(Routes.GeoCardSCreen.route)
    }else{

    }
}