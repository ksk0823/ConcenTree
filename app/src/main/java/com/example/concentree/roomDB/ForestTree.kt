package com.example.concentree.roomDB

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

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
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val treeId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val treeStage: Int,
    val onForest: Boolean,
    val taskDescription: String,
    val xPosition: Int,
    val yPosition: Int,
    var forestId: Int,
    val color: Int
)