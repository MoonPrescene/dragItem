package com.example.dragitem.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dragitem.CheckCallButtonState
import com.example.dragitem.R
import com.example.dragitem.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val viewModel: HomeFragmentViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding
    private  var width = 0F
    private  var height = 0F
    var distanceX = 0F
    var distanceY = 0F
    var movedX: Float = 0F
    var movedY: Float = 0F
    var a = 0F
    var b = 0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )


        val bundleReceive = arguments
        val sizeButton = bundleReceive!!.get(SettingFragment.buttonSizeKey) as Int
        val stateButton = bundleReceive.get(SettingFragment.buttonStateKey) as Boolean

        setUpDraggableButton(sizeButton, stateButton)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun setUpDraggableButton(sizeButton: Int, stateButton: Boolean) {
        val params = binding.textView.layoutParams
        val checkCallButtonState = viewModel.getCheckCallButtonState()
        if (checkCallButtonState.value!!.state){
            binding.textView.visibility = View.VISIBLE
        }else{
            binding.textView.visibility = View.GONE
        }
        binding.apply {
            textView.x = checkCallButtonState.value!!.x
            textView.y = checkCallButtonState.value!!.y
        }
        val sizeY = setSizeButtonHeight(checkCallButtonState.value!!.size)
        val sizeX = sizeY*(3/4)

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        width = displayMetrics.widthPixels.toFloat() - 100
        height = displayMetrics.heightPixels.toFloat() - 100
        binding.textView.setOnTouchListener { view, event ->
            val action = event.action
            var xDown = 0F
            var yDown = 0F
            when(action){

                MotionEvent.ACTION_DOWN ->{
                    xDown = event.x
                    yDown = event.y
                }

                MotionEvent.ACTION_MOVE ->{
                    movedX = event.x
                    movedY = event.y

                    distanceX = movedX - xDown
                    distanceY = movedY - yDown
                    binding.apply {
                        textView.x = textView.x + distanceX
                        textView.y = textView.y + distanceY
                    }

                }

                MotionEvent.ACTION_UP ->{
                    if (    a==movedX && b == movedY    ){
                        //Toast.makeText(this, "SHowToast", Toast.LENGTH_SHORT).show()
                        navigationToSettingFragment()
                    }

                    a = movedX
                    b = movedY


                    binding.apply {
                        if (textView.x >= width || textView.y >= height || textView.y <= 2*sizeY.toFloat()) {
                            if (textView.x >= width - sizeX/2) {
                                textView.x = width - 6*sizeX
                            }
                            if (textView.y >= height - sizeY/2) {
                                textView.y = height - 6*sizeY
                            }
                            if (textView.y <= 2*sizeY.toFloat()) {
                                textView.y = 2*sizeY.toFloat()
                            }
                        }

                        saveCheckCallButtonState(1, movedX, movedY, sizeButton, stateButton)

                    }
                }

                MotionEvent.ACTION_CANCEL ->{

                }

                else->{

                }
            }

            true
        }

    }

    private fun navigationToSettingFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
        findNavController().navigate(action)
    }

    private fun saveCheckCallButtonState(id: Int, x: Float, y: Float, size: Int, state: Boolean) {
        val checkCallButtonState = CheckCallButtonState(id, x, y, state, size)
        viewModel.insertCheckCallButtonState(checkCallButtonState)
    }

    private fun setSizeButtonHeight(size: Int): Float{
        var heightOfButton: Float = 0f
        if (size == 0){
            heightOfButton = 120f
        }
        if (size == 1){
            heightOfButton = 80f
        }
        if (size == 2){
            heightOfButton = 60f
        }
        return heightOfButton
    }

}