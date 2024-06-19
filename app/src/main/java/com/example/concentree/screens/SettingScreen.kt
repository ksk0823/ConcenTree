package com.example.concentree.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.concentree.roomDB.Phrase
import com.example.concentree.roomDB.User
import com.example.concentree.ui.theme.ConcenTreeTheme
import com.example.concentree.viewmodel.AppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(viewModel: AppViewModel) {
    var showNicknameDialog by remember { mutableStateOf(false) }
    var showAddPhraseDialog by remember { mutableStateOf(false) }
    var showEditPhraseDialog by remember { mutableStateOf(false) }
    var showDeletePhraseDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showCreditsDialog by remember { mutableStateOf(false) }

    var shownickname by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var newPhrase by remember { mutableStateOf("") }
    var phraseToEdit by remember { mutableStateOf<Phrase?>(null) }
    var phraseToDelete by remember { mutableStateOf<Phrase?>(null) }

    val phrases by viewModel.phraseList.collectAsState()
    val user by viewModel.user.collectAsState()

    var phraseList by remember { mutableStateOf<List<Phrase>>(emptyList()) }

    SideEffect {
        viewModel.getAllPhrases()
        phraseList = phrases
    }

    LaunchedEffect(Unit) {
        viewModel.getAllPhrases()
        viewModel.getUserById(1)  // Assuming user ID 1 for the current user
        nickname = if (user != null) user?.username.toString() else ""
        shownickname = nickname
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top bar
        TopAppBar(
            title = { Text(text = "설정") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Settings Box
        SettingCategoryBox(title = "사용자 설정") {
            SettingItem(text = "닉네임 설정 [ 현재 닉네임 : ${shownickname} ]") { showNicknameDialog = true }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Phrase Settings Box
        SettingCategoryBox(title = "문구 설정") {
            SettingItem(text = "문구 추가") { showAddPhraseDialog = true }
            SettingItem(text = "문구 수정") { showEditPhraseDialog = true }
            SettingItem(text = "문구 삭제") { showDeletePhraseDialog = true }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Other Information Box
        SettingCategoryBox(title = "기타 정보") {
            SettingItem(text = "도움말") { showHelpDialog = true }
            SettingItem(text = "크레딧") { showCreditsDialog = true }
        }

        // Nickname Dialog
        if (showNicknameDialog) {
            InputDialog(
                title = "닉네임 설정",
                textFieldValue = nickname,
                onValueChange = { nickname = it },
                onConfirm = {
                    user?.let {
                        viewModel.UpdateUser(it.copy(username = nickname))
                        viewModel.getUserById(1)
                    }
                    shownickname = nickname
                    showNicknameDialog = false
                },
                onDismiss = {
                    nickname = ""
                    showNicknameDialog = false
                }
            )
        }

        // Add Phrase Dialog
        if (showAddPhraseDialog) {
            InputDialog(
                title = "문구 추가",
                textFieldValue = newPhrase,
                onValueChange = { newPhrase = it },
                onConfirm = {
                    viewModel.InsertPhrase(Phrase(id = 0, phrase = newPhrase))
                    newPhrase = ""
                    showAddPhraseDialog = false
                },
                onDismiss = {
                    newPhrase = ""
                    showAddPhraseDialog = false
                }
            )
        }

        // Edit Phrase Dialog
        if (showEditPhraseDialog) {
            ListDialog(
                title = "문구 수정",
                items = phraseList,
                onItemClick = { index ->
                    phraseToEdit = phraseList[index]
                    showEditPhraseDialog = false
                },
                onDismiss = { showEditPhraseDialog = false }
            )
        }

        // Handle phrase editing
        phraseToEdit?.let { phrase ->
            InputDialog(
                title = "문구 수정",
                textFieldValue = phrase.phrase,
                onValueChange = { updatedPhrase ->
                    phraseToEdit = phrase.copy(phrase = updatedPhrase)
                },
                onConfirm = {
                    viewModel.UpdatePhrase(phraseToEdit!!)
                    phraseToEdit = null
                },
                onDismiss = { phraseToEdit = null }
            )
        }

        // Delete Phrase Dialog
        if (showDeletePhraseDialog) {
            ListDialog(
                title = "문구 삭제",
                items = phraseList,
                onItemClick = { index ->
                    phraseToDelete = phraseList[index]
                    showDeletePhraseDialog = false
                },
                onDismiss = { showDeletePhraseDialog = false }
            )
        }

        // Handle phrase deletion
        phraseToDelete?.let { phrase ->
            ConfirmDialog(
                title = "문구 삭제",
                message = "정말 삭제하시겠습니까?",
                onConfirm = {
                    viewModel.DeletePhrase(phrase)
                    phraseToDelete = null
                },
                onDismiss = { phraseToDelete = null }
            )
        }

        // Help Dialog
        if (showHelpDialog) {
            InfoDialog(
                title = "도움말",
                message = "\nGrowth에서 자신만의 나무를 키우고\n\n" +
                        "Forest에서 나만의 숲을 키워보세요!\n\n" +
                        "Shop에서 나무와 컬러를 구매하고\n\n" +
                        "Chart에서 자신의 기록을 확인하세요.",
                onDismiss = { showHelpDialog = false }
            )
        }

        // Credits Dialog
        if (showCreditsDialog) {
            InfoDialog(
                title = "크레딧",
                message = "\n2팀 - ConcentTree\n\n" +
                        "2024 1학기 모바일 프로그래밍\n" +
                        "[고상진 김수경 이지원 임제형]",
                onDismiss = { showCreditsDialog = false }
            )
        }
    }
}

@Composable
fun SettingCategoryBox(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Divider(
                color = Color.Gray.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            content()
        }
    }
}

@Composable
fun SettingItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        color = Color.Black
    )
}

@Composable
fun InputDialog(
    title: String,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = textFieldValue, onValueChange = onValueChange)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onConfirm) {
                        Text("확인")
                    }
                }
            }
        }
    }
}

@Composable
fun ListDialog(
    title: String,
    items: List<Phrase>,
    onItemClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(items.size) { index ->
                        Text(
                            text = items[index].phrase,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemClick(index) }
                                .padding(vertical = 8.dp),
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onDismiss) {
                        Text("취소")
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = message)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onConfirm) {
                        Text("예")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onDismiss) {
                        Text("아니오")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = message)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onDismiss) {
                        Text("닫기")
                    }
                }
            }
        }
    }
}

