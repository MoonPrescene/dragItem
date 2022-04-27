package com.example.dragitem.fragment

import androidx.lifecycle.LiveData
import com.example.dragitem.CheckCallButtonState

class CheckCallButtonStateRepository(private val checkCallButtonStateDao: CheckCallButtonStateDao) {

    val getStateCheckCallButton: LiveData<CheckCallButtonState> = checkCallButtonStateDao
        .getStateCheckCallButton()

    fun insertCheckCallButtonState(checkCallButtonState: CheckCallButtonState){
        checkCallButtonStateDao.insertCheckCallButtonState(checkCallButtonState)
    }

    fun setState(isVisible: Boolean){
        checkCallButtonStateDao.setState(isVisible)
    }
}