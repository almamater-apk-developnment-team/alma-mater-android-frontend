package com.journalia_nitt.journalia_admin_cms.student.navigationDeck

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.journalia.Student.SharedPreferences.clearUserDetails
import com.journalia_nitt.journalia_admin_cms.R
import com.journalia_nitt.journalia_admin_cms.navigation.Screens
import com.journalia_nitt.journalia_admin_cms.ui.theme.urbanist
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideMenuBar(showSideBar : MutableState<Boolean>,navController: NavController)
{

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


//
//    val context = LocalContext.current
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.End
//    ) {
//        Card(
//            modifier = Modifier
//                .width(220.dp)
//                .fillMaxHeight(),
//            colors = CardDefaults.cardColors(Color.White),
//            shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
//            elevation = CardDefaults.cardElevation(5.dp)
//        ) {
//            Spacer(modifier = Modifier.padding(20.dp))
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.SpaceAround
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Absolute.SpaceAround,
//                    verticalAlignment = Alignment.CenterVertically
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .padding(start = 10.dp, top = 10.dp),
////                        horizontalArrangement = Arrangement.Start
//                ) {
//                    Icon(
//                        modifier = Modifier.clickable{
//                            showSideBar.value = false
//                        },
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "close menu"
//                    )
//                    Text(
//                        text = "alma mater",
//                        fontSize = 29.sp,
//                        fontFamily = urbanist,
//                        color = Color(150, 103, 224)
//                    )
//                }
//                Divider(
//                    modifier = Modifier.padding(horizontal = 15.dp),
//                    thickness = 3.dp,
//                    color = Color.Black
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(30.dp,0.dp,0.dp,0.dp)
//                        .clickable {
//                            navController.navigate(Screens.Webmail.route)
//                        },
//                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        modifier = Modifier.size(25.dp),
//                        painter = painterResource(id = R.drawable.nittlogo),
//                        contentDescription = null
//                    )
//                    Text(
//                        text = "Webmail Login",
//                        fontSize = 18.sp,
//                        fontFamily = urbanist
//                    )
//                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(30.dp,0.dp,0.dp,0.dp)
//                        .clickable {
//
//                        },
//                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Image(
//                        modifier = Modifier.size(25.dp),
//                        painter = painterResource(id = R.drawable.notifications),
//                        contentDescription = null
//                    )
//                    Column()
//                    {
//                        Text(
//                            text = "Notification",
//                            fontSize = 18.sp,
//                            fontFamily = urbanist
//                        )
//                        Text(
//                            text = "Inbox",
//                            fontSize = 18.sp,
//                            fontFamily = urbanist
//                        )
//                    }
//
//                }
//                Divider(
//                    modifier = Modifier.padding(horizontal = 15.dp),
//                    thickness = 3.dp,
//                    color = Color.Black
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(30.dp,0.dp,0.dp,0.dp)
//                        .clickable {
//                            navController.navigate(Screens.ClubDirectory.route)
//                        },
//                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        modifier = Modifier.size(25.dp),
//                        painter = painterResource(id = R.drawable.clubs),
//                        contentDescription = null
//                    )
//                    Text(
//                        text = "Explore Clubs",
//                        fontSize = 18.sp,
//                        fontFamily = urbanist
//                    )
//                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(30.dp,0.dp,0.dp,0.dp)
//                        .clickable {
//                            navController.navigate(Screens.FestDirectory.route)
//                        },
//                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        modifier = Modifier.size(25.dp),
//                        painter = painterResource(id = R.drawable.fests),
//                        contentDescription = null
//                    )
//                    Text(
//                        text = "Explore Fests",
//                        fontSize = 18.sp,
//                        fontFamily = urbanist
//                    )
//                }
//                Divider(
//                    modifier = Modifier.padding(horizontal = 15.dp),
//                    thickness = 3.dp,
//                    color = Color.Black
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(30.dp,0.dp,0.dp,0.dp)
//                        .clickable {
//                            navController.navigate(Screens.AdminPage.route)
//                        },
//                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        modifier = Modifier.size(25.dp),
//                        painter = painterResource(id = R.drawable.adminboard),
//                        contentDescription = null
//                    )
//                    Text(
//                        text = "Admin Board",
//                        fontSize = 18.sp,
//                        fontFamily = urbanist
//                    )
//                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 40.dp)
//                        .clickable {

//                            }
//                        },
//                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Sign Out",
//                        fontSize = 18.sp,
//                        fontFamily = urbanist
//                    )
//                    Spacer(modifier = Modifier.padding(start = 20.dp))
//                    Image(
//                        modifier = Modifier.size(25.dp),
//                        painter = painterResource(id = R.drawable.signout),
//                        contentDescription = null
//                    )
//                }
//            }
//        }
//    }
}