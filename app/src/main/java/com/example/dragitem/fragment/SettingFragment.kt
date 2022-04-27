package com.example.dragitem.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dragitem.CheckCallButtonState
import com.example.dragitem.R
import com.example.dragitem.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private val viewModel: SettingFragmentViewModel by activityViewModels()
    private lateinit var checkCallButtonState: CheckCallButtonState

    var buttonState = true
    var sizeButton = 0

    private lateinit var binding: FragmentSettingBinding
    private var buttonSizes = ButtonSize.values()
    val TAG = "_BUTTON_STATE"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        )



        //Get state of check call button state
        subscribeUi()
//
//        if (checkCallButtonState.state){
//            binding.checkButton.isChecked
//        }

        activity?.actionBar?.hide()

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_list_button_size,
            buttonSizes.map {
                it.sizeName
            }
        )
        binding.spinner.adapter = arrayAdapter


        //Check change state and size of button
        /*binding.spinner.setOnItemClickListener { _, _, position, _ ->
            sizeButton = buttonSizes[position].size
        }*/

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sizeButton = buttonSizes[position].size
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.checkButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            Log.d(TAG, "change button state: $isChecked")
            viewModel.setState(isChecked)
        }

        //save new state of button
//        saveCheckCallButtonState(checkCallButtonState.id, checkCallButtonState.x, checkCallButtonState.y, sizeButton, buttonState)

//        val sizeName = binding.autoCompleteTextView.selectedItem.toString()
//        when(sizeName){
//            ButtonSize.SMALL.sizeName -> sizeButton = ButtonSize.SMALL.size
//            ButtonSize.MEDIUM.sizeName -> sizeButton = ButtonSize.MEDIUM.size
//            ButtonSize.LARGE.sizeName -> sizeButton = ButtonSize.LARGE.size
//        }
//
//        binding.backButton.setOnClickListener {
//            popToHomeFragment(buttonState, sizeButton)
//        }

//        buttonState = SettingFragmentArgs.fromBundle(requireArguments(requireArguments)).state
//        sizeButton = SettingFragmentArgs.fromBundle(requireArguments()).size


        binding.backButton.setOnClickListener {
            popToHomeFragment()
        }

        return binding.root
    }

    private fun popToHomeFragment() {
        val action = SettingFragmentDirections.actionSettingFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun saveCheckCallButtonState(id: String, x: Float, y: Float, size: Int, state: Boolean) {
        val checkCallButtonState = CheckCallButtonState(id, x, y, state, size)
        viewModel.insertCheckCallButtonState(checkCallButtonState)
    }

    private fun subscribeUi() {
        viewModel.apply {
            getCheckCallButtonState().observe(viewLifecycleOwner){
                if (it != null){
                    checkCallButtonState = it
                }
            }
        }
    }

}

enum class ButtonSize(val sizeName: String = "", val size: Int ){
    SMALL("Nho", 2),
    MEDIUM("Trung binh", 1),
    LARGE("Lon", 0)
}