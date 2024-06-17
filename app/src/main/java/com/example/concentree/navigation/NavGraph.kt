package com.example.concentree.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.concentree.screens.ChartScreen
import com.example.concentree.screens.ForestScreen
import com.example.concentree.screens.GrowthScreen
import com.example.concentree.screens.SettingScreen
import com.example.concentree.screens.ShopScreen
import com.example.concentree.viewmodel.AppViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel : AppViewModel) {
    NavHost(navController = navController, startDestination = "Forest") {
        composable("Shop") {
            ShopScreen(viewModel)
        }
        composable("Forest") {
            ForestScreen(viewModel)
        }
        composable("Growth") {
            GrowthScreen(viewModel)
        }
        composable("Chart") {
            ChartScreen(viewModel)
        }
        composable("Setting") {
            SettingScreen(viewModel)
        }
    }
}