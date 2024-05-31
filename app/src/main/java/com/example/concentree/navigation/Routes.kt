package com.example.concentree.navigation

sealed class Routes (val route: String) {
    object Shop : Routes("Shop")
    object Forest : Routes("Forest")
    object Growth : Routes("Growth")
    object Chart : Routes("Chart")
    object Setting : Routes("Setting")
}