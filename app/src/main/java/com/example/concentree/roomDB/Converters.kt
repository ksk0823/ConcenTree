package com.example.concentree.roomDB

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    // Room DB에서 Date 타입 - Long 타입 변환 가능하도록 설정
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
    }
}