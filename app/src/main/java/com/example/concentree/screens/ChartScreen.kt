package com.example.concentree.screens

import android.util.Log
import android.widget.CalendarView
import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.concentree.roomDB.Tree
import com.example.concentree.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import com.patrykandpatrick.vico.*
import java.time.LocalDateTime
import java.time.LocalTime
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.platform.LocalContext
import androidx.room.PrimaryKey
import java.time.Duration


@Composable
fun ChartScreen(appViewModel:AppViewModel) {
    val currentYear = remember { mutableStateOf(LocalDate.now().year) }
    val currentMonth = remember { mutableStateOf(LocalDate.now().monthValue) }

    val selectedDate = remember {
        mutableStateOf(LocalDate.now())
    }


    val forest = remember { mutableStateListOf<List<ForestTree>>() }


//    val forestTreeList by appViewModel.forestTreeListLJW.observeAsState(initial = emptyList())
//
//    LaunchedEffect(selectedDate.value) {
//        val startOfWeek = selectedDate.value.with(DayOfWeek.MONDAY).minusDays(1)
//        val endOfWeek = selectedDate.value.with(DayOfWeek.SATURDAY)
//        Log.d("qwqw", "${startOfWeek} -> ${endOfWeek}")
//        Log.d("qwqw", "${LocalDateTime.of(startOfWeek, LocalTime.MIN)} -> ${LocalDateTime.of(endOfWeek, LocalTime.MAX)}")
//        appViewModel.getTreesInRange(
//            LocalDateTime.of(startOfWeek, LocalTime.MIN),
//            LocalDateTime.of(endOfWeek, LocalTime.MAX)
//        )
//    }

    val forestTreeList = listOf(ForestTree(
        treeId = 1,  // Ensure this treeId exists in the Tree table
        startTime = LocalDateTime.now().minusHours(2),
        endTime = LocalDateTime.now(),
        treeStage = 2,
        onForest = true,
        taskDescription = "Pruning",
        xPosition = 20,
        yPosition = 25,
        forestId = 1,
        color = 2
    ))

    Log.d("qwqw", forestTreeList.toString())

//    LaunchedEffect(selectedDate.value) {
//        forest.clear()
//
//        val startOfWeek = selectedDate.value.with(DayOfWeek.SUNDAY)
//        val weekDatesWithTimes = (0..6).map {
//            val date = startOfWeek.plusDays(it.toLong())
//            val startOfDay = LocalDateTime.of(date, LocalTime.MIN)  // 00:00
//            val endOfDay = LocalDateTime.of(date, LocalTime.MAX)    // 23:59
//            startOfDay to endOfDay
//        }
//
//        weekDatesWithTimes.forEach { (start, end) ->
//            appViewModel.getTreesInRange(start, end)
//            val temp = appViewModel.forestTreeList.collectAsState(initial = null).value
//            temp?.let { forest.add(it) }
//        }
//    }

//    LaunchedEffect(selectedDate.value) {
//        val startOfWeek = selectedDate.value.with(DayOfWeek.SUNDAY)
//        val weekDatesWithTimes = (0..6).map {
//            val date = startOfWeek.plusDays(it.toLong())
//            val startOfDay = LocalDateTime.of(date, LocalTime.MIN)  // 00:00
//            val endOfDay = LocalDateTime.of(date, LocalTime.MAX)    // 23:59
//            appViewModel.getTreesInRange(startOfDay, endOfDay)
//        }
//    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


    CalendarView(selectedDate)

        //테스트용 데이터 삽입


//        var startOfWeek = selectedDate.value.with(DayOfWeek.MONDAY)
//        var lastSun = startOfWeek.minusDays(1)
//        var weekDatesWithTimes = (0..6).map {
//            val date = lastSun.plusDays(it.toLong())
//            val startOfDay = LocalDateTime.of(date, LocalTime.MIN)  // 00:00
//            val endOfDay = LocalDateTime.of(date, LocalTime.MAX)  // 23:59
//            startOfDay to endOfDay
//        }

//        val forest = remember { mutableStateListOf<List<ForestTree>>() }
//
//            forest.clear()
//
//            for (i in 0..6) {
//                val start = weekDatesWithTimes[i].first
//                val end = weekDatesWithTimes[i].second
//
//                appViewModel.getTreesInRange(start, end)
//                val temp = appViewModel.forestTreeList.collectAsState(initial = null).value
//
//                temp?.let { forest.add(it) }
//            }

        
         // var forest가지고 밑의 리스트 만들기

            BarChart(forestTreeList)

            Spacer(modifier = Modifier.height(30.dp))


//        forest?.let {
//        if(forest.size >= i+1) {
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(forest[i]) {
//                    LogItem(it = it, appViewModel = appViewModel)
//                }
//            }
//        }
//        }



//        val dayOfWeekIndex = when (selectedDate.value.dayOfWeek) {
//            DayOfWeek.SUNDAY -> 0
//            DayOfWeek.MONDAY -> 1
//            DayOfWeek.TUESDAY -> 2
//            DayOfWeek.WEDNESDAY -> 3
//            DayOfWeek.THURSDAY -> 4
//            DayOfWeek.FRIDAY -> 5
//            DayOfWeek.SATURDAY -> 6
//        }
//
//        if (forestTreeList.isNotEmpty() && forestTreeList.size > dayOfWeekIndex) {
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(items = forestTreeList[dayOfWeekIndex], key = {}) { forestTree ->
//                    LogItem(forestTree, appViewModel)
//                }
//            }
//        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(forestTreeList) { forestTree ->
                LogItem(forestTree, appViewModel, selectedDate?.value)
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

//@Composable

//@Composable
//fun BarChart(entries: List<ForestTree>) {
//    // 일주일간의 데이터를 그룹화
//    val weekData = (0..6).map { dayOfWeek ->
//        entries.filter { it.startTime.dayOfWeek.value == dayOfWeek + 1 }
//    }
//
//    // 최대 값을 계산
//    val maxValue = weekData.maxOfOrNull { dayList ->
//        dayList.sumOf { it.endTime.minute - it.startTime.minute }
//    } ?: 0
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//            .padding(20.dp, 0.dp, 20.dp, 0.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.Bottom
//    ) {
//        weekData.forEachIndexed { index, entry ->
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                val sum = entry.sumOf { it.endTime.minute - it.startTime.minute }
//                val heightFraction = if (maxValue != 0) sum.toFloat() / maxValue else 0f
//
//                Box(
//                    modifier = Modifier
//                        .height((200 * heightFraction).dp)
//                        .width(20.dp)
//                        .background(Color.Green)
//                )
//
//                val dayOfWeekText = when (index) {
//                    0 -> "일"
//                    1 -> "월"
//                    2 -> "화"
//                    3 -> "수"
//                    4 -> "목"
//                    5 -> "금"
//                    6 -> "토"
//                    else -> ""
//                }
//                Text(text = dayOfWeekText, modifier = Modifier.height(24.dp))
//            }
//        }
//    }
//}


@Composable
fun BarChart(entries: List<ForestTree>) {
    // 일주일간의 데이터를 그룹화
    val weekData = (0..6).map { dayOfWeek ->
        entries.filter { it.startTime.dayOfWeek.value == dayOfWeek + 1 }
    }

    // 최대 값을 계산
    val maxValue = weekData.maxOfOrNull { dayList ->
        dayList.sumOf {
            Log.d("qwqw", Duration.between(it.startTime, it.endTime).toMinutes().toString())
            Duration.between(it.startTime, it.endTime).toMinutes()
        }
    } ?: 0

    Log.d("qwqw", "maxValue = ${maxValue}")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        weekData.forEachIndexed { index, entry ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val sum = entry.sumOf {
                    Log.d("qwqw", "as: ${Duration.between(it.startTime, it.endTime).toMinutes()}")
                    Duration.between(it.startTime, it.endTime).toMinutes()
                }

                val heightFraction = if (maxValue.toInt() != 0) sum.toFloat() / maxValue else 0f
                Log.d("qwqw", "Frac: $heightFraction")
                Box(
                    modifier = Modifier
                        .height((128 * heightFraction).dp)
                        .width(20.dp)
                        .background(Color.Green)
                )

                val dayOfWeekText = when (index) {
                    0 -> "일"
                    1 -> "월"
                    2 -> "화"
                    3 -> "수"
                    4 -> "목"
                    5 -> "금"
                    6 -> "토"
                    else -> ""
                }
                Text(text = dayOfWeekText, modifier = Modifier.height(24.dp))
            }
        }
    }
}






//@Composable
//fun LogItem(it: ForestTree, appViewModel: AppViewModel) {
//    Row(modifier = Modifier
//        .padding(10.dp, 0.dp, 10.dp, 0.dp)
//        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//
//        var image:Int = R.drawable.sapling_0
//        appViewModel.getTreeById(it.treeId)
//        val treeId = appViewModel.tree.collectAsState(null)
//        treeId?.let {item ->
//            val treeName = when(item.value?.id) {
//            0 -> "apple"
//            1 -> "birch"
//            2 -> "cedar"
//            3 -> "fir"
//            4 -> "maple"
//            5 -> "pine"
//            6 -> "spruce"
//            else -> ""
//        }
//            val stage = it.treeStage
//
//            val color = it.color
//
//            val context = LocalContext.current
//            Log.d("qwqw", "${treeName}_level${stage}_${color}")
//            if(treeName == "") {
//                image = R.drawable.sapling_0
//            }
//            else {
//
//                image = context.resources.getIdentifier("${treeName}_level${stage}_${color}", "drawable", context.packageName)
//            }
//        }
//
//        Image(painter = painterResource(id = image), contentDescription = "", modifier = Modifier.height(36.dp))
//
//        Spacer(modifier = Modifier.width(8.dp))
//        Column {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                appViewModel.getTreeById(it.treeId)
//                val treeId = appViewModel.tree.collectAsState(null)
//                treeId.let {item ->
//                    Text(text = item.value?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Icon(painterResource(id = R.drawable.baseline_access_time_24), contentDescription = "", tint = colorResource(
//                        id = R.color.palette3
//                    ), modifier = Modifier.size(12.dp))
//
//                    val total = it.endTime.minute - it.startTime.minute
//
//                    Text(text = total.toString(), fontSize = 12.sp)
//                }
//            }
//            Text(text = it.taskDescription, fontSize = 12.sp)
//        }
//    }
//}

//@Composable
//fun LogItem(forestTree: ForestTree, appViewModel: AppViewModel) {
//    Row(
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        var imageResource: Int = R.drawable.sapling_0
//
//        // LiveData를 관찰
////        val tree by appViewModel.getTreeById(forestTree.treeId).observeAsState()
//        appViewModel.getTreeById(it.treeId)
//        val treeId = appViewModel.tree.collectAsState(null)
//        treeId?.let { item ->
//            val treeName = when (item.id) {
//                0 -> "apple"
//                1 -> "birch"
//                2 -> "cedar"
//                3 -> "fir"
//                4 -> "maple"
//                5 -> "pine"
//                6 -> "spruce"
//                else -> ""
//            }
//
//            val stage = forestTree.treeStage
//            val color = forestTree.color
//
//            val context = LocalContext.current
//            imageResource = context.resources.getIdentifier("${treeName}_level${stage}_${color}", "drawable", context.packageName)
//        }
//
//        Image(
//            painter = painterResource(id = imageResource),
//            contentDescription = "",
//            modifier = Modifier.height(36.dp)
//        )
//
//        Spacer(modifier = Modifier.width(8.dp))
//
//        Column {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(text = tree?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                Spacer(modifier = Modifier.width(8.dp))
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_access_time_24),
//                    contentDescription = "",
//                    tint = colorResource(id = R.color.palette3),
//                    modifier = Modifier.size(12.dp)
//                )
//
//                val totalMinutes = forestTree.endTime.minute - forestTree.startTime.minute
//                Text(text = "$totalMinutes 분", fontSize = 12.sp)
//            }
//            Text(text = forestTree.taskDescription, fontSize = 12.sp)
//        }
//    }
//}



@Composable
fun LogItem(forestTree: ForestTree, appViewModel: AppViewModel, selectedDate:LocalDate?) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var imageResource: Int = R.drawable.sapling_0

        // LiveData를 관찰
        appViewModel.getTreeById(forestTree.treeId)
        val treeList by appViewModel.forestTreeList.collectAsState(null)

        val dayOfWeekIndex = when (selectedDate?.dayOfWeek) {
            DayOfWeek.SUNDAY -> 0
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
            else -> 0
        }

        var treeName = ""
//        tree?.let { item ->
//            treeName = when (item[dayOfWeekIndex].treeId) {
//                0 -> "apple"
//                1 -> "birch"
//                2 -> "cedar"
//                3 -> "fir"
//                4 -> "maple"
//                5 -> "pine"
//                6 -> "spruce"
//                else -> ""
//            }
//
//            val stage = forestTree.treeStage
//            val color = forestTree.color
//
//            val context = LocalContext.current
//            imageResource = context.resources.getIdentifier("${treeName}_level${stage}_${color}", "drawable", context.packageName)
//        }

        treeList?.forEach { item ->
            treeName = when (item.treeId) {
                0 -> "apple"
                1 -> "birch"
                2 -> "cedar"
                3 -> "fir"
                4 -> "maple"
                5 -> "pine"
                6 -> "spruce"
                else -> ""
            }

            val stage = forestTree.treeStage
            val color = forestTree.color

            val context = LocalContext.current
            imageResource = context.resources.getIdentifier("${treeName}_level${stage}_${color}", "drawable", context.packageName)
        }

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "",
            modifier = Modifier.height(36.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = treeName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_access_time_24),
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${forestTree.startTime.hour}:${forestTree.startTime.minute.toString().padStart(2, '0')} - ${forestTree.endTime.hour}:${forestTree.endTime.minute.toString().padStart(2, '0')}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = forestTree.taskDescription,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
