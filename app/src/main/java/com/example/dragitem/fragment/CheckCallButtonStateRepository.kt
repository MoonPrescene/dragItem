package com.example.dragitem.fragment


import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dragitem.CheckCallButtonState

class CheckCallButtonStateRepository(private val checkCallButtonStateDao: CheckCallButtonStateDao) {

    //val TAG = "_BUTTON_SIZE"
    val getStateCheckCallButton: LiveData<CheckCallButtonState> = checkCallButtonStateDao
        .getStateCheckCallButton()

    val buttonStates: LiveData<List<CheckCallButtonState>> = checkCallButtonStateDao.getStateCheckCallButtons()

    fun insertCheckCallButtonState(checkCallButtonState: CheckCallButtonState){
        checkCallButtonStateDao.insertCheckCallButtonState(checkCallButtonState)
    }

    fun setPosition(x: Float, y: Float){
        checkCallButtonStateDao.setPosition(x, y)
    }

    fun setState(isVisible: Boolean){
        checkCallButtonStateDao.setState(isVisible)
    }

    fun setSize(sizeOfButton: Int){
        //Log.d(TAG, "set new size button: $sizeOfButton")
        checkCallButtonStateDao.setSize(sizeOfButton)
    }
}