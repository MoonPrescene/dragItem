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

    @Query("SELECT * FROM CheckCallButtonState")
    fun getStateCheckCallButton(): LiveData<CheckCallButtonState>

    @Query("UPDATE CheckCallButtonState SET state = :isVisible WHERE id='default_id'")
    fun setState(isVisible: Boolean)

}