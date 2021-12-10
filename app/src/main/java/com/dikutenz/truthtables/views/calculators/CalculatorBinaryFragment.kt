package com.dikutenz.truthtables.views.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.viewModel.HistoryViewModel
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.MainActivity
import com.dikutenz.truthtables.views.results.ResultFragment
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class CalculatorBinaryFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()
    private val historyViewModel: HistoryViewModel by viewModel()

    private lateinit var sTextView: TextView
    private lateinit var solveButton: MaterialButton
    private lateinit var clearButton: MaterialButton
    private lateinit var backspaceButton: MaterialButton
    private lateinit var oneButton: MaterialButton
    private lateinit var zeroButton: MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator_binary, container, false)
        initUI(view)
        mainViewModel.booleanFunction.observe(viewLifecycleOwner) { sTextView.text = it }
        return view
    }

    private fun addChar(char: Char) {
        mainViewModel.addChar(char)
    }

    private fun solve() {
        val booleanFunction = mainViewModel.booleanFunction.value
        var correctInput = true
        var x = 0
        var count = 0
        booleanFunction?.let {
            x = booleanFunction.length
            while (x > 0 && x % 2 == 0) {
                count++
                x /= 2
            }
        }
        if (x > 1 || count == 0) correctInput = false

        if (correctInput) {
            mainViewModel.enterFinished = true
            historyViewModel.insert(
                BooleanFunction(
                    mainViewModel.booleanFunction.value!!,
                    mainViewModel.getInputTypeToString()
                )
            )
            showResultFragment()
        } else Toast.makeText(activity, "Неверный ввод!", Toast.LENGTH_LONG).show()
    }

    private fun showResultFragment() {
        val fragment: Fragment = ResultFragment()
        if (activity is MainActivity) (activity as MainActivity).replaceFragment(fragment)
    }

    private fun backspaceChar() {
        if (mainViewModel.booleanFunction.value!!.isNotEmpty())
            mainViewModel.backspaceChar()
    }

    private fun clearFunction() {
        mainViewModel.clearFunction()
    }


    private fun initUI(view: View) {
        sTextView = view.findViewById(R.id.s_text_view)
        solveButton = view.findViewById(R.id.solve_button)

        clearButton = view.findViewById(R.id.clear_button)
        backspaceButton = view.findViewById(R.id.backspace_button)
        oneButton = view.findViewById(R.id.one_button)
        zeroButton = view.findViewById(R.id.zero_button)

        solveButton.setOnClickListener { solve() }

        clearButton.setOnClickListener { clearFunction() }
        backspaceButton.setOnClickListener { backspaceChar() }
        oneButton.setOnClickListener { addChar('1') }
        zeroButton.setOnClickListener { addChar('0') }

    }
}