package com.example.easypark_api_front.screens



import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.easypark_api_front.R
import com.example.easypark_api_front.Routes
import com.example.easypark_api_front.viewModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun displaySignUp(navController: NavController,viewModal: viewModal) {
    var context = LocalContext.current;
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
                text = "Create Account", style = TextStyle(
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,

                    ),
                //modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire

            )

            Text(text = "Fill your information below ", style = TextStyle(
                color = Color(0xFFC4C4C4),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp

            ),
                //modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire

            )

            Text(text = "or register with your social account ", style = TextStyle(
                color = Color(0xFFC4C4C4),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp

            ),
                //modifier=Modifier.padding(23.dp) // Ajouter une marge autour du texte si nécessaire

            )


            Column (modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ){

                var full_name by rememberSaveable { mutableStateOf("") }
                var password1 by rememberSaveable { mutableStateOf("") }
                var password2 by rememberSaveable { mutableStateOf("") }

                var phone_number by rememberSaveable { mutableStateOf("") }

                val keyboardController = LocalSoftwareKeyboardController.current


                OutlinedTextField(
                    modifier = Modifier
                        .width(332.dp)
                        .padding(top = 10.dp),
                    value = full_name,
                    onValueChange = { full_name = it },
                    label = { Text(text = "full name") },
                    placeholder = { Text(text = "Enter your full name") },


                    //supportingText = { Text(text = "Please use the company email address.") },
                    isError = full_name.contains('.'),
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
                    value = phone_number,
                    onValueChange = { phone_number = it },
                    label = { Text(text = "Phone Number") },
                    placeholder = { Text(text = "Enter your Phone Number") },


                    //supportingText = { Text(text = "Please use the company email address.") },
                    isError = phone_number.contains('.'),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
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
                    value = password1,
                    onValueChange = { password1 = it },
                    label = { Text(text = "password") },
                    placeholder = { Text(text = "Enter your password") },



                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
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
                    value = password2,
                    onValueChange = { password2 = it },
                    label = { Text(text = "Confirm password") },
                    placeholder = { Text(text = "password") },



                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(32.dp),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    singleLine = true
                )


                var passwordMatchError by remember { mutableStateOf(false) }
                var emptyFieldsError by remember { mutableStateOf(false) }
                val showToast = remember { mutableStateOf(false) }

                if (showToast.value) {
                    val context = LocalContext.current

                    Toast.makeText(context, "registering error", Toast.LENGTH_SHORT).show()
                    showToast.value = false
                }
                if (passwordMatchError) {
                    Text(
                        text = "The passwords do not match",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                if (emptyFieldsError) {
                    Text(
                        text = "All Fields are obligatory",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
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

                        if (password1 != password2) {
                            passwordMatchError = true
                        }
                        else {
                            if (full_name.isEmpty() || phone_number.isEmpty() || password1.isEmpty()) {
                            emptyFieldsError = true
                           } else {
                                passwordMatchError = false
                                emptyFieldsError = false
                                viewModal.registerUser(full_name, phone_number, password1, context)
                                }
                            }
                        })


                {
                    Text(
                        text = "Sign up",
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


                Text(text = "Or Continue With", style = TextStyle(
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
                    .clickable {

                        viewModal.signInWithGoogle(context)

                    },
            )

            Text(text = "already have an account ? ", style = TextStyle(
                color = Color(0xFFC4C4C4),
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp

            ),
                modifier = Modifier

                    .padding(top = 40.dp)

            )

            Text(
                text = "login", style = TextStyle(
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,

                    ),
                modifier = Modifier.clickable {
                    navController.navigate(Routes.SignIn.route)
                }
            )


        }
    }

    successCheck(sucess = viewModal.success.value, navController = navController)

}// end of composable function

fun successCheck(sucess:Boolean,navController: NavController){
    if (sucess){
        navController.navigate(Routes.GeoCardSCreen.route)

    }
}