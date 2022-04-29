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
    val TAG = "_BUTTON_SIZE_STATE"

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

        activity?.actionBar?.hide()

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_list_button_size,
            buttonSizes.map {
                it.sizeName
            }
        )
        binding.spinner.adapter = arrayAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "index of spinner: $position")
                sizeButton = buttonSizes[position].size
                Log.d(TAG, "update new size button: $sizeButton")
                viewModel.setSize(sizeButton)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.checkButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            Log.d(TAG, "change button state: $isChecked")
            viewModel.setState(isChecked)
        }

        binding.backButton.setOnClickListener {
            popToHomeFragment()
        }

        return binding.root
    }

    private fun popToHomeFragment() {
        val action = SettingFragmentDirections.actionSettingFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun showSizeOfButton(index: Int){
        binding.apply {
            spinner.post { spinner.setSelection(index)}
        }
    }

    private fun subscribeUi() {
        viewModel.apply {
            getCheckCallButtonState().observe(viewLifecycleOwner){
                if (it != null){
                    Log.d(TAG, "lmao " + it.toMap())
                    checkCallButtonState = it
                    showSizeOfButton(checkCallButtonState.size)
                }
            }
        }
    }

}

enum class ButtonSize(val sizeName: String = "", val size: Int ){
    LARGE("Lon", 0),
    MEDIUM("Trung binh", 1),
    SMALL("Nho", 2)
}