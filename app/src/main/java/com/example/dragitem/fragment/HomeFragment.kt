package com.example.dragitem.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private var distanceX = 0F
    private var distanceY = 0F
    private var movedX: Float = 0F
    private var movedY: Float = 0F
    private var a = 0F
    private var b = 0F
    private lateinit var checkCallButtonState: CheckCallButtonState
    val TAG = "_BUTTON_SIZE_STATE"

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

        subscribeUi()

//      setUpDraggableButton(checkCallButtonState)
        return binding.root
    }

    private fun subscribeUi() {

        Log.d(TAG, "Get button state")
        viewModel.apply {
            /*getCheckCallButtonStates().observe(viewLifecycleOwner){
                Log.d(TAG, "STATES: $it")
            }*/
            getCheckCallButtonState().observe(viewLifecycleOwner){
                if (it != null){
                    checkCallButtonState = it

                    //handle show button
                    Log.d(TAG, "BS != null -> show button state, new size button ${checkCallButtonState.size}")
                    setUpDraggableButton(it)
                }else{
                    Log.d(TAG, "BS == null -> save new button state")
                    //handle save button state
                    val buttonState = CheckCallButtonState("default_id", 0f, 0f, true, 0)
                    insertCheckCallButtonState(buttonState)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun setUpDraggableButton(checkCallButtonState: CheckCallButtonState) {

        if (checkCallButtonState.state){
            binding.textView.visibility = View.VISIBLE
        }else{
            binding.textView.visibility = View.GONE
        }
        binding.apply {
            Log.d("_SAVE_POSITION", "show x: ${textView.x} || y: ${textView.y}")

            textView.post {
                textView.x = checkCallButtonState.x
                textView.y = checkCallButtonState.y
            }
        }
        val sizeY = checkCallButtonState.getSizeButton(resources)
        val sizeX = sizeY*0.75f

        binding.textView.layoutParams.height = sizeY.toInt()
        binding.textView.layoutParams.width = sizeX.toInt()

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        width = displayMetrics.widthPixels.toFloat()
        binding.constraint.post {
            height = binding.constraint.height.toFloat()
        }


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
                    if ( a==movedX && b == movedY ){
                        //Toast.makeText(this, "SHowToast", Toast.LENGTH_SHORT).show()
                        navigationToSettingFragment()
                    }else{
                        a = movedX
                        b = movedY


                        binding.apply {
                            if (textView.x >= width - sizeX) {
                                textView.x = width - sizeX
                            }
                            if (textView.x <= 0){
                                textView.x = 0f
                            }

                            if (textView.y <= 0){
                                textView.y = 0f
                            }

                            if (textView.y >= height - sizeY){
                                textView.y = height - sizeY
                            }

                            Toast.makeText(requireContext(), "${textView.y}-${height}", Toast.LENGTH_SHORT).show()

                            viewModel.setCheckCallCButtonPosition(textView.x, textView.y)
                        }
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
}