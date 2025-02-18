package com.nitt.student.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.nitt.R
import com.nitt.navigation.Screens
import com.nitt.student.sharedPreferences.getUserDetails
import com.nitt.theme.urbanist

@Composable
fun StudentHomeScreen(
    navController: NavController,
) {

    val authRepository = JWTToken()
    val context=LocalContext.current
    val adminImage= painterResource(R.drawable.admin)
    val scrollState = rememberScrollState()
    val userDetails = getUserDetails(context = context)
    val rollno = userDetails?.collegeId.toString()
    if (userDetails != null) {
        authRepository.generateJWT(rollno,context).toString()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(20.dp, 10.dp)
                    .shadow(15.dp, shape = RoundedCornerShape(12.dp)),
            ) {
                val name = userDetails?.name
                Column() {
                    if (name != null) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp),
                            text = "Hello $name !",
                            fontFamily = urbanist,
                            fontSize = 22.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                    Card(
                        modifier = Modifier
                            .padding(10.dp,10.dp)
                            .border(1.dp,Color.Black,RoundedCornerShape(12.dp))
                            .clickable{
                                navController.navigate(Screens.StudentAdminDashboardScreen.route)
                            },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 20.dp
                        ),
                        colors = CardDefaults.cardColors(containerColor = Color(177, 157, 255))
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(0.dp,5.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp,Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Image(
                                painter=adminImage,
                                contentDescription="admin",
                                modifier=Modifier.size(
                                    25.dp
                                )
                            )
                            Text(
                                text="Admin Dashboard",
                                fontWeight=FontWeight.Bold,
                                fontSize=18.sp,
                                fontFamily = urbanist,
                            )

                        }
                    }
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Explore",
                    fontWeight = FontWeight(600),
                    fontFamily = urbanist,
                    fontSize = 24.sp,
                    color = Color.Black
                )
                Text(
                    text = "what's happening around you!",
                    fontWeight = FontWeight(500),
                    fontFamily = urbanist,
                    fontSize = 14.sp,
                    color = Color(177, 157, 255),
                    modifier= Modifier.padding(0.dp,0.dp,0.dp,10.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround)
            {
                CommunityCardComponent( navController = navController, route = Screens.StudentAlumniCommunityScreen.route, communityName = "Alumni", data = R.raw.alumni_logo)
                CommunityCardComponent( navController = navController, route = Screens.StudentHomeScreen.route, communityName = "Star Zerox", data = R.raw.star_zeros_logo, onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://myalmamater.in/star_xerox"))
                    context.startActivity(intent)
                })
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top=20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                CommunityCardComponent( navController = navController, route = Screens.StudentHomeScreen.route, communityName = "Club", data = R.raw.club_community_logo)
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun CommunityCardComponent(
    navController: NavController,
    route:String,
    communityName:String,
    data:Any,
    onClick:() -> Unit = ({ })
)
{

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .shadow(15.dp, shape = RoundedCornerShape(20.dp))
            .clickable {
                if(communityName == "Club")
                {
                    Toast.makeText(navController.context,"Coming Soon",Toast.LENGTH_SHORT).show()
                }
                onClick()
                navController.navigate(route)
            }
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = communityName,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = urbanist,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Community",
                fontWeight = FontWeight(600),
                fontFamily = urbanist,
                fontSize = 12.sp,
                color = Color(177, 157, 255),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(10.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "nitt_fest_logo",
                modifier = Modifier.padding(5.dp).size(110.dp,90.dp)
            )
        }
    }
}