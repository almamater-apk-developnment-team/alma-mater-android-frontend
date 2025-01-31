package com.nitt.common.navigationDeck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nitt.student.navigationDeck.searchBar

@Composable
fun adminAndAlumniScaffold(
    currentPage : @Composable () -> Unit,
    searchBar : Boolean,
    heading : String,
    navController: NavController
):String
{
    var searchType by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = {
            Column()
            {
                Header(heading = heading, navController = navController)
                if (searchBar) {
                    Spacer(modifier = Modifier.height(10.dp))
                    searchType = searchBar()
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier.background(color = Color.White)
            )
        },

        content = {paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                currentPage()
            }
        }
    )

    return searchType
}