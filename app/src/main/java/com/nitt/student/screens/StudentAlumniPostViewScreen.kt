package com.nitt.student.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nitt.R
import com.nitt.alumni.clickedPost
import com.nitt.alumni.response.Comment
import com.nitt.alumni.response.Upvote
import com.nitt.alumni.theUser
import com.nitt.alumni.viewModels.AlumniUploadViewModel
import com.nitt.theme.urbanist

@Composable
fun StudentAlumniPostViewScreen( navController: NavController) {
    val scrollState = rememberScrollState()
    val heading = clickedPost.value.title
    val body = clickedPost.value.description
    val formLink = clickedPost.value.link1
    val viewModel: AlumniUploadViewModel = viewModel()

    val context = LocalContext.current
    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pfp)
    val comments = remember {
        mutableStateListOf<Comment>().apply {
            addAll(clickedPost.value.comments)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 15.dp)
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .scale(2.0f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.john_doe),
                        contentDescription = "Profile image"
                    )
                }
            }
            Spacer(modifier = Modifier.padding(start = 5.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ){
                Text(
                    text = clickedPost.value.username,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = urbanist
                )
                Text(
                    modifier = Modifier
                        .offset(y=18.dp),
                    text = "CEO - Bharatx",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    fontFamily = urbanist
                )
            }
            Spacer(modifier = Modifier.padding(start = 110.dp))

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Text(
                    text = heading,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = urbanist,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
            Spacer(modifier = Modifier.padding(top = 25.dp))
            Divider(
                color = Color.LightGray,
                thickness = (1).dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.padding(top = 30.dp))
            Box(
                modifier = Modifier
                    .width(353.dp)
                    .height(182.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
            ){
                DisplayPostImage(fileUrl = clickedPost.value.file_url)
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Text(
                text = body,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                fontSize = 16.sp,
                fontFamily = urbanist
            )
            if(clickedPost.value.link1.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(top = 30.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp)
                        .padding(horizontal = 30.dp),
                    colors = CardDefaults.cardColors(Color(163, 127, 219))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Available link",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontFamily = urbanist
                        )
                        IconButton(
                            onClick = {},
                            modifier = Modifier.scale(0.9f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.link),
                                contentDescription = "Link button"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text =  getRelativeTime(clickedPost.value.upload_time),
                    fontSize = 12.sp,
                    fontFamily = urbanist
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                thickness = (1).dp,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.padding(start = 20.dp))
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                        },
                        modifier = Modifier
                            .scale(2f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.comment_button),
                            contentDescription = "Comment button"
                        )
                    }
                    Spacer(
                        modifier = Modifier.padding(start = 3.dp)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = clickedPost.value.comments.size.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = urbanist
                    )
                }
                var image = remember(clickedPost.value.upvoters) { mutableStateOf( Icons.Outlined.FavoriteBorder ) }
                if(clickedPost.value.upvoters.contains(theUser.username)) {
                    image.value = Icons.Filled.Favorite
                }
                var upvotes = remember(clickedPost.value.upvotes){ mutableStateOf(clickedPost.value.upvotes) }
                Spacer(modifier = Modifier.padding(start = 20.dp))
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if(!clickedPost.value.upvoters.contains(theUser.username)) {
                                println(clickedPost.value.id)
                                viewModel.upvotePost(
                                    clickedPost.value.id,
                                    Upvote(theUser.username, clickedPost.value.id)
                                )
                                clickedPost.value.upvotes++
                                image.value = Icons.Filled.Favorite
                            }
                        },
                        modifier = Modifier
                    ) {
                        Image(
                            painter = rememberVectorPainter(image.value),
                            contentDescription = "Like button",
                            modifier = Modifier.size(23.dp)
                        )
                    }
                    Text(
                        modifier = Modifier,
                        text = upvotes.value.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = urbanist
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = "Comments",
                fontSize = 19.sp,
                fontFamily = urbanist,
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = clickedPost.value.comments.size.toString() + " comments",
                fontSize = 12.sp,
                fontFamily = urbanist,
                color = Color.Black,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        if (comments.isEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.padding(start = 20.dp))
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    text = "No comments yet",
                    fontSize = 16.sp,
                    fontFamily = urbanist,
                    color = Color.Gray
                )
            }
        } else {
            comments.forEach { comment ->
                CommentCard(comment, bitmap)
            }
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun CommentCard(comment: Comment, bitmap: Bitmap) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(Color(163, 127, 219)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = comment.username,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White
                )
                Text(
                    text = comment.comment,
                    fontSize = 12.sp,
                    color = Color.White,
                    lineHeight = 15.sp
                )
            }
        }
    }
}


@Composable
fun DisplayPostImage(fileUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(fileUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Post Image",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
