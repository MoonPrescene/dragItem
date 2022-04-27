package com.example.dragitem.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.dragitem.CheckCallButtonState
import com.example.dragitem.CheckCallButtonStateDataBase

class HomeFragmentViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var getStateCheckCallButton: LiveData<CheckCallButtonState>
    private val repository: CheckCallButtonStateRepository

    init {
        val checkCallButtonStateDao = CheckCallButtonStateDataBase.getCheckCallButtonStateDataBase(application)!!
            .checkCallButtonStateDao()
        repository = CheckCallButtonStateRepository(checkCallButtonStateDao)
    }

    fun insertCheckCallButtonState(checkCallButtonState: CheckCallButtonState){
        repository.insertCheckCallButtonState(checkCallButtonState)
    }

    fun getCheckCallButtonState(): LiveData<CheckCallButtonState> {
        getStateCheckCallButton = repository.getStateCheckCallButton
        return getStateCheckCallButton
    }
}