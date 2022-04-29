package com.example.dragitem.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.dragitem.CheckCallButtonState
import com.example.dragitem.CheckCallButtonStateDataBase
import java.nio.channels.FileLock

class HomeFragmentViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var getStateCheckCallButton: LiveData<CheckCallButtonState>
    private val repository: CheckCallButtonStateRepository

    init {
        val checkCallButtonStateDao = CheckCallButtonStateDataBase.getCheckCallButtonStateDataBase(application)!!
            .checkCallButtonStateDao()
        repository = CheckCallButtonStateRepository(checkCallButtonStateDao)
    }

    fun setCheckCallCButtonPosition(x: Float, y: Float){
        Log.d("_SAVE_POSITION", "x: $x || y: $y")
        repository.setPosition(x, y)
    }

    fun insertCheckCallButtonState(checkCallButtonState: CheckCallButtonState){
        repository.insertCheckCallButtonState(checkCallButtonState)
    }

    fun getCheckCallButtonState(): LiveData<CheckCallButtonState> {
        getStateCheckCallButton = repository.getStateCheckCallButton
        return getStateCheckCallButton
    }

    fun getCheckCallButtonStates(): LiveData<List<CheckCallButtonState>> {
        return repository.buttonStates
    }
}