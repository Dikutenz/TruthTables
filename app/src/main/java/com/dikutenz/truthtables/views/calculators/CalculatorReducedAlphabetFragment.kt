package com.dikutenz.truthtables.views.calculators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.*
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.enums.CorrectInput
import com.dikutenz.truthtables.viewModel.HistoryViewModel
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.results.ResultFragment
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class CalculatorReducedAlphabetFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()
    private val historyViewModel: HistoryViewModel by viewModel()

    private lateinit var sTextView: TextView
    private lateinit var solveButton: MaterialButton
    private lateinit var clearButton: MaterialButton
    private lateinit var backspaceButton: MaterialButton

    private lateinit var aButton: MaterialButton
    private lateinit var bButton: MaterialButton
    private lateinit var cButton: MaterialButton

    private lateinit var xButton: MaterialButton
    private lateinit var yButton: MaterialButton
    private lateinit var zButton: MaterialButton

    private lateinit var notButton: MaterialButton
    private lateinit var andButton: MaterialButton
    private lateinit var orButton: MaterialButton
    private lateinit var implyButton: MaterialButton
    private lateinit var eqButton: MaterialButton
    private lateinit var xorButton: MaterialButton
    private lateinit var norButton: MaterialButton
    private lateinit var nandButton: MaterialButton

    private lateinit var beginButton: MaterialButton
    private lateinit var endButton: MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator_reduced_alphabet, container, false)
        initUI(view)
        mainViewModel.booleanFunction.observe(viewLifecycleOwner) { sTextView.text = it }
        return view
    }

    private fun addChar(char: Char) {
        mainViewModel.addChar(char)
    }

    private fun solve() {
        val booleanFunction = mainViewModel.booleanFunction.value!!
        val correctInput = Solve.checkCorrect(booleanFunction)
        val message = when (correctInput) {
            CorrectInput.OK -> "Вычисление!"
            CorrectInput.LACK_OPEN_PARENTHESIS -> "Недостаточно открывающих скобок!"
            CorrectInput.LACK_CLOSE_PARENTHESIS -> "Недостаточно закрывающих скобок!"
            CorrectInput.ZERO_VARIABLES -> "Ноль переменных!"
            CorrectInput.ZERO_OPERATORS -> "Ноль операторов!"
            CorrectInput.FIRST_ERROR -> "Первый символ ошибочный!"
            CorrectInput.LAST_ERROR -> "Последний символ ошибочный!"
            CorrectInput.OTHER_ERROR -> "Ошибка ввода!"
        }
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        if (correctInput == CorrectInput.OK) {
            mainViewModel.enterFinished = true
            historyViewModel.insert(
                BooleanFunction(
                    mainViewModel.booleanFunction.value!!,
                    mainViewModel.getInputTypeToString()
                )
            )
            showResultFragment()
        }
    }

    private fun showResultFragment() {
        val fr: Fragment = ResultFragment()
        val fc = activity as FragmentChangeListener?
        fc!!.replaceFragment(fr)
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

        aButton = view.findViewById(R.id.ar_button)
        bButton = view.findViewById(R.id.br_button)
        cButton = view.findViewById(R.id.cr_button)

        xButton = view.findViewById(R.id.xr_button)
        yButton = view.findViewById(R.id.yr_button)
        zButton = view.findViewById(R.id.zr_button)

        notButton = view.findViewById(R.id.not_button)
        andButton = view.findViewById(R.id.and_button)
        orButton = view.findViewById(R.id.or_button)
        implyButton = view.findViewById(R.id.imply_button)
        eqButton = view.findViewById(R.id.eq_button)
        xorButton = view.findViewById(R.id.xor_button)
        norButton = view.findViewById(R.id.nor_button)
        nandButton = view.findViewById(R.id.nand_button)
        beginButton = view.findViewById(R.id.begin_button)
        endButton = view.findViewById(R.id.end_button)

        aButton.setOnClickListener { addChar('A') }
        bButton.setOnClickListener { addChar('B') }
        cButton.setOnClickListener { addChar('C') }

        xButton.setOnClickListener { addChar('X') }
        yButton.setOnClickListener { addChar('Y') }
        zButton.setOnClickListener { addChar('Z') }

        notButton.setOnClickListener { addChar(LogicOperations.notChar) }
        andButton.setOnClickListener { addChar(LogicOperations.andChar) }
        orButton.setOnClickListener { addChar(LogicOperations.orChar) }
        implyButton.setOnClickListener { addChar(LogicOperations.implyChar) }
        eqButton.setOnClickListener { addChar(LogicOperations.eqChar) }
        xorButton.setOnClickListener { addChar(LogicOperations.xorChar) }
        norButton.setOnClickListener { addChar(LogicOperations.norChar) }
        nandButton.setOnClickListener { addChar(LogicOperations.nandChar) }


        beginButton.setOnClickListener { addChar(LogicOperations.openingParenthesis) }
        endButton.setOnClickListener { addChar(LogicOperations.closingParenthesis) }

        solveButton.setOnClickListener { solve() }

        clearButton.setOnClickListener { clearFunction() }
        backspaceButton.setOnClickListener { backspaceChar() }

    }
}
