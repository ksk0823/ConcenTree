package com.example.concentree.screens


import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concentree.R
import com.example.concentree.roomDB.Tree
import com.example.concentree.viewmodel.AppViewModel

@Composable
fun ShopScreen(appViewModel: AppViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedTree by remember { mutableStateOf<Tree?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            LaunchedEffect(Unit) {
                appViewModel.getTreesInShop() // getTreesInShop() 메서드 호출
            }
            val trees by appViewModel.treeList.collectAsState(initial = emptyList()) // List<Tree>

//        Button(onClick = {
//        // TODO: 테스트용 데이터 넣는부분 시작 나중에 꼭 지우기
//            for(i in 1..10) {
//                val tt = Tree(i, "ㅁㄴㅁㄴ", "ㅂㅈㅂㅈ", 100, true)
//                appViewModel.InsertTree(tt)
//            }
//
//            // TODO: 테스트용 데이터 넣는부분 끝 나중에 꼭 지우기
//        }) {
//         Text(text = "as")
//        }

            Log.d("treeList", trees.toString())
            var tree1 = emptyList<Tree>()
            if (trees.lastIndex >= 3)
                tree1 = trees.subList(0, 4)

            var testList1 = listOf(
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "asas",
                    name = "첫번째 나무",
                    price = 100
                ),
                Tree(
                    id = 0,
                    isPurchased = true,
                    description = "qweqwea",
                    name = "두번째 나무",
                    price = 120
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "dfdfggdf",
                    name = "세번째 나무",
                    price = 200
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "qwedfgsdfg",
                    name = "네번째 나무",
                    price = 90
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "hjkjhgk",
                    name = "다섯번째 나무",
                    price = 140
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "cvbnx",
                    name = "여섯번째 나무",
                    price = 1000
                )
            )
            var testList2 = listOf(
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "asas",
                    name = "첫번째 나무2",
                    price = 100
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "qweqwea",
                    name = "두번째 나무2",
                    price = 120
                ),
                Tree(
                    id = 0,
                    isPurchased = true,
                    description = "dfdfggdf",
                    name = "세번째 나무2",
                    price = 200
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "qwedfgsdfg",
                    name = "네번째 나무2",
                    price = 90
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "hjkjhgk",
                    name = "다섯번째 나무2",
                    price = 140
                ),
                Tree(
                    id = 0,
                    isPurchased = false,
                    description = "cvbnx",
                    name = "여섯번째 나무2",
                    price = 1000
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Cat("카테고리 1")
                }
                items(testList1) {
                    CatItem(it = it, onClick = {
                        selectedTree = it
                        showDialog = true
                    })
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Cat("카테고리 2")
                }
                items(testList2) {
                    CatItem(it = it, onClick = {
                        selectedTree = it
                        showDialog = true
                    })
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Cat("카테고리 3")
                }
                items(testList2) {
                    CatItem(it = it, onClick = {
                        selectedTree = it
                        showDialog = true
                    })
                }
            }
        }

        Row(modifier = Modifier
            .wrapContentSize()
            .padding(0.dp, 60.dp, 40.dp, 0.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_attach_money_24),
                contentDescription = "dollar sign",
                modifier = Modifier.size(30.dp)
            )
            Text(text = "100", fontSize = 20.sp, modifier = Modifier.offset(-6.dp, 0.dp))
        }

    }

    if (showDialog) {
        selectedTree?.let { tree ->
            BuyDialog(
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    // 구매 확정 처리
                    showDialog = false
                },
                dialogTitle = "구매 확인",
                dialogText = "${tree.name}을(를) ${tree.price}에 구매하시겠습니까?",
                icon = Icons.Outlined.Info
            )
        }
    }
}


@Composable
private fun Cat(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Black,
        modifier = Modifier.padding(0.dp, 50.dp, 0.dp, 8.dp)
    )
}


@Composable
private fun CatItem(it: Tree, onClick: () -> Unit) {
    val price = it.price.toString() // it.price
    val image = 1
    val title = it.name // it.name
    val description = it.description // it.description
    var modifier = Modifier
    if (!it.isPurchased) {
        modifier.clickable { onClick() }
    }
    Column(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .size(107.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
        ) {
            if (it.isPurchased) {
                Text(text = "SoldOut", modifier = Modifier.width(107.dp), textAlign = TextAlign.Center, lineHeight = 107.sp, fontSize = 24.sp, fontWeight = FontWeight.Black)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(41.dp)
                    .height(18.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStart = CornerSize(8.dp),
                            topEnd = CornerSize(0.dp),
                            bottomEnd = CornerSize(8.dp),
                            bottomStart = CornerSize(0.dp)
                        )
                    )
                    .border(
                        1.dp,
                        Color.Gray,
                        shape = RoundedCornerShape(
                            topStart = CornerSize(8.dp),
                            topEnd = CornerSize(0.dp),
                            bottomEnd = CornerSize(8.dp),
                            bottomStart = CornerSize(0.dp)
                        )
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_attach_money_24),
                    contentDescription = "dollar sign",
                    modifier = Modifier.size(16.dp)
                )
                Text(price, fontSize = 12.sp, modifier = Modifier.offset(-2.dp, 0.dp))
            }
        }
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(description, fontSize = 12.sp)
    }
}


@Composable
private fun BuyDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}