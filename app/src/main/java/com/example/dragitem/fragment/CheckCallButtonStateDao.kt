package com.example.dragitem.fragment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dragitem.CheckCallButtonState

@Dao
interface CheckCallButtonStateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCheckCallButtonState(vararg checkCallButtonState: CheckCallButtonState)

    @Query("SELECT * FROM CheckCallButtonState WHERE id='default_id'")
    fun getStateCheckCallButton(): LiveData<CheckCallButtonState>

    @Query("SELECT * FROM CheckCallButtonState")
    fun getStateCheckCallButtons(): LiveData<List<CheckCallButtonState>>

    @Query("UPDATE CheckCallButtonState SET state = :isVisible WHERE id='default_id'")
    fun setState(isVisible: Boolean)

    @Query("UPDATE CheckCallButtonState SET size = :sizeOfButton WHERE id='default_id'")
    fun setSize(sizeOfButton: Int)

    @Query("UPDATE CheckCallButtonState SET positionX = :x, positionY =:y where id ='default_id'")
    fun setPosition(x: Float, y: Float)
}