package com.example.concentree.screens


import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.concentree.R
import com.example.concentree.roomDB.Tree
import com.example.concentree.viewmodel.AppViewModel

@Composable
fun ShopScreen(appViewModel: AppViewModel) {
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
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text("카테고리 1", modifier = Modifier.background(Color.Blue))
            }
            items(tree1) {
                CatItem(it = it)
            }
        }
    }
}



@Composable
private fun Cat(appViewModel: AppViewModel) {
    val cat = "카테고리1"
    val list = listOf("")

    Column() {
        Text(cat)
        
    }

}


@Composable
private fun CatItem(it: Tree) {
    val price = it.price.toString() // it.price
    val image = 1
    val title = it.name // it.name
    val description = it.description // it.description
    Column() {
        Box(contentAlignment = Alignment.TopStart) {
            Row() {
                Icon(painter = painterResource(id = R.drawable.baseline_attach_money_24), contentDescription = "dollar sign")
                Text(price)
            }
//            Image(painter = image, contentDescription = "tree image")
        }
        Text(title)
        Text(description)
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