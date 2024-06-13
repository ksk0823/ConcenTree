package com.example.concentree.screens

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concentree.R
import com.example.concentree.roomDB.ForestTree
import com.example.concentree.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import com.patrykandpatrick.vico.*

@Composable
fun ChartScreen(appViewModel:AppViewModel) {
    val currentYear = remember { mutableStateOf(LocalDate.now().year) }
    val currentMonth = remember { mutableStateOf(LocalDate.now().monthValue) }

    val testvalue = remember {
        mutableStateOf(LocalDate.now())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = "ChartScreen",
//            fontSize = 40.sp,
//        )


    CalendarView(testvalue)


            val testData = listOf(
                LogEntry(date = LocalDate.now().minusDays(3).toEpochDay(), value = 10, name = "일"),
                LogEntry(date = LocalDate.now().minusDays(2).toEpochDay(), value = 20, name = "월"),
                LogEntry(date = LocalDate.now().minusDays(1).toEpochDay(), value = 1, name = "화"),
                LogEntry(date = LocalDate.now().toEpochDay(), value = 25, name = "수"),
                LogEntry(date = LocalDate.now().plusDays(1).toEpochDay(), value = 30, name = "목"),
                LogEntry(date = LocalDate.now().plusDays(2).toEpochDay(), value = 5, name = "금"),
                LogEntry(date = LocalDate.now().plusDays(3).toEpochDay(), value = 12, name = "토")
            )
            BarChart(testData)

            Spacer(modifier = Modifier.height(30.dp))

            // 테스트용
            LazyColumn() {
                items(4) {


                Row(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 10.dp, 0.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(36.dp)
                            .background(Color.Black)
                    ) {}
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "사과나무", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painterResource(id = R.drawable.baseline_access_time_24),
                                contentDescription = "",
                                tint = colorResource(
                                    id = R.color.palette3
                                ),
                                modifier = Modifier.size(12.dp)
                            )
                            Text(text = "1:13", fontSize = 12.sp)
                        }
                        Text(text = "내용내용", fontSize = 12.sp)
                    }
                }
                }
            }

//        appViewModel.getTreesInRange() // LocalDateTime
        // 우선 오늘 날짜 구해서 오늘에 해당하는 달력 보여주기ㅏ
    }
}



@Composable
fun CalendarView(selectedDate: MutableState<LocalDate>) {
    val pagerState = rememberPagerState(initialPage = YearMonth.now().monthValue - 1, pageCount = {12})
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage((pagerState.currentPage - 1).coerceAtLeast(0))
                }
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
            }

            Text(
                text = "${YearMonth.of(LocalDate.now().year, pagerState.currentPage + 1).year}년 ${YearMonth.of(LocalDate.now().year, pagerState.currentPage + 1).month.value}월",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage((pagerState.currentPage + 1).coerceAtMost(11))
                }
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val yearMonth = YearMonth.of(LocalDate.now().year, page + 1)
            val daysInMonth = yearMonth.lengthOfMonth()
            val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek

            Column {
                DaysOfWeekHeader()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    val emptyDays = firstDayOfMonth.value % 7
                    items(emptyDays) {
                        Box(modifier = Modifier.padding(4.dp))
                    }

                    items(daysInMonth) { day ->
                        val date = LocalDate.of(yearMonth.year, yearMonth.month, day + 1)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(if (date == selectedDate.value) Color.Blue else Color.Transparent)
                                .clickable {
                                    selectedDate.value = date
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = (day + 1).toString(),
                                color = if (date == selectedDate.value) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DaysOfWeekHeader() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        val d = listOf("일", "월", "화", "수", "목", "금", "토")
        d.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun BarChart(entries: List<LogEntry>) {
    val maxValue = (entries.maxOfOrNull { it.value } ?: 0).toFloat()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        entries.forEach { entry ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val heightFraction = entry.value / maxValue
            Box(
                modifier = Modifier
                    .height(((200 - 24) * heightFraction).dp)
                    .width(20.dp)
                    .background(Color.Green)
            )
                Text(entry.name, modifier = Modifier.height(24.dp))
            }
        }
    }
}
@Composable
fun LogList(entries: List<LogEntry>) {
    LazyColumn {
        items(entries) { entry ->
            Text(text = "Date: ${entry.date}, Value: ${entry.value}")
        }
    }
}

data class LogEntry(
    val id: Int = 0,
    val date: Long,
    val value: Int,
    val name:String
)
@Composable
fun LogItem(it: ForestTree) {
    Row(modifier = Modifier
        .padding(10.dp, 0.dp, 10.dp, 0.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .width(24.dp)
            .height(36.dp)
            .background(Color.Black)) { }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "사과나무", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(painterResource(id = R.drawable.baseline_access_time_24), contentDescription = "", tint = colorResource(
                    id = R.color.palette3
                ), modifier = Modifier.size(12.dp))
                Text(text = "1:13", fontSize = 12.sp)
            }
            Text(text = "내용내용", fontSize = 12.sp)
        }
    }
}