package com.example.dragitem.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dragitem.CheckCallButtonState
import com.example.dragitem.R
import com.example.dragitem.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private val viewModel: SettingFragmentViewModel by activityViewModels()
    private lateinit var checkCallButtonState: CheckCallButtonState
    private var sizeButton = 0
    private lateinit var binding: FragmentSettingBinding
    private var buttonSizes = ButtonSize.values()
    private lateinit var listPopUpWindow: ListPopupWindow
    private val TAG = "_BUTTON_SIZE_STATE"


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

        listPopUpWindow = ListPopupWindow(requireContext(),
            null,
            androidx.appcompat.R.attr.listPopupWindowStyle)



        //Get state of check call button state
        subscribeUi()

        activity?.actionBar?.hide()

        /*binding.apply {
            buttonPopUp.setOnClickListener {
                val popUp = PopupMenu(requireContext(), buttonPopUp)
                popUp.inflate(R.menu.item_list)
                popUp.setOnMenuItemClickListener {
                    buttonPopUp.text = it.title
                    getSizeOfButton()
                    viewModel.setSize(sizeButton)
                    true
                }
                popUp.show()
            }
        }*/

        listPopUpWindow.anchorView = binding.buttonPopUp

        val arrayAdapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.item_list_button_size,
                buttonSizes.map {
                    it.sizeName
                }
            )
        }

        listPopUpWindow.setAdapter(arrayAdapter)

        listPopUpWindow.setOnItemClickListener { parent, view, position, id ->
            sizeButton = buttonSizes[position].size
            viewModel.setSize(sizeButton)
            listPopUpWindow.dismiss()
        }

        binding.buttonPopUp.setOnClickListener {
            v: View? -> listPopUpWindow.show()
        }
        //binding.spinner.adapter = arrayAdapter

       /* binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "index of spinner: $position")
                sizeButton = buttonSizes[position].size
                Log.d(TAG, "update new size button: $sizeButton")
                viewModel.setSize(sizeButton)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }*/

        binding.checkButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            Log.d(TAG, "change button state: $isChecked")
            viewModel.setState(isChecked)
        }

        binding.backButton.setOnClickListener {
            popToHomeFragment()
        }

        return binding.root
    }

    /*private fun getSizeOfButton() {
        when(binding.buttonPopUp.text){
            "Lớn" ->{
                sizeButton = 0
            }
            "Trung bình" ->{
                sizeButton = 1
            }
            "Nhỏ" ->{
                sizeButton = 2
            }
        }
    }*/

    private fun popToHomeFragment() {
        val action = SettingFragmentDirections.actionSettingFragmentToHomeFragment()
        findNavController().navigate(action)
    }

   /* private fun showSizeOfButton(index: Int){
        binding.apply {
            spinner.post { spinner.setSelection(index)}
        }
    }*/

    @SuppressLint("SetTextI18n")
    private fun subscribeUi() {
        viewModel.apply {
            getCheckCallButtonState().observe(viewLifecycleOwner){
                if (it != null){
                    Log.d(TAG, "lmao " + it.toMap())
                    checkCallButtonState = it
//                    showSizeOfButton(checkCallButtonState.size)
                    when(checkCallButtonState.size){
                        0 -> {
                            binding.buttonPopUp.text = "Lớn"
                        }
                        1 ->{
                            binding.buttonPopUp.text = "Trung bình"
                        }
                        2 ->{
                            binding.buttonPopUp.text = "Nhỏ"
                        }
                    }
                }
            }
        }
    }

}

enum class ButtonSize(val sizeName: String = "", val size: Int ){
    LARGE("Lớn", 0),
    MEDIUM("Trung bình", 1),
    SMALL("Nhỏ", 2)
}