package com.example.concentree.screens

import android.graphics.drawable.Icon
import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.concentree.roomDB.Tree
import com.example.concentree.viewmodel.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.toList
import kotlin.coroutines.CoroutineContext

@Composable
fun ShopScreen(appViewModel: AppViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { 
        // TODO: 테스트용 데이터 넣는부분 시작 나중에 꼭 지우기
            val tt = Tree(1, "ㅁㄴㅁㄴ", "ㅂㅈㅂㅈ", 100, true)
            appViewModel.InsertTree(tt)


            // TODO: 테스트용 데이터 넣는부분 끝 나중에 꼭 지우기
        }) {
         Text(text = "as")
        }
        appViewModel.getTreesInShop()
        val trees by appViewModel.treeList.collectAsState(initial = emptyList()) // List<Tree>
        Log.d("treeList", trees.toString())
        LazyColumn {
            items(trees) {
                Cat(appViewModel)
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
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(list) {
//                CatItem(it)
            }
        }
    }

}


@Composable
private fun CatItem(it: Tree) {
    val price = "100" // it.price
    val image = 1
    val title = "사과나무" // it.name
    val description = "설명설명" // it.description
    Column() {
        Box(contentAlignment = Alignment.TopStart) {
            Row() {
                Icon(Icons.Outlined.Info, contentDescription = "dollar sign")
                Text(price)
            }
//            Image(painter = image, contentDescription = "tree image")
        }
        Text(title)
        Text(description)
    }
}