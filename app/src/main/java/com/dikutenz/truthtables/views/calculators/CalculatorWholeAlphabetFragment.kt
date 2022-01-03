package com.dikutenz.truthtables.views.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.CorrectError
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.LogicOperations
import com.dikutenz.truthtables.model.Solve
import com.dikutenz.truthtables.model.enums.CorrectInput
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.viewModel.HistoryViewModel
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.MainActivity
import com.dikutenz.truthtables.views.results.ResultFragment
import com.google.android.material.button.MaterialButton
import org.koin.android.viewmodel.ext.android.viewModel

class CalculatorWholeAlphabetFragment : Fragment(), CalculatorSettingFragment.OnDismissListener {

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
    private lateinit var dButton: MaterialButton

    private lateinit var eButton: MaterialButton
    private lateinit var fButton: MaterialButton
    private lateinit var hButton: MaterialButton
    private lateinit var gButton: MaterialButton

    private lateinit var iButton: MaterialButton
    private lateinit var jButton: MaterialButton
    private lateinit var kButton: MaterialButton
    private lateinit var lButton: MaterialButton

    private lateinit var mButton: MaterialButton
    private lateinit var nButton: MaterialButton
    private lateinit var oButton: MaterialButton
    private lateinit var pButton: MaterialButton

    private lateinit var qButton: MaterialButton
    private lateinit var rButton: MaterialButton
    private lateinit var sButton: MaterialButton
    private lateinit var tButton: MaterialButton

    private lateinit var uButton: MaterialButton
    private lateinit var vButton: MaterialButton
    private lateinit var wButton: MaterialButton

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
        val view = inflater.inflate(R.layout.fragment_calculator_whole_alphabet, container, false)
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
        if (mainViewModel.booleanFunction.value!!.isNotEmpty())
            mainViewModel.backspaceChar()
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

        aButton = view.findViewById(R.id.a_button)
        bButton = view.findViewById(R.id.b_button)
        cButton = view.findViewById(R.id.c_button)
        dButton = view.findViewById(R.id.d_button)

        eButton = view.findViewById(R.id.e_button)
        fButton = view.findViewById(R.id.f_button)
        gButton = view.findViewById(R.id.g_button)
        hButton = view.findViewById(R.id.h_button)

        iButton = view.findViewById(R.id.i_button)
        jButton = view.findViewById(R.id.j_button)
        kButton = view.findViewById(R.id.k_button)
        lButton = view.findViewById(R.id.l_button)

        mButton = view.findViewById(R.id.m_button)
        nButton = view.findViewById(R.id.n_button)
        oButton = view.findViewById(R.id.o_button)
        pButton = view.findViewById(R.id.p_button)

        qButton = view.findViewById(R.id.q_button)
        rButton = view.findViewById(R.id.r_button)
        sButton = view.findViewById(R.id.s_button)
        tButton = view.findViewById(R.id.t_button)

        uButton = view.findViewById(R.id.u_button)
        vButton = view.findViewById(R.id.v_button)
        wButton = view.findViewById(R.id.w_button)

        xButton = view.findViewById(R.id.x_button)
        yButton = view.findViewById(R.id.y_button)
        zButton = view.findViewById(R.id.z_button)

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
        dButton.setOnClickListener { addChar('D') }

        eButton.setOnClickListener { addChar('E') }
        fButton.setOnClickListener { addChar('F') }
        gButton.setOnClickListener { addChar('G') }
        hButton.setOnClickListener { addChar('H') }

        iButton.setOnClickListener { addChar('I') }
        jButton.setOnClickListener { addChar('J') }
        kButton.setOnClickListener { addChar('K') }
        lButton.setOnClickListener { addChar('L') }

        mButton.setOnClickListener { addChar('M') }
        nButton.setOnClickListener { addChar('N') }
        oButton.setOnClickListener { addChar('O') }
        pButton.setOnClickListener { addChar('P') }

        qButton.setOnClickListener { addChar('Q') }
        rButton.setOnClickListener { addChar('R') }
        sButton.setOnClickListener { addChar('S') }
        tButton.setOnClickListener { addChar('T') }

        uButton.setOnClickListener { addChar('U') }
        vButton.setOnClickListener { addChar('V') }
        wButton.setOnClickListener { addChar('W') }

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
