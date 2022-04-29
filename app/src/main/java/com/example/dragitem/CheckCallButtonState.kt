package com.example.dragitem

import android.content.res.Resources
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CheckCallButtonState")
class CheckCallButtonState(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "default_id",

    @ColumnInfo(name = "positionX")
    var x: Float = 0.0f,

    @ColumnInfo(name = "positionY")
    var y: Float = 0f,

    @ColumnInfo(name = "state")
    var state: Boolean = true,

    @ColumnInfo(name = "size")
    var size: Int = 0
){
    fun getSizeButton(resources: Resources): Float{
        return when (size){
            0 -> resources.getDimension(R.dimen.large)
            1 -> resources.getDimension(R.dimen.medium)
            else ->  resources.getDimension(R.dimen.small)
        }
    }

    fun toMap(): String {
        return "x: $x || y: $y || s: $size"
    }
}
