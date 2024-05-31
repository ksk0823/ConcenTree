package com.example.concentree.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import com.example.concentree.R

data class BarItem (val title :String, val selectIcon: Int, val onSelectedIcon : Int, val route:String)

object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = "Shop",
            selectIcon = R.drawable.shop_icon,
            onSelectedIcon = R.drawable.shop_select_icon,
            route = "Shop"
        ),
        BarItem(
            title = "Forest",
            selectIcon = R.drawable.forest_icon,
            onSelectedIcon = R.drawable.forest_select_icon,
            route = "Forest"
        ),
        BarItem(
            title = "Growth",
            selectIcon = R.drawable.clock_icon,
            onSelectedIcon = R.drawable.clock_select_icon,
            route = "Growth"
        ),
        BarItem(
            title = "Chart",
            selectIcon = R.drawable.chart_icon,
            onSelectedIcon = R.drawable.chart_select_icon,
            route = "Chart"
        ),
        BarItem(
            title = "Setting",
            selectIcon = R.drawable.setting_icon,
            onSelectedIcon = R.drawable.setting_select_icon,
            route = "Setting"
        )
    )
}