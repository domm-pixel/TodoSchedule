package com.dom.todo.model.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Schedule(
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "contents") val contents: String?,
    @ColumnInfo(name = "checked") val checked: Boolean?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}