package com.dikutenz.truthtables.views.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dikutenz.truthtables.viewModel.MainViewModel

import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.SNF
import com.dikutenz.truthtables.model.enums.InputType.BINARY
import com.dikutenz.truthtables.model.SNF.getStep1SDNF
import com.dikutenz.truthtables.model.SNF.getStep2SDNF
import com.dikutenz.truthtables.model.Solve
import com.dikutenz.truthtables.model.Solve.getBinaryTruthTable
import com.dikutenz.truthtables.model.Solve.getTruthTable
import org.koin.android.viewmodel.ext.android.viewModel

class ResultSDNFFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var resultLayout: LinearLayout
    private lateinit var step1TextView:TextView
    private lateinit var step2TextView:TextView
    private lateinit var step3TextView:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result_s_d_n_f, container, false)
        initUI(view)
        loadData()
        return view
    }

    private fun loadData() {
        mainViewModel.booleanFunction.observe(viewLifecycleOwner) {
            if (it.isNotEmpty() && mainViewModel.enterFinished) {
                resultLayout.visibility = View.VISIBLE
                step1TextView.text = getStep1SDNF(it, mainViewModel.inputType)
                step2TextView.text = getStep2SDNF(it, mainViewModel.inputType)
                step3TextView.text = SNF.getSDNF(
                    if (mainViewModel.inputType == BINARY) getBinaryTruthTable(it)
                    else getTruthTable(it))
            } else {
                resultLayout.visibility = View.GONE
            }
        }
    }

    private fun initUI(view: View) {
        resultLayout = view.findViewById(R.id.result_layout)
        step1TextView = view.findViewById(R.id.step1_text_view)
        step2TextView = view.findViewById(R.id.step2_text_view)
        step3TextView = view.findViewById(R.id.step3_text_view)
    }

}
