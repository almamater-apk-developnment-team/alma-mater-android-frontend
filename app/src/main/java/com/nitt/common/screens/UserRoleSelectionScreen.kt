package com.nitt.common.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.nitt.R
import com.nitt.navigation.Screens
import com.nitt.theme.color_2
import com.nitt.theme.urbanist

@Composable
fun UserRoleSelectionScreen(navController: NavController)
{
    val versionCode = "1.3"
    val buttonColor = Color(0XFFCDC1FF)
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().background(color = color_2).padding(WindowInsets.systemBars.asPaddingValues()).background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "alma",
                    fontWeight = FontWeight(600),
                    fontFamily = urbanist,
                    fontSize = 50.sp,
                    color = Color(150,103,224)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text="mater",
                    fontWeight = FontWeight(600),
                    fontFamily = urbanist,
                    fontSize = 50.sp,
                    color = Color(188, 128, 240)
                )
            }
            Text(
                text="made for NIT Trichy",
                fontWeight = FontWeight(600),
                fontFamily = urbanist,
                fontSize = 15.sp,
                color = Color(150,103,224)
            )
            Spacer(modifier = Modifier.height(20.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.raw.nitt_logo)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "SVG Logo",
                modifier = Modifier.size(100.dp)
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = "Hey Welcome to alma mater's",
                fontFamily = urbanist,
                fontSize = 20.sp)
            Text(
                text = "Beta Testing $versionCode",
                fontFamily = urbanist,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = buttonColor),
                elevation = CardDefaults.cardElevation(15.dp),
                modifier = Modifier.shadow(0.dp, shape = RoundedCornerShape(12.dp)).fillMaxWidth(0.85f).padding(top = 20.dp)
                    .clickable {
                        navController.navigate(Screens.StudentLoginScreen.route)
                    }
            )
            {
                Text(text = "< LOGIN >",fontSize = 18.sp,modifier= Modifier.padding(0.dp,20.dp).align(Alignment.CenterHorizontally),
                    fontFamily = urbanist,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "By moving forward you accept to our",
                fontSize = 14.sp,
                fontFamily = urbanist,
                color = Color.Gray
            )
            Row {
                Text(
                    text = "Terms and Conditions",
                    fontSize = 14.sp,
                    fontFamily = urbanist,
                    color = Color.Black,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://myalmamater.in/terms_and_conditions"))
                        context.startActivity(intent)
                    }
                )
                Text(
                    text = " and ",
                    fontSize = 14.sp,
                    fontFamily = urbanist,
                    color = Color.Gray
                )
                Text(
                    text = "Privacy Policy",
                    fontSize = 14.sp,
                    fontFamily = urbanist,
                    color = Color.Black,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://myalmamater.in/privacy_policy"))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}