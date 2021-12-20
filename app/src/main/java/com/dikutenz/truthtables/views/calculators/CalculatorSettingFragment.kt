package com.dikutenz.truthtables.views.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CalculatorSettingFragment(private val onDismissListener: OnDismissListener) :
    DialogFragment() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculatro_setting, container, false)
        val reduceRadioButton: RadioButton = view.findViewById(R.id.reduce_radio_button)
        val wholeRadioButton: RadioButton = view.findViewById(R.id.whole_radio_button)
        val binaryRadioButton: RadioButton = view.findViewById(R.id.binary_radio_button)
        val eqRadioButton: RadioButton = view.findViewById(R.id.eq_radio_button)
        when (mainViewModel.inputType) {
            InputType.REDUCED_ALPHABET -> reduceRadioButton.isChecked = true
            InputType.WHOLE_ALPHABET -> wholeRadioButton.isChecked = true
            InputType.BINARY -> binaryRadioButton.isChecked = true
            InputType.EQUIVALENCE_FUNCTION -> eqRadioButton.isChecked = true
        }
        val saveButton: Button = view.findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            if (reduceRadioButton.isChecked) mainViewModel.inputType = InputType.REDUCED_ALPHABET
            else if (wholeRadioButton.isChecked) mainViewModel.inputType = InputType.WHOLE_ALPHABET
            else if (binaryRadioButton.isChecked) mainViewModel.inputType = InputType.BINARY
            else if (eqRadioButton.isChecked) mainViewModel.inputType = InputType.EQUIVALENCE_FUNCTION
            onDismissListener.onDismiss()
            dismiss()
        }
        return view
    }

    interface OnDismissListener {
        fun onDismiss()
    }

}