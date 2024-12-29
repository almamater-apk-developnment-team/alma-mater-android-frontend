package com.journalia_nitt.journalia_admin_cms

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
val poppins = FontFamily(Font(R.font.poppins))
val landingPageButtonTexts = listOf(
    Pair("ADMIN DASHBOARD", Screens.AdminPage),
    Pair("POST A DEADLINE" , Screens.DeadlinePage),
    Pair("POST ANNOUNCEMENT" , Screens.AnnouncementPage)
)
val mode = mutableIntStateOf(0)
@Composable
fun SplashPage(innerPadding: PaddingValues , navController: NavController) {

    val context = LocalContext.current
    val token = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    try {
                        token.value = getFromSharedPreferences(context , "token")
                        if(token.value == "") {
                            navController.navigate(Screens.LoginPage.route)
                        }
                        else {
                            navController.navigate(Screens.LandingPage.createRoute(token.value))
                        }
                    }
                    catch(e : Exception) {
                        Log.d("message" , e.message.toString())
                    }
                },
            colors = CardDefaults.cardColors(Color(163, 127, 219))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Text(
                    text = "Alma mater",
                    color = Color.Companion.White,
                    fontFamily = poppins,
                    fontSize = 35.sp
                )
                Spacer(modifier = Modifier.padding(0.dp))
                Text(
                    text = "Admin Interface",
                    color = Color.Companion.White,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.padding(40.dp))
                Image(
                    painter = painterResource(R.drawable.nittlogo),
                    contentDescription = "Logo",
                    modifier = Modifier.scale(4f)
                )
            }
        }
    }
}
@Composable
fun LandingPage(token : MutableState<String>,innerPadding: PaddingValues , navController: NavController) {

    val context = LocalContext.current

    try {
        if(getFromSharedPreferences(context , "token") == "") {
            saveToSharedPreferences(context,"token",token.value)
        }
    }
    catch(e : Exception) {
        Log.d("message" , e.message.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(Color.Companion.White)
        ) {
            Box() {
                Column(
                    modifier = Modifier
                ) {
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Alma mater",
                            fontFamily = poppins,
                            fontSize = 32.sp,
                            color = Color.Companion.Black,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        modifier = Modifier.padding(end = 10.dp, top = 10.dp),
                        onClick = {
                            token.value = ""
                            saveToSharedPreferences(context,"token",token.value)
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.signout),
                            contentDescription = "sign_out",
                            modifier = Modifier.scale(3f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(top = 80.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.nittlogo),
                    contentDescription = "Logo",
                    modifier = Modifier.scale(3.5f)
                )
            }
            Spacer(modifier = Modifier.padding(top = 60.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Student Welfare ",
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    color = Color.Companion.Black,
                    fontWeight = FontWeight.Companion.Bold
                )
                Text(
                    text = "Dashboard",
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    color = Color.Companion.Black,
                    fontWeight = FontWeight.Companion.Bold
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            for (i in landingPageButtonTexts) {
                Spacer(modifier = Modifier.padding(top = 25.dp))
                Card(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable {
                            if (i.first == "POST A DEADLINE") mode.value = 0
                            else if(i.first == "POST ANNOUNCEMENT") mode.value = 1
                            val route = when (i.second) {
                                is Screens.AdminPage -> Screens.AdminPage.createRoute(token.value)
                                is Screens.DeadlinePage -> Screens.DeadlinePage.createRoute(token.value)
                                is Screens.AnnouncementPage -> Screens.AnnouncementPage.createRoute(token.value)
                                else -> throw IllegalStateException("Unknown screen")
                            }
                            navController.navigate(route)
                        }
                        .border(
                            width = 2.dp,
                            color = Color.Companion.Black,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(Color(205, 193, 255))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Companion.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = i.first,
                            fontFamily = poppins,
                            fontSize = 20.sp,
                            color = Color.Companion.Black,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp, start = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Companion.Bottom
            ) {
                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .weight(0.5f),
                    colors = CardDefaults.cardColors(Color(163, 127, 219))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Companion.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "POST A QUERY",
                            fontFamily = poppins,
                            fontSize = 20.sp,
                            color = Color.Companion.White,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(start = 20.dp))
                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .weight(0.5f),
                    colors = CardDefaults.cardColors(Color(163, 127, 219))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Companion.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "CONTACT US",
                            fontFamily = poppins,
                            fontSize = 20.sp,
                            color = Color.Companion.White,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
            }
        }
    }
}