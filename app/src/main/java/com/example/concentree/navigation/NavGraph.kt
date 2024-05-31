package com.example.concentree.navigation

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.concentree.screens.ChartScreen
import com.example.concentree.screens.ForestScreen
import com.example.concentree.screens.GrowthScreen
import com.example.concentree.screens.SettingScreen
import com.example.concentree.screens.ShopScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Growth") {
        composable("Shop") {
            ShopScreen()
        }
        composable("Forest") {
            ForestScreen()
        }
        composable("Growth") {
            GrowthScreen()
        }
        composable("Chart") {
            ChartScreen()
        }
        composable("Setting") {
            SettingScreen()
        }
    }
}