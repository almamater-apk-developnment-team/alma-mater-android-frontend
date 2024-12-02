package com.example.myapplication

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AdminDashBoard(innerPaddingValues: PaddingValues , navController: NavController){
    val scrollState = rememberScrollState()
    Column(modifier=Modifier.fillMaxSize().padding(innerPaddingValues)){
        TopBar()
        Box(modifier=Modifier.fillMaxWidth()
            .height(50.dp),
            contentAlignment = Alignment.Center){
            Text(text="ADMIN DASHBOARD",
                fontSize = 24.sp    ,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(R.font.poppins))
            )
        }
        Box(modifier = Modifier.fillMaxWidth()
            .height(100.dp)){
                Row(modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(163, 127, 219)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.size(height = 60.dp, width = 175.dp)
                            .border(
                                shape = RoundedCornerShape(10.dp),
                                width = 2.dp,
                                color = Color.Black
                            )
                            .shadow(shape = RoundedCornerShape(10.dp), elevation = 10.dp)
                    ) {
                        Text(
                            text = "DEADLINE",
                            fontSize = 17.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(600)
                        )
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(205, 193, 255)),
                        modifier = Modifier.size(height = 60.dp, width = 200.dp)
                            .border(
                                shape = RoundedCornerShape(10.dp),
                                width = 2.dp,
                                color = Color.Black
                            )
                            .shadow(shape = RoundedCornerShape(10.dp), elevation = 10.dp)
                    ) {
                        Text(
                            text = "ANNOUNCEMENT",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(600)
                        )
                    }
                }
            }
        Box(modifier=Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
            ){
            Column(
                modifier=Modifier.height(800.dp).
                verticalScroll(scrollState)) {
                AdminCard()
                AdminCard()
                AdminCard()
                AdminCard()
                AdminCard()

            }

        }
    }
}

