package com.dikutenz.truthtables.views.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ResultSettingFragment(private val onDismissListener: OnDismissListener) : DialogFragment() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result_setting, container, false)
        val truthTableCheckBox: CheckBox = view.findViewById(R.id.truth_table_check_box)
        val sdnfCheckBox: CheckBox = view.findViewById(R.id.sdnf_check_box)
        val sknfCheckBox: CheckBox = view.findViewById(R.id.sknf_check_box)
        val saveButton: Button = view.findViewById(R.id.save_button)
        truthTableCheckBox.isChecked = mainViewModel.isWholeTable
        sdnfCheckBox.isChecked = mainViewModel.isSDNF
        sknfCheckBox.isChecked = mainViewModel.isSKNF
        saveButton.setOnClickListener {
            mainViewModel.isWholeTable = truthTableCheckBox.isChecked
            mainViewModel.isSDNF = sdnfCheckBox.isChecked
            mainViewModel.isSKNF = sknfCheckBox.isChecked
            onDismissListener.onDismiss()
            dismiss()
        }
        return view
    }

    interface OnDismissListener{
        fun onDismiss()
    }
}