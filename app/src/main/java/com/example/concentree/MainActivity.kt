package com.example.concentree

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.concentree.components.ConcenTreeNavigationBar
import com.example.concentree.navigation.NavGraph
import com.example.concentree.roomDB.AppDatabase
import com.example.concentree.roomDB.Tree
import com.example.concentree.roomDB.User
import com.example.concentree.ui.theme.ConcenTreeTheme
import com.example.concentree.viewmodel.AppViewModel
import com.example.concentree.viewmodel.AppViewModelFactory
import com.example.concentree.viewmodel.ForestTreeRepository
import com.example.concentree.viewmodel.PhraseRepository
import com.example.concentree.viewmodel.TreeRepository
import com.example.concentree.viewmodel.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConcenTreeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var navController = rememberNavController()
                    MainScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val viewModel : AppViewModel = viewModel(factory =
    AppViewModelFactory(PhraseRepository(db), ForestTreeRepository(db), TreeRepository(db), UserRepository(db)))
    LaunchedEffect(Unit) {
        /*val t0 = Tree(0, "apple", "사과나무", 100, true)
        viewModel.InsertTree(t0)
        val t1 = Tree(1, "birch", "자작나무", 100, true)
        viewModel.InsertTree(t1)
        val t2 = Tree(2, "cedar", "삼나무", 100, true)
        viewModel.InsertTree(t2)
        val t3 = Tree(3, "fir", "전나무", 100, true)
        viewModel.InsertTree(t3)
        val t4 = Tree(4, "maple", "단풍나무", 100, true)
        viewModel.InsertTree(t4)
        val t5 = Tree(5, "pine", "소나무", 100, true)
        viewModel.InsertTree(t5)
        val t6 = Tree(6, "spruce", "가문비나무", 100, true)
        viewModel.InsertTree(t6)*/
    }

    Scaffold(
        bottomBar = { ConcenTreeNavigationBar(navController)}
    ){
        Box(Modifier.padding(it)){
            NavGraph(navController, viewModel)
        }
//        val newTree0 = Tree(0, "basic", "기본나무", 0, true)
//        val newTree1 = Tree(1, "maple", "단풍나무", 300, true)
//        val newTree2 = Tree(2, "apple", "사과나무", 500, false)
//        val newTree3 = Tree(3, "pine", "소나무", 100, true)
//        viewModel.InsertTree(newTree0)
//        viewModel.InsertTree(newTree1)
//        viewModel.InsertTree(newTree2)
//        viewModel.InsertTree(newTree3)
    }
}