package com.example.concentree.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.concentree.R
import com.example.concentree.roomDB.ForestTree
import com.example.concentree.roomDB.Phrase
import com.example.concentree.roomDB.Tree
import com.example.concentree.viewmodel.AppViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun GrowthScreen(viewModel: AppViewModel) {
    val context = LocalContext.current

    var timeLeftSeconds by remember { mutableStateOf(0) }
    var initSeconds by remember { mutableStateOf(0) }
    var buttonText by remember { mutableStateOf("중지") }
    var showPopup by remember { mutableStateOf(false) }
    var treeImage by remember { mutableStateOf(R.drawable.plus) }
    var treeStage by remember { mutableStateOf(0) }
    var timerStarted by remember { mutableStateOf(false) }

    var phraseList by remember { mutableStateOf<List<Phrase>>(emptyList()) }
    var treeList by remember { mutableStateOf<List<Tree>>(emptyList()) }
    val init_phrase = "Press + To Grow Tree"
    var phraseToShow by remember { mutableStateOf(init_phrase) }

    var startTime by remember { mutableStateOf(LocalDateTime.MIN) }
    var settingTree by remember { mutableStateOf<Tree>(Tree(0,"init","",0,false)) }
    var settingId by remember { mutableStateOf(0) }
    var settingDescription by remember { mutableStateOf("") }

    val user by viewModel.user.collectAsState()
    val phrases by viewModel.phraseList.collectAsState()
    val trees by viewModel.treeList.collectAsState()

    SideEffect {
        viewModel.getAllPhrases()
        viewModel.getTreesToGrow()
        phraseList = phrases
        treeList = trees
    }

    LaunchedEffect(Unit) {
        viewModel.getAllPhrases()
        viewModel.getTreesToGrow()
        phraseList = phrases
        treeList = trees
    }

    LaunchedEffect(timerStarted) {
        if (timerStarted) {
            phraseToShow = phraseList.randomOrNull()?.phrase ?: ""
            initSeconds = timeLeftSeconds
        }
        if (timerStarted && timeLeftSeconds > 0) {
            var secondsCounter = 0
            while (timeLeftSeconds > 0) {
                if(timeLeftSeconds == initSeconds*2/3) buttonText = "얻기"
                delay(1000L)
                timeLeftSeconds -= 1
                secondsCounter++
                if (secondsCounter % 60 == 0) {  // Update phraseToShow every minute
                    phraseToShow = phraseList.randomOrNull()?.phrase ?: ""
                }
            }
            if (timeLeftSeconds == 0) {
                buttonText = "완료"
            }
        }
    }

    LaunchedEffect(timeLeftSeconds) {
        if(timeLeftSeconds == 0 && initSeconds != 0){
            treeStage = 3
            val imgFindString = settingTree.name+"_level3"
            val imageResourceId = context.resources.getIdentifier(imgFindString, "drawable", context.packageName)
            treeImage =  imageResourceId
        }
        else if(0 < timeLeftSeconds && timeLeftSeconds <= initSeconds * 1/3){
            treeStage = 2
            val imgFindString = settingTree.name+"_level2"
            val imageResourceId = context.resources.getIdentifier(imgFindString, "drawable", context.packageName)
            treeImage =  imageResourceId
        }
        else if(initSeconds * 1/3 < timeLeftSeconds && timeLeftSeconds <= initSeconds * 2/3){
            treeStage = 1
            val imgFindString = settingTree.name+"_level1"
            val imageResourceId = context.resources.getIdentifier(imgFindString, "drawable", context.packageName)
            treeImage =  imageResourceId
        }
        else if(initSeconds * 2/3 < timeLeftSeconds)  {
            treeStage = 0
            treeImage = R.drawable.seed
        }
        else if(initSeconds == 0){
            treeStage = 0
            treeImage = R.drawable.plus
        }
    }

    LaunchedEffect(initSeconds) {
        if(initSeconds == 0){
            treeStage = 0
            treeImage = R.drawable.plus
            phraseToShow = init_phrase
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(painter = painterResource(id = R.drawable.coin), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${user?.coins ?: 0}")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = phraseToShow, fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.LightGray, shape = CircleShape)
                    .clickable { showPopup = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(id = treeImage), contentDescription = null, modifier = Modifier.size(100.dp))
            }

            Text(text = formatTime(timeLeftSeconds), fontSize = 24.sp, modifier = Modifier.padding(top = 16.dp))
            Button(onClick = {
                if(treeStage == 0){
                    // 중단 눌렀으니 초기화만 해주기
                    initSeconds = 0
                    timeLeftSeconds = 0
                    timerStarted = false
                }
                else{
                    val endTime = LocalDateTime.now()
                    settingId = settingTree.id
                    val newForestTree = ForestTree(
                        treeId = settingId,
                        startTime = startTime,
                        endTime = endTime,
                        treeStage = treeStage,
                        onForest = false,
                        taskDescription = settingDescription,
                        xPosition = 0,
                        yPosition = 0,
                        forestId = 0,
                        color = 1
                    )
                    // ForestTree DB에 추가
                    viewModel.insertForestTree(newForestTree)
                    // User coins 업데이트
                    user?.let { user ->
                        val updatedCoins = user.coins + 10 * (newForestTree.treeStage)
                        viewModel.updateUserCoins(user.id, updatedCoins)
                    }
                    // 완료 눌렀으니 초기화 해주기
                    initSeconds = 0
                    timeLeftSeconds = 0
                    timerStarted = false
                }
            }) {
                Text(text = buttonText)
            }
        }

        if (showPopup) {
            TreeSelectionPopup(viewModel, treeList = treeList, onDismiss = { showPopup = false }, onConfirm = { time, selectedTree,description ->
                timeLeftSeconds = timeToSeconds(time)  // Convert time to seconds
                treeImage = R.drawable.seed
                timerStarted = true  // Start the timer

                startTime = LocalDateTime.now()
                selectedTree?.let {
                    settingTree = selectedTree
                }
                settingDescription = description
            })
        }
    }
}

@Composable
fun TreeSelectionPopup(viewModel: AppViewModel, treeList: List<Tree>, onDismiss: () -> Unit, onConfirm: (String, Tree?, String) -> Unit) {
    var selectedTree by remember { mutableStateOf<Tree?>(null) }
    var description by remember { mutableStateOf("") }

    var time by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Select Tree", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

                treeList.forEach { tree ->
                    Text(
                        text = tree.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedTree = tree }
                            .background(if (selectedTree == tree) Color.LightGray else Color.Transparent)
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    value = time,
                    onValueChange = { newText ->
                        // Only keep digits and limit to 4 characters
                        val digitsOnly = newText.text.filter { it.isDigit() }.take(4)
                        if (digitsOnly.length <= 4) {
                            val result = digitsOnly.formatTimeAndMoveCursor()
                            time = result
                            if(digitsOnly.length == 4){
                                val parts = time.text.split(":").map { it.toInt() }
                                errorMessage = if ((parts[0] > 3) || (parts[0] == 3 && parts[1] > 0) || (parts[0] ==0 && parts[1] < 30)) {
                                    "시간은 3시간 이하, 30분 이상이여야 합니다."
                                } else {
                                    null
                                }
                            }
                        }
                    },
                    label = { Text("hh:mm") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = errorMessage != null,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                )
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = LocalTextStyle.current,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Task Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("취소")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        if(errorMessage==null){
                            onConfirm(time.text, selectedTree, description)
                            onDismiss()
                        }
                    }) {
                        Text("확인")
                    }
                }
            }
        }
    }
}

fun TextFieldValue.cursorToEnd(): TextFieldValue {
    return copy(selection = TextRange(this.text.length))
}

private fun String.formatTimeAndMoveCursor(): TextFieldValue {
    val formattedString = if (length <= 3) {
        this
    } else {
        "${substring(0, 2)}:${substring(2, minOf(length, 4))}"
    }
    return TextFieldValue(formattedString).cursorToEnd()
}

fun formatTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

fun timeToSeconds(time: String): Int {
    val parts = time.split(":").map { it.toInt() }
    return parts[0] * 3600 + parts[1] * 60
}
