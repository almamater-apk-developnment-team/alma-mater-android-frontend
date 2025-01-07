package com.journalia_nitt.journalia_admin_cms.administration.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.journalia_nitt.journalia_admin_cms.R
import com.journalia_nitt.journalia_admin_cms.administration.EditStateAdmin
import com.journalia_nitt.journalia_admin_cms.administration.infoPasser
import com.journalia_nitt.journalia_admin_cms.administration.response.Deadline
import com.journalia_nitt.journalia_admin_cms.alumni.EditState
import com.journalia_nitt.journalia_admin_cms.navigation.Screens
import com.journalia_nitt.journalia_admin_cms.student.screens.ShowImageInDialog
import com.journalia_nitt.journalia_admin_cms.ui.theme.urbanist
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.time.LocalDate

fun getMonthInt(month : String) : Int {
    val ans = when(month) {
        "Jan" -> 1
        "Feb" -> 2
        "Mar" -> 3
        "Apr" -> 4
        "May" -> 5
        "Jun" -> 6
        "Jul" -> 7
        "Aug" -> 8
        "Sep" -> 9
        "Oct" -> 10
        "Nov" -> 11
        "Dec" -> 12
        else -> 0
    }
    return ans
}

fun getMonth(month : Int) : String {
    val ans =  when(month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> "Invalid Month"
    }
    return ans.uppercase()
}

fun getMonthCalender(
    year : Int ,
    month : Int
) : List<List<Int>> {

    val specificDate = LocalDate.of(year,month,1)

    val firstDayOfMonth = specificDate.dayOfWeek.value
    val noOfDaysInTheMonth = specificDate.lengthOfMonth()

    val dateInRow = mutableListOf<List<Int>>()
    var dateInCurrentRow = mutableListOf<Int>()

    var n = firstDayOfMonth

    for(i in 1..noOfDaysInTheMonth) {
        n += 1
        dateInCurrentRow.add(i)
        if(n%7==0) {
            while(dateInCurrentRow.size<7) dateInCurrentRow.add(0,0)
            dateInRow.add(dateInCurrentRow.toList())
            dateInCurrentRow = mutableListOf()
        }
    }
    if(dateInCurrentRow.isNotEmpty()) {
        while(dateInCurrentRow.size<7) dateInCurrentRow.add(0)
        dateInRow.add(dateInCurrentRow)
    }
    return dateInRow.toList()
}

fun openPdf(
    context: Context,
    pdfUrl: String,
    navController: NavController
) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(pdfUrl), "application/pdf")
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val packageManager = context.packageManager
        val resolvedInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolvedInfo.isNotEmpty()) {
            context.startActivity(intent)
        }
        else {
            Toast.makeText(context, "No PDF reader found. Please install one.", Toast.LENGTH_LONG).show()
            val encodedPdfUrl = try {
                URLEncoder.encode(pdfUrl, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                pdfUrl
            }
            val pdfUrlLocal = "https://docs.google.com/gview?embedded=true&url=$encodedPdfUrl"
            navController.navigate(Screens.WebViewScreen.createRoute(pdfUrlLocal))
        }
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Error opening PDF. Please try again later.", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun AdminViewPostScreen(
    navController: NavController,
) {
    var item = infoPasser.value
    val verticalScroll = rememberScrollState()
    val showDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.verticalScroll(verticalScroll),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = item.title.toString(),
            fontFamily = urbanist,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        val month = getMonth(item.deadline.substring(3,5).toInt())
        Text(
            text = item.deadline.substring(0,2) + " " + month.lowercase() + " " + item.deadline.substring(6,10),
            fontFamily = urbanist,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "B.Tech I year",
            fontFamily = urbanist,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = item.author,
            fontFamily = urbanist,
            fontSize = 16.sp
        )
        Card(
            modifier = Modifier
                .clickable {
                    EditStateAdmin.value = true
                    navController.navigate(Screens.AdminCreatePostScreen.createRoute(mode = item.mode))
                },
            colors = CardDefaults.cardColors(Color(163, 127, 219))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Edit ",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = urbanist
                )
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = "Edit button",
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
            }
        }
        OutlinedTextField(
            value = item.description.toString(),
            enabled = false,
            onValueChange = {
                //do nothing
            },
            textStyle = TextStyle(
                fontFamily = urbanist,
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(300.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                )
        )
        Text(
            text = "IMPORTANT LINKS",
            fontFamily = urbanist,
            fontSize = 20.sp
        )

        if(item.link1.toString().isEmpty() && item.link2.toString().isEmpty()) {
            Text(
                text = "No Important links found",
                fontFamily = urbanist,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        else if(item.link1.toString().isNotEmpty()) {
            LinkCard(item.link1,navController)
        }
        else if(item.link2.toString().isNotEmpty()) {
            LinkCard(item.link2,navController)
        }
        Text(
            text = "CIRCULAR",
            fontFamily = urbanist,
            fontSize = 20.sp
        )
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 20.dp)
                .clickable {
                    Log.d("url",item.file_url.toString())
                    if(item.file_url.toString().endsWith(".jpg")) {
                        showDialog.value = true
                    }
                    else if(item.file_url.toString().endsWith(".pdf")) {
                        openPdf(context,item.file_url.toString(), navController)
                    }
                    else {
                        Toast.makeText(context, "No file found", Toast.LENGTH_SHORT).show()
                        return@clickable
                    }
                },
            colors = CardDefaults.cardColors(
                containerColor =  Color(163, 127, 219)
            )
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
            {
                Text(
                    text = "Click to view circular",
                    fontFamily = urbanist,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
                )
            }
        }
    }
    if(showDialog.value) {
        ShowImageInDialog(showDialog,item.file_url)
    }
}


@Composable
fun LinkCard(
    url : String?,
    navController: NavController
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 20.dp)
            .clickable {
                val url = url
                if (url.isNullOrEmpty() || !Patterns.WEB_URL.matcher(url).matches()) {
                    Toast.makeText(context, "Invalid URL", Toast.LENGTH_SHORT).show()
                } else {
                    val fixedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        "https://$url"
                    } else {
                        url
                    }
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fixedUrl))
                    val resolvedActivities = context.packageManager.queryIntentActivities(intent, 0)
                    if (resolvedActivities.isNotEmpty()) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "No app found to handle this link", Toast.LENGTH_SHORT).show()
                    }
                }
            },
        colors = CardDefaults.cardColors(
            containerColor =  Color(163, 127, 219)
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = url.toString(),
                fontFamily = urbanist,
                color = Color.White,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.7f).padding(horizontal = 5.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.copy_link_icon),
                contentDescription = "copy-link-icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        clipboardManager.setText( AnnotatedString(url.toString()))
                    }
                ,
                tint = Color.White
            )
        }
    }
}
