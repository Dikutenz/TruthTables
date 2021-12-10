package com.dikutenz.truthtables.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.enums.InputType.*
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.calculators.CalculatorBinaryFragment
import com.dikutenz.truthtables.views.calculators.CalculatorReducedAlphabetFragment
import com.dikutenz.truthtables.views.calculators.CalculatorWholeAlphabetFragment
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class SettingCreateTruthTableFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var wholeAlphabetRadioButton: RadioButton
    private lateinit var reduceAlphabetRadioButton: RadioButton
    private lateinit var binaryRadioButton: RadioButton
    private lateinit var wholeTableCheckBox: CheckBox
    private lateinit var sdnfCheckBox: CheckBox
    private lateinit var sknfCheckBox: CheckBox
    private lateinit var okButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting_create_truth_table, container, false)
        initUI(view)
        return view
    }

    private fun openCalculator() {
        mainViewModel.isWholeTable = wholeTableCheckBox.isChecked
        mainViewModel.isSDNF = sdnfCheckBox.isChecked
        mainViewModel.isSKNF = sknfCheckBox.isChecked
        mainViewModel.clearFunction()

        val fragment: Fragment = when {
            reduceAlphabetRadioButton.isChecked -> CalculatorReducedAlphabetFragment()
            wholeAlphabetRadioButton.isChecked -> CalculatorWholeAlphabetFragment()
            binaryRadioButton.isChecked -> CalculatorBinaryFragment()
            else -> CalculatorReducedAlphabetFragment()
        }
        mainViewModel.inputType = when {
            reduceAlphabetRadioButton.isChecked -> REDUCED_ALPHABET
            wholeAlphabetRadioButton.isChecked -> WHOLE_ALPHABET
            binaryRadioButton.isChecked -> BINARY
            else -> REDUCED_ALPHABET
        }
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    private fun initUI(view: View) {
        wholeAlphabetRadioButton = view.findViewById(R.id.whole_alphabet_radio_button)
        reduceAlphabetRadioButton = view.findViewById(R.id.reduced_alphabet_radio_button)
        binaryRadioButton = view.findViewById(R.id.binary_radio_button)
        wholeTableCheckBox = view.findViewById(R.id.whole_table_check_box)
        sdnfCheckBox = view.findViewById(R.id.sdnf_check_box)
        sknfCheckBox = view.findViewById(R.id.sknf_check_box)
        okButton = view.findViewById(R.id.ok_button)
        okButton.setOnClickListener { openCalculator() }

        wholeTableCheckBox.isChecked = mainViewModel.isWholeTable
        sdnfCheckBox.isChecked = mainViewModel.isSDNF
        sknfCheckBox.isChecked = mainViewModel.isSKNF
    }


}
