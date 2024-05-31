package com.example.concentree.components

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.concentree.navigation.NavBarItems
import com.example.concentree.navigation.Routes

@Composable
fun ConcenTreeNavigationBar(navController: NavController){
    NavigationBar(
        containerColor = Color.Transparent
    ){
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(Routes.Growth.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter =
                        if (currentRoute == navItem.route) {
                            painterResource(id = navItem.onSelectedIcon)
                        }
                        else {
                            painterResource(id = navItem.selectIcon)
                        },
                        contentDescription = navItem.title,
                        tint = if (currentRoute == navItem.route) {
                            MaterialTheme.colorScheme.onSecondary // 선택된 상태의 아이콘 색상
                        } else {
                            MaterialTheme.colorScheme.secondary // 선택되지 않은 상태의 아이콘 색상
                        }
                    )
                },
                label = {
                    Text(text = navItem.title, modifier = Modifier.offset(y = 8.dp))
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}