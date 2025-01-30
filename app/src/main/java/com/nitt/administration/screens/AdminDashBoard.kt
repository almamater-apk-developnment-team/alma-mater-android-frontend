package com.nitt.administration.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nitt.administration.response.AdminPost
import com.nitt.administration.viewModels.AdminDetailsViewModel
import com.nitt.administration.viewModels.PostRepository
import com.nitt.navigation.Screens
import com.nitt.student.navigationDeck.Search
import com.nitt.theme.urbanist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashBoard(navController: NavController,searchViewModel: Search){
    val adminDetailsViewModel: AdminDetailsViewModel = viewModel()
    LaunchedEffect(Unit) {
        adminDetailsViewModel.fetchAdminDetails()
    }
    val detailsList = adminDetailsViewModel.detailsList
    val isLoading = adminDetailsViewModel.isLoading
    val loadingStatus = adminDetailsViewModel.loadingStatus
    val searchType = searchViewModel.searchQuery
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    if(pullToRefreshState.isRefreshing)
    {
        LaunchedEffect(true) {
            isRefreshing = true
            adminDetailsViewModel.fetchAdminDetails()
            isRefreshing = false
        }
    }
    LaunchedEffect(isRefreshing) {
        if(isRefreshing)
        {
            pullToRefreshState.startRefresh()
        }
        else
        {
            pullToRefreshState.endRefresh()
        }
    }
    Column(
        modifier= Modifier
        .fillMaxSize()
        .padding(top = 5.dp),
    ){
        var mode by remember{ mutableIntStateOf(0) }
        Column(
            modifier = Modifier.nestedScroll(
                pullToRefreshState.nestedScrollConnection
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp) ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        mode = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(mode==0) Color(0xFFA37FDB) else Color(0xFFCDC1FF),
                    ),
                    border = ButtonDefaults.outlinedButtonBorder,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .shadow(
                            shape = RoundedCornerShape(10.dp),
                            elevation = 4.dp
                        )
                ) {
                    Text(
                        text = "DEADLINE",
                        fontSize = 15.sp,
                        fontFamily = urbanist,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
                Button(
                    onClick = {
                        mode = 1
                    },
                    border = ButtonDefaults.outlinedButtonBorder,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(mode==1) Color(0xFFA37FDB) else Color(0xFFCDC1FF),
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .shadow(
                            shape = RoundedCornerShape(10.dp),
                            elevation = 4.dp
                        )
                ) {
                    Text(
                        text = "ANNOUNCEMENT",
                        fontSize = 15.sp,
                        fontFamily = urbanist,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }
        }
        val announcementCount = detailsList.sumOf { user ->
            user.details.count { post -> post.type == "Announcement" }
        }

        val deadlineCount = detailsList.sumOf { user ->
            user.details.count { post -> post.type == "Deadline" }
        }

        val filteredPosts = detailsList.filter { it.details.any { post -> post.title.startsWith(searchType.value) }}

        Box(
            contentAlignment = Alignment.Center
        )
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            ) {
                if (searchType.value.isEmpty()) {
                    detailsList.forEach { user ->
                        items(user.details) { post ->
                            if (loadingStatus.value == "Loaded Successfully") {
                                if (mode == 0 && post.type == "Deadline") {
                                    if (deadlineCount != 0) {
                                        AdminCard(
                                            navController = navController,
                                            post = post
                                        )
                                    } else {
                                        Text(
                                            text = "No Deadlines As of Now...",
                                            fontSize = 14.sp,
                                            fontFamily = urbanist,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                } else if (mode == 1 && post.type == "Announcement") {
                                    if (announcementCount != 0) {
                                        AdminCard(
                                            navController = navController,
                                            post = post
                                        )
                                    } else {
                                        Text(
                                            text = "No Announcements As of Now...",
                                            fontSize = 14.sp,
                                            fontFamily = urbanist,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            } else {
                                Text(text = loadingStatus.value)
                            }
                        }
                    }
                } else {
                    filteredPosts.forEach { user ->
                        items(user.details) { post ->
                            if (loadingStatus.value == "Loaded Successfully") {
                                if (mode == 0 && post.type == "Deadline") {
                                    if (deadlineCount != 0) {
                                        AdminCard(
                                            navController = navController,
                                            post = post
                                        )
                                    } else {
                                        Text(
                                            text = "No Deadlines As of Now...",
                                            fontSize = 14.sp,
                                            fontFamily = urbanist,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                } else if (mode == 1 && post.type == "Announcement") {
                                    if (announcementCount != 0) {
                                        AdminCard(
                                            navController = navController,
                                            post = post
                                        )
                                    } else {
                                        Text(
                                            text = "No Announcements As of Now...",
                                            fontSize = 14.sp,
                                            fontFamily = urbanist,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(isLoading.value)
            {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                )
                {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 3.dp,
                    )
                    Text(
                        text = "Loading...",
                        fontSize = 14.sp,
                        fontFamily = urbanist,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AdminCard(
    navController: NavController,
    post: AdminPost
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 5.dp)
            .clickable {
                PostRepository.savePost(post)
                navController.navigate(Screens.AdminViewPostScreen.route + "/${post.postId}")
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFA37FDB))
    ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column()
                {
                    Text(
                        text = post.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = urbanist,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 10.dp,top = 10.dp).fillMaxWidth(0.9f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = post.description,
                        fontSize = 18.sp,
                        fontFamily = urbanist,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp).fillMaxWidth(0.9f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = post.author,
                        fontSize = 15.sp,
                        fontFamily = urbanist,
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp).fillMaxWidth(0.9f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                HorizontalDivider(color = Color.White,modifier = Modifier.fillMaxWidth(0.85f),
                    thickness = 1.5.dp)
                Text(
                    text = "Deadline:   ${post.deadline.date.toString() + "/"+ post.deadline.monthInInt.toString() + "/"+ post.deadline.year }",
                    fontSize = 18.sp,
                    fontFamily = urbanist,
                    color = Color.White,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp).fillMaxWidth(0.9f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
    }
}