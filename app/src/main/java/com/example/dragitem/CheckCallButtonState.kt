package com.example.dragitem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CheckCallButtonState")
class CheckCallButtonState(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,

    @ColumnInfo(name = "positionX")
    var x: Float = 0.0f,

    @ColumnInfo(name = "positionY")
    var y: Float = 0f,

    @ColumnInfo(name = "state")
    var state: Boolean = true,

    @ColumnInfo(name = "size")
    var size: Int = 0
)
