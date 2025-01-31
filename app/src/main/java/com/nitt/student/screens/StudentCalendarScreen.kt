package com.nitt.student.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.nitt.R
import com.nitt.firebase.reminderToCalendar
import com.nitt.student.days
import com.nitt.student.viewModels.CalendarViewModel
import com.nitt.theme.color
import com.nitt.theme.color_2
import com.nitt.theme.urbanist
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.nitt.administration.response.Date
import com.nitt.administration.screens.AdminCard
import com.nitt.administration.viewModels.AdminDetailsViewModel
import com.nitt.student.sharedPreferences.getUserDetails
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCalendarScreen()
{
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val viewModel = remember { CalendarViewModel() }
    val isLoadingCustomReminder = viewModel.isLoading.collectAsState()
    val calendarEvents = viewModel.calendarEvents.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getCalendarDetails(context=context,scope = coroutineScope)
    }


    var showAdminReminders by remember {
        mutableStateOf(true)
    }
  // admin reminders
    val adminDetailsViewModel: AdminDetailsViewModel = viewModel()
    LaunchedEffect(Unit) {
        adminDetailsViewModel.fetchAdminDetails()
    }
    val detailsList = adminDetailsViewModel.detailsList
    val isLoading = adminDetailsViewModel.isLoading
    val loadingStatus = adminDetailsViewModel.loadingStatus

    var response by remember { mutableStateOf("") }
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf(today) }
    var showPopup by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    if(pullToRefreshState.isRefreshing)
    {
        LaunchedEffect(true) {
            isRefreshing = true
            viewModel.getCalendarDetails(context=context,scope = coroutineScope)
            delay(2000)
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
    val gradient = Brush.linearGradient(
        colors = listOf(Color(150, 103, 224), Color(188, 128, 240))
    )
    var date by remember{ mutableStateOf("") }
    var title by remember{ mutableStateOf("") }
    if(showPopup)
    {
        date = selectedDate.toString()
        Dialog(
            onDismissRequest = { showPopup = false },
        ) {
            Column(modifier = Modifier.fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(12.dp)),horizontalAlignment = Alignment.CenterHorizontally)
            {
                Column(modifier = Modifier.fillMaxWidth().padding(0.dp,10.dp),horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(text = "Get notification of events and important",color = Color(150, 103, 224), fontWeight = FontWeight.Bold, fontFamily = urbanist)
                    Text(text = "dates by saving it in your calendar", color = Color(150, 103, 224), fontWeight = FontWeight.Bold,fontFamily = urbanist)
                }
                Column(modifier = Modifier.fillMaxWidth().background(brush = gradient,shape = RoundedCornerShape(0.dp,0.dp,12.dp,12.dp)).fillMaxWidth(0.9f).padding(10.dp,10.dp,10.dp,0.dp),horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(text = "Title of the Reminder",modifier = Modifier.align(Alignment.Start),color= Color.White, fontSize = 18.sp, fontFamily = urbanist)
                    TextField(
                        value = title,
                        onValueChange = { title = it
                        },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = urbanist,
                            fontSize = 18.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth().padding(0.dp,0.dp,0.dp,10.dp).border(1.5.dp,
                            Color.Black, shape = RoundedCornerShape(12.dp)
                        ),
                    )
                    Text(text = "Reminder Date",modifier = Modifier.align(Alignment.Start) ,color= Color.White, fontSize = 18.sp, fontFamily = urbanist)
                    TextField(
                        value = date,
                        onValueChange = { date = it
                        },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = urbanist,
                            fontSize = 18.sp
                        ),
                        label = {
                            Text(
                                text = "YYYY-MM-DD",color= Color.Black,
                                fontFamily = urbanist
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth().padding(0.dp,0.dp,0.dp,10.dp).border(1.5.dp,
                            Color.Black, shape = RoundedCornerShape(12.dp)
                        ),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.calendar_icon),
                                contentDescription = "calendar_icon",
                                modifier = Modifier.clickable {
                                }.size(25.dp)
                            )
                        }
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                response = reminderToCalendar(title = title,date = date,context = context).toString()
                                Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                                title=""
                                date=""
                            }
                            showPopup = false
                        },
                        colors = ButtonColors(
                            containerColor = Color.Black,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(0.dp,0.dp,0.dp,10.dp)
                    ) {
                        Text(text = "Submit", fontSize = 18.sp,color = Color.White, fontFamily = urbanist)
                    }
                }
            }
        }
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(
                pullToRefreshState.nestedScrollConnection
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box()
        {
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                selectedDate = calendarScreen()
                Card(
                    modifier = Modifier
                        .clickable {
                            showPopup = true
                        }
                ) {
                    Row(
                        modifier = Modifier.background(brush = gradient).padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Custom",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontFamily = urbanist
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Plus",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = Color.Black,
                containerColor = Color.White
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Button(
                onClick = {
                    showAdminReminders = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(showAdminReminders) Color(0xFFA37FDB) else Color(0xFFCDC1FF),
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 4.dp
                    )
            )
            {
                Text(
                    text = "Admin Remiders",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    color = Color.Black
                )
            }
            Button(
                onClick = {
                    showAdminReminders = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(!showAdminReminders) Color(0xFFA37FDB) else Color(0xFFCDC1FF),
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 4.dp
                    )
            )
            {
                Text(
                    text = "Custom Reminders",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = urbanist,
                    color = Color.Black
                )
            }

        }
        val filteredList = detailsList.flatMap { user ->
            user.details.filter { post ->
                post.date.date.toString() == selectedDate.dayOfYear.toString() &&
                        post.date.monthInInt == selectedDate.monthValue &&
                        post.date.year == selectedDate.year
            }
        }
        val customfilteredList = calendarEvents.value.entries.filter {
            it.value == selectedDate.toString()
        }.toList()

        if(showAdminReminders)
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if(filteredList.isNotEmpty())
                {
                    items(filteredList){post ->
                        AdminReminderHandler(
                            event  = post.title to post.date
                        )
                    }
                }
                else
                {
                    item { Text(text = "No Admin Reminders on $selectedDate",  fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = urbanist) }
                }
            }
        }
        else
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if(isLoadingCustomReminder.value)
                {
                    item{
                        Text("its loading")
                    }
                }
                else if(errorMessage.value.isNotEmpty())
                {
                    item{
                        Text("its loading")
                    }
                }
                else
                {
                    if(customfilteredList.isNotEmpty())
                    {
                        items(customfilteredList) { item ->
                            EachDeadline(item)
                        }
                    }
                    else
                    {
                        item { Text(text = "No Custom Reminder on this date on $selectedDate",  fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = urbanist) }
                    }
                }
            }
        }
    }
}
@Composable
fun AdminReminderHandler(
    event : Pair<String, Date>
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(color_2),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 40.dp).background(color = Color.White).padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f).padding(start = 30.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp,Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = event.first,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = urbanist,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = event.second.date.toString() + " " + event.second.monthInString + " " + event.second.year ,
                    color = Color(141, 72, 203, 255),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = urbanist
                )
            }
        }
    }
}
@Composable
fun EachDeadline(
    event : Map.Entry<String, String>
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(color_2),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 40.dp).background(color = Color.White).padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.spacedBy(3.dp,Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = event.key,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = urbanist,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "Deadline: "+event.value.substring(8,10) + " " + getMonth(event.value.substring(5,7).toInt()) + " " + event.value.substring(0,4),
                    color = Color(141, 72, 203, 255),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = urbanist
                )
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.raw.dustbin)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "dust_bin",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(Color.Red)
            )
        }
    }
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
@Composable
fun calendarScreen(): LocalDate {
    val selectColor = Color(5, 3, 153).copy(alpha = 0.25f)
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf(today) }
    var selectedMonth by remember { mutableStateOf("") }
    val initialYear =  Year.now().minusYears(10).value
    val finalYear = Year.now().plusYears(10).value
    val startMonth = YearMonth.now()
    val state = rememberCalendarState(
        startMonth = YearMonth.of(initialYear,12),
        endMonth = YearMonth.of(finalYear,12),
        firstVisibleMonth = startMonth,
        firstDayOfWeek = firstDayOfWeekFromLocale(),
        outDateStyle = OutDateStyle.EndOfRow
    )
    val gradient = Brush.linearGradient(
        colors = listOf(Color(150, 103, 224), Color(188, 128, 240))
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Box(
            modifier = Modifier.padding(vertical = 10.dp).border(1.dp,Color.Black, shape = RoundedCornerShape(12.dp))
        )
        {
            Text(
                text = state.firstVisibleMonth.yearMonth.month.toString() + " " + state.firstVisibleMonth.yearMonth.year,
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = urbanist
            )
        }
        Column(modifier = Modifier.padding(20.dp,0.dp).background(brush = gradient,shape = RoundedCornerShape(16.dp)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
        {
            Row(
                modifier = Modifier.fillMaxWidth().padding(30.dp,7.dp,30.dp,0.dp), horizontalArrangement = Arrangement.SpaceAround
            )
            {
                for(i in days)
                {
                    Box(
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(text = i,  fontSize = 15.sp,
                            color = if(i == "Su" || i == "Sa") Color.Red else Color.White,
                            fontFamily = urbanist,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            HorizontalCalendar(
                state = state,
                modifier = Modifier.padding(horizontal = 30.dp),
                calendarScrollPaged = true,
                userScrollEnabled = true,
                dayContent = { day ->
                    if(day.date.month == state.firstVisibleMonth.yearMonth.month)
                    {
                        Box(
                            modifier = Modifier
                                .padding(3.dp)
                                .size(40.dp)
                                .background(if (day.date == today) color else if(day.date == selectedDate)  selectColor  else Color.Transparent, shape = RectangleShape)
                                .clickable {
                                    selectedDate = day.date
                                    selectedMonth = state.firstVisibleMonth.toString()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.date.dayOfMonth.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if(day.date.dayOfWeek == DayOfWeek.SUNDAY || day.date.dayOfWeek == DayOfWeek.SATURDAY) Color.Red else Color.White,
                                modifier = Modifier.align(Alignment.Center),
                                fontFamily = urbanist
                            )
                        }
                    }
                },
            )
        }
    }
    return selectedDate
}