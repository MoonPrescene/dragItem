package com.example.dragitem.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.dragitem.R
import com.example.dragitem.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    var buttonState = true
    var sizeButton = 0

    companion object{
        const val buttonStateKey = "button_state"
        const val buttonSizeKey = "button_size"
    }

    private lateinit var binding: FragmentSettingBinding

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

        activity?.actionBar?.hide()
        binding.backButton.setOnClickListener {
            popToHomeFragment()
        }
        val buttonSize = resources.getStringArray(R.array.buttonSize)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_list_button_size, buttonSize)
        binding.autoCompleteTextView.adapter = arrayAdapter

        binding.checkButton.setOnClickListener {
            if (binding.checkButton.isChecked){
                buttonState
            }else{
                buttonState = false
            }
        }

        val size = binding.autoCompleteTextView.selectedItem.toString()
        if (size == "Lớn"){
            sizeButton = 0
        }
        if (size == "Trung bình"){
            sizeButton = 1
        }
        if (size == "Lớn"){
            sizeButton = 2
        }

        val bundle = Bundle()
        bundle.putSerializable(buttonStateKey, buttonState)
        bundle.putInt(buttonSizeKey, sizeButton)
        return binding.root
    }

    private fun popToHomeFragment() {
        val action = SettingFragmentDirections.actionSettingFragmentToHomeFragment()
        findNavController().navigate(action)
    }

}