package com.example.dragitem.fragment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dragitem.CheckCallButtonState

@Dao
interface CheckCallButtonStateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCheckCallButtonState(vararg checkCallButtonState: CheckCallButtonState)

    @Query("SELECT * FROM CheckCallButtonState")
    fun getStateCheckCallButton(): LiveData<CheckCallButtonState>

}