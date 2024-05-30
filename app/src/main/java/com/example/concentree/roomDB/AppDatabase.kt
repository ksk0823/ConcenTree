package com.example.concentree.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class, Tree::class, ForestTree::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getTreeDao(): TreeDao
    abstract fun getForestTreeDao(): ForestTreeDao

    companion object {
        private var database: AppDatabase? = null

        fun getDatabase(context: Context)= database ?: Room.databaseBuilder(
            context, AppDatabase::class.java, "appDB"
        ).build().also {
            database = it
        }
    }
}