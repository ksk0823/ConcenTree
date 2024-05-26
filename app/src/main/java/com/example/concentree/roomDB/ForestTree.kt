package com.example.concentree.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "forest_tree",
    foreignKeys = [ForeignKey(
        entity = Tree::class,
        parentColumns = ["id"],
        childColumns = ["treeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ForestTree(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val treeId: Int,
    val startTime: Long,
    val endTime: Long,
    val successful: Boolean,
    val taskDescription: String,
    val xPosition: Int,
    val yPosition: Int
)