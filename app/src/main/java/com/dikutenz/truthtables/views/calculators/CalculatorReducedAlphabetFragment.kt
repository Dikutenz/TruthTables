package com.dikutenz.truthtables.views.calculators

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.CorrectError
import com.dikutenz.truthtables.model.FragmentChangeListener
import com.dikutenz.truthtables.model.LogicOperations
import com.dikutenz.truthtables.model.Solve
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.enums.CorrectInput
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.viewModel.HistoryViewModel
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.MainActivity
import com.dikutenz.truthtables.views.results.ResultFragment
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class CalculatorReducedAlphabetFragment : Fragment(), CalculatorSettingFragment.OnDismissListener {

    private val mainViewModel: MainViewModel by viewModel()
    private val historyViewModel: HistoryViewModel by viewModel()

    private lateinit var toolbar: Toolbar
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
        savedInstanceState: Bundle?,
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
        val correctInput = CorrectError.checkCorrect(booleanFunction)
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
            (requireActivity() as MainActivity).replaceFragment(ResultFragment())
        }
    }

    private fun backspaceChar() {
        if (mainViewModel.booleanFunction.value!!.isNotEmpty()) mainViewModel.backspaceChar()
    }

    private fun clearFunction() {
        mainViewModel.clearFunction()
    }

    override fun onDismiss() {
        (requireActivity() as MainActivity).replaceFragment(
            when (mainViewModel.inputType) {
                InputType.REDUCED_ALPHABET -> CalculatorReducedAlphabetFragment()
                InputType.WHOLE_ALPHABET -> CalculatorWholeAlphabetFragment()
                InputType.BINARY -> CalculatorBinaryFragment()
                InputType.EQUIVALENCE_FUNCTION -> CalculatorEqTwoFunctionsFragment()
            })
    }

    private fun initUI(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Калькулятор"
        toolbar.inflateMenu(R.menu.calculator_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_setting -> {
                    val dialogFragment = CalculatorSettingFragment(this)
                    dialogFragment.show(requireActivity().supportFragmentManager, "SettingFragment")
                }
            }
            true
        }
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
