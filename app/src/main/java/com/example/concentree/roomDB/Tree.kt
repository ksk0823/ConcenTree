package com.example.concentree.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tree")

data class Tree(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val isPurchased: Boolean
)