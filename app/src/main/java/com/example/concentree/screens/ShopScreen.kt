package com.example.concentree.screens


import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.Image
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
import com.example.concentree.roomDB.User
import com.example.concentree.viewmodel.AppViewModel

@Composable
fun ShopScreen(appViewModel: AppViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedTree by remember { mutableStateOf<Tree?>(null) }
    appViewModel.getUserById(1)
    val user by appViewModel.user.collectAsState(initial = null)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            appViewModel.getTreesInShop() // getTreesInShop() 메서드 호출
            val trees by appViewModel.treeList.collectAsState(initial = emptyList()) // List<Tree>

            var colorsT = listOf<Tree>()

            user?.let {
                val colors = listOf(
                    "Pink" to user!!.colorPink,
                    "Red" to user!!.colorRed,
                    "Purple" to user!!.colorPurple,
                    "Yellow" to user!!.colorYellow,
                    "Green" to user!!.colorGreen,
                    "White" to user!!.colorWhite
                )

                colorsT = colors.map {
                    Tree(
                        id = 0,
                        description = "color",
                        name = it.first,
                        price = 100,
                        isPurchased = it.second
                    )
                }
            }



            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Cat("나무")
                }
                items(trees) {
                    CatItem(it = it, onClick = {
                        selectedTree = it
                        showDialog = true
                    })
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Cat("색깔")
                }
                items(colorsT) {
                    CatItem(it = it, onClick = {
                        selectedTree = it
                        showDialog = true
                    })
                }
            }
        }

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(0.dp, 60.dp, 40.dp, 0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_attach_money_24),
                contentDescription = "dollar sign",
                modifier = Modifier.size(30.dp)
            )
            Text(text = user?.coins.toString(), fontSize = 20.sp, modifier = Modifier.offset(-6.dp, 0.dp))
        }

    }

    if (showDialog) {
        selectedTree?.let { tree ->
            BuyDialog(
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    user?.let { currentUser ->
                        Log.d("qw", "${currentUser.coins} - ${tree.price}")
                        if (currentUser.coins >= tree.price) {
                            Log.d("qw", "qwqw")
                            // 코인 수 변화
                            appViewModel.updateUserCoins(
                                currentUser.id,
                                currentUser.coins - tree.price
                            )

                            if (tree.description != "color") {
                                val updatedTree = Tree(
                                    id = tree.id,
                                    name = tree.name,
                                    price = tree.price,
                                    isPurchased = true,
                                    description = tree.description
                                )
                                appViewModel.UpdateTree(updatedTree)
                            } else {
                                val updatedUser = when (tree.name) {
                                    "Pink" -> currentUser.copy(colorPink = true)
                                    "Red" -> currentUser.copy(colorRed = true)
                                    "Purple" -> currentUser.copy(colorPurple = true)
                                    "Yellow" -> currentUser.copy(colorYellow = true)
                                    "Green" -> currentUser.copy(colorGreen = true)
                                    "White" -> currentUser.copy(colorWhite = true)
                                    else -> currentUser
                                }
                                appViewModel.UpdateUser(updatedUser)
                            }
                        }
                    }

                    appViewModel.getUserById(1)
                    appViewModel.getTreesInShop()
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
    val title = it.name // it.name
    val description = it.description // it.description


    val image = when(it.id) {
        0 -> R.drawable.apple_level1_0
        1 -> R.drawable.birch_level1_0
        2 -> R.drawable.cedar_level1_0
        3 -> R.drawable.fir_level1_0
        4 -> R.drawable.maple_level1_0
        5 -> R.drawable.pine_level1_0
        6 -> R.drawable.spruce_level1_0
        else -> R.drawable.sapling_0
    }

    val color = when(it.name) {
        "Pink" -> Color(0xFFFFC0CB)
        "Green" -> Color.Green
        "Yellow" -> Color.Yellow
        "Red" -> Color.Red
        "Purple" -> Color.Magenta
        "White" -> Color.White
        else -> Color.Gray
    }
    Column(
        modifier = Modifier.clickable {
            if (!it.isPurchased) {
                onClick()
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .size(107.dp)
                .background(color = color, shape = RoundedCornerShape(8.dp))
        ) {
            if (it.description != "color") {
                Image(painter = painterResource(id = image), contentDescription = "", modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp))
            }
            if (it.isPurchased) {
                Text(
                    text = "SoldOut",
                    modifier = Modifier.width(107.dp),
                    textAlign = TextAlign.Center,
                    lineHeight = 107.sp,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )
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
        if (description != "color") {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(description, fontSize = 12.sp)
        } else {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

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