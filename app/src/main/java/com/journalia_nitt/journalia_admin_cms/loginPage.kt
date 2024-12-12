package com.journalia_nitt.journalia_admin_cms

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginPage(
    innerPaddingValues: PaddingValues ,
    navController: NavController
){

    val coroutineScope = rememberCoroutineScope()

    var emailId by remember{mutableStateOf("")}
    var passWord by remember{mutableStateOf("")}
    var isLoading by remember{mutableStateOf(false)}

    Column(modifier= Modifier
        .fillMaxSize()
        .padding(innerPaddingValues)
        .background(Color(163, 127, 219)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "alma mater",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            fontFamily = poppins,
            modifier = Modifier.padding(bottom = 50.dp)
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Image(
            painter = painterResource(R.drawable.nittlogo),
            contentDescription = "Logo",
            modifier = Modifier.scale(3f)
        )
        Spacer(modifier = Modifier.padding(top = 100.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(start = 55.dp))
            Text(
                text = "Email ID",
                fontFamily = poppins,
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        OutlinedTextField(
            value = emailId,
            onValueChange = {
                emailId = it
            },
            modifier = Modifier
                .width(340.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            singleLine = false
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(start = 55.dp))
            Text(
                text = "Password",
                fontFamily = poppins,
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        OutlinedTextField(
            value = passWord,
            onValueChange = {
                passWord = it
            },
            modifier = Modifier
                .width(340.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            singleLine = false
        )
        Spacer(modifier = Modifier.padding(top = 120.dp))

        if(isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.padding(top = 30.dp))
        }

        Button(
            modifier=Modifier.size(height=57.dp, width=140.dp),
            onClick = {
                coroutineScope.launch(Dispatchers.Main) {
                    try {
                        isLoading = true
                        val response = LoginClient.login(LoginBody(emailId, passWord))
                        if (response.isSuccessful) {
                            isLoading = false
                            passWord = ""
                            Log.d("messageLogin", "success")
                            navController.navigate(Screens.SecretPage.createRoute(emailId))
                        } else {
                            //handle displaying that the process failed
                            isLoading = false
                            Log.d("messageLogin", response.message)
                            passWord = ""
                            navController.navigate(Screens.LoginPage.route)
                        }
                    }
                    catch (e: Exception) {
                        isLoading = false
                        passWord = ""
                        Log.d("message", e.message.toString())
                    }
                }
            },
            shape= RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.8f))
        ) {
            Text(
                text="Submit",
                color=Color.White,
                fontFamily= FontFamily(Font(R.font.poppins)),
                fontWeight=FontWeight(600),
                fontSize=20.sp
            )
        }
    }
}

@Composable
fun SecretChecking(
    email: String,
    innerPaddingValues: PaddingValues,
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()
    var secret by remember{mutableStateOf("")}
    var isLoading by remember{mutableStateOf(false)}

    Column(modifier= Modifier
        .fillMaxSize()
        .padding(innerPaddingValues)
        .background(Color(163, 127, 219)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "alma mater",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            fontFamily = poppins,
            modifier = Modifier.padding(bottom = 50.dp)
        )
        Image(
            painter = painterResource(R.drawable.nittlogo),
            contentDescription = "Logo",
            modifier = Modifier.scale(4f)
        )
        Spacer(modifier = Modifier.padding(top = 100.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "Enter the Verification code sent to your Email ID",
                fontFamily = poppins,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        OutlinedTextField(
            value = secret,
            onValueChange = {
                secret = it
            },
            modifier = Modifier
                .width(340.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            singleLine = false
        )
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Didn't receive ?",
                fontFamily = poppins,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(
                text = "Send again",
                fontFamily = poppins,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.padding(top = 120.dp))

        if(isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.padding(top = 30.dp))
        }

        Button(
            modifier=Modifier.size(height=57.dp, width=240.dp),
            onClick = {
                coroutineScope.launch(Dispatchers.Main) {
                    try {
                        isLoading = true
                        val response = SecretClient.secret(SecretBody(email, secret))
                        if (response.token == null) {
                            isLoading = false
                            secret = ""
                            Log.d("message", response.message)
                            navController.navigate(Screens.LoginPage.route)
                        } else {
                            //handle displaying that the process
                            isLoading = false
                            secret = ""
                            Log.d("token",response.token)
                            navController.navigate(Screens.LandingPage.createRoute(response.token))
                        }
                    }
                    catch (e: Exception) {
                        isLoading = false
                        secret = ""
                        Log.d("message", e.message.toString())
                    }
                }
            },
            shape= RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(224, 170, 255))
        ) {
            Text(
                text="Go to Dashboard",
                color=Color.Black,
                fontFamily= FontFamily(Font(R.font.poppins)),
                fontWeight=FontWeight(600),
                fontSize=20.sp
            )
        }
    }
}


