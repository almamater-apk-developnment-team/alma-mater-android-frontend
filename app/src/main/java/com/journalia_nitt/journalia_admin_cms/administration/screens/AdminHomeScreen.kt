package com.journalia_nitt.journalia_admin_cms.administration.screens

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.journalia_nitt.journalia_admin_cms.R
import com.journalia_nitt.journalia_admin_cms.administration.landingPageButtonTexts
import com.journalia_nitt.journalia_admin_cms.administration.mode
import com.journalia_nitt.journalia_admin_cms.administration.sharedPreferences.getFromSharedPreferences
import com.journalia_nitt.journalia_admin_cms.administration.sharedPreferences.saveToSharedPreferences
import com.journalia_nitt.journalia_admin_cms.navigation.Screens_in_Admin_cms
import com.journalia_nitt.journalia_admin_cms.ui.theme.urbanist

@Composable
fun AdminHomeScreen(token : MutableState<String>, innerPadding: PaddingValues, navController: NavController) {

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
                        modifier = Modifier.scale(2f)
                    )
                }
                Column(
                    modifier = Modifier
                ) {
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "alma",
                            fontFamily = urbanist,
                            fontSize = 40.sp,
                            color = Color(0XFF9667E0),
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "mater",
                            fontFamily = urbanist,
                            fontSize = 40.sp,
                            color = Color(0XFFBC80F0),
                            fontWeight = FontWeight.ExtraBold
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
                    text = "Welcome to "+token.value,
                    fontFamily = urbanist,
                    fontSize = 20.sp,
                    color = Color.Companion.Black,
                    fontWeight = FontWeight.Companion.Bold
                )
                Text(
                    text = "Dashboard",
                    fontFamily = urbanist,
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
                        .padding(horizontal = 25.dp)
                        .clickable {
                            if (i.first == "POST A DEADLINE") mode.value = 0
                            else if(i.first == "POST ANNOUNCEMENT") mode.value = 1
                            val route = when (i.second) {
                                is Screens_in_Admin_cms.AdminPage -> Screens_in_Admin_cms.AdminPage.createRoute(token.value)
                                is Screens_in_Admin_cms.DeadlinePage -> Screens_in_Admin_cms.DeadlinePage.createRoute(token.value)
                                is Screens_in_Admin_cms.AnnouncementPage -> Screens_in_Admin_cms.AnnouncementPage.createRoute(token.value)
                                else -> throw IllegalStateException("Unknown screen")
                            }
                            navController.navigate(route)
                        }
                        .shadow(
                            elevation = 15.dp,
                            shape = RoundedCornerShape(10.dp)
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
                            fontFamily = urbanist,
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
                            fontFamily = urbanist,
                            fontSize = 16.sp,
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
                            fontFamily = urbanist,
                            fontSize = 16.sp,
                            color = Color.Companion.White,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
            }
        }
    }
}