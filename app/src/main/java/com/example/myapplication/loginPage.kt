package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable

fun LoginPage(innerPaddingValues: PaddingValues , navController: NavController){
    var userName by remember{mutableStateOf("")}
    var emailId by remember{mutableStateOf("")}
    var passWord by remember{mutableStateOf("")}
    Column(modifier= Modifier
        .fillMaxSize()
        .background(Color(163, 127, 219))
    ){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
                contentAlignment = Alignment.Center
            ){
                Text(text="alma mater",
                    color=Color.Black,
                    fontFamily=FontFamily(Font(R.font.poppins)),
                    fontWeight=FontWeight(600),
                    fontSize=32.sp
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
                contentAlignment = Alignment.Center){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 35.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(modifier= Modifier
                        .fillMaxWidth()
                        .height(135.dp)){
                        Column()
                        {
                            Text(
                                text = "Username",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontWeight = FontWeight(400),
                                fontSize = 20.sp
                            )
                            OutlinedTextField(
                                value = userName,
                                onValueChange = {userName=it},
                                modifier = Modifier
                                    .width(340.dp)
                                    .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = false
                            )
                        }
                    }
                    Box(modifier= Modifier
                        .fillMaxWidth()
                        .height(135.dp)){
                        Column(){
                            Text(
                                text = "Email Id",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontWeight = FontWeight(400),
                                fontSize = 20.sp
                            )
                            OutlinedTextField(
                                value = emailId,
                                onValueChange = {emailId=it},
                                modifier = Modifier
                                    .width(340.dp)
                                    .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = false
                            )
                        }
                    }
                    Box(modifier= Modifier
                        .fillMaxWidth()
                        .height(135.dp)){
                        Column(){
                            Text(
                                text = "Password",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontWeight = FontWeight(400),
                                fontSize = 20.sp
                            )
                            OutlinedTextField(
                                value = passWord,
                                onValueChange = {passWord=it},
                                modifier = Modifier
                                    .width(340.dp)
                                    .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = false
                            )
                        }
                    }
                }
            }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier= Modifier
            .fillMaxWidth()
            .height(135.dp),
            contentAlignment = Alignment.Center
        ){
            Button(onClick = {
                navController.navigate(Screens.LandingPage.route) {
                    popUpTo(Screens.LoginPage.route) { inclusive = true }
                }
            },
                modifier=Modifier.size(height=57.dp, width=240.dp),
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
}


