package com.example.concentree.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.concentree.viewmodel.AppViewModel
import java.time.LocalDate

@Composable
fun ChartScreen(appViewModel:AppViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ChartScreen",
            fontSize = 40.sp,
        )

        val currentYear = remember { mutableStateOf(LocalDate.now().year) }
        val currentMonth = remember { mutableStateOf(LocalDate.now().monthValue) }

//        appViewModel.getTreesInRange() // LocalDateTime
    }
}