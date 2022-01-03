package com.dikutenz.truthtables.views.theories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.LogicOperations.andChar
import com.dikutenz.truthtables.model.LogicOperations.eqChar
import com.dikutenz.truthtables.model.LogicOperations.implyChar
import com.dikutenz.truthtables.model.LogicOperations.nandChar
import com.dikutenz.truthtables.model.LogicOperations.norChar
import com.dikutenz.truthtables.model.LogicOperations.notChar
import com.dikutenz.truthtables.model.LogicOperations.orChar
import com.dikutenz.truthtables.model.LogicOperations.xorChar
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.enums.InputType.*
import com.dikutenz.truthtables.viewModel.TheoryViewModel
import com.dikutenz.truthtables.views.adapters.TableAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class LogicOperationsFragment : Fragment() {

    private val theoryViewModel: TheoryViewModel by viewModel()
    private lateinit var nameTextView: TextView
    private lateinit var descTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var signTextView: TextView
    private lateinit var diagImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theory_l_o, container, false)
        initUI(view)
        loadData()
        return view
    }


    private fun loadData() {
        val s = theoryViewModel.s
        nameTextView.text = s
        descTextView.text = getDefinition(s)
        createTable(getBooleanFunction(s))
        signTextView.text = getSign(s)
        loadDiagram(s)
    }

    private fun loadDiagram(s: String) {
        diagImageView.load(
            when (s) {
                "Конъюнкция" -> R.mipmap.diag_and_foreground
                "Дизъюнкция" -> R.mipmap.diag_or_foreground
                "Инверсия" -> R.mipmap.diag_not_foreground
                "Импликация" -> R.mipmap.diag_imply_foreground
                "Эквивалентность" -> R.mipmap.diag_eq_foreground
                "Исключающее ИЛИ" -> R.mipmap.diag_xor_foreground
                "Стрелка Пирса" -> R.mipmap.diag_nor_foreground
                "Штрих Шеффера" -> R.mipmap.diag_nand_foreground
                else -> R.mipmap.diag_ab_foreground
            })
    }

    private fun getDefinition(s: String) =
        when (s) {
            "Конъюнкция" -> "Конъюнкция - это логическая операция, которая каждым двум простым (или исходным) высказываниям ставит в соответствие составное высказывание, являющееся истинным тогда и только тогда, когда оба исходных высказывания истинны."
            "Дизъюнкция" -> " Дизъюнкция - это логическая операция, которая каждым двум простым (или исходным) высказываниям ставит в соответствие составное высказывание, являющееся ложным тогда и только тогда, когда оба исходных высказывания ложны и истинным, когда хотя бы одно из двух образующих его высказываний истинно."
            "Инверсия" -> "Инверсия - логическая операция, которая каждому исходному высказыванию ставит в соответствие составное высказывание, заключающееся в том, что исходное высказывание отрицается."
            "Импликация" -> "Импликация - это логическая операция, которая каждым двум простым (или исходным) высказываниям ставит в соответствие составное высказывание, которое ложно тогда и только тогда, когда из истины следует ложь."
            "Эквивалентность" -> "Эквивалентность - это логическая операция, которая каждым двум простым (или исходным) высказываниям ставит в соответствие составное высказывание, которое является истинным тогда и только тогда, когда оба простых логических выражения имеют одинаковую истинность."
            "Исключающее ИЛИ" -> "Исключающее ИЛИ - это логическая операция, которая каждым двум простым (или исходным) высказываниям ставит в соответствие составное высказывание, являющееся ложным тогда и только тогда, когда оба простых выражения имеют одинаковую истинность."
            "Штрих Шеффера" -> "Штрих Шеффера - это логическая операция, которая каждым двум простым (или исходным) высказываниям ставит в соответствие составное высказывание, являющееся ложным тогда и только тогда, когда оба простых выражения истинны. "
            "Стрелка Пирса" -> "Стрелка Пирс - это логическая операция, которая каждым двум простым (или исходным) высказываниям ставит в соответствие составное высказывание, являющееся истинным тогда и только тогда, когда оба простых выражения ложны. "
            else -> ""
        }

    private fun getBooleanFunction(s: String) =
        when (s) {
            "Конъюнкция" -> "A${andChar}B"
            "Дизъюнкция" -> "A${orChar}B"
            "Инверсия" -> "${notChar}A"
            "Импликация" -> "A${implyChar}B"
            "Эквивалентность" -> "A${eqChar}B"
            "Исключающее ИЛИ" -> "A${xorChar}B"
            "Стрелка Пирса" -> "A${norChar}B"
            "Штрих Шеффера" -> "A${nandChar}B"
            else -> ""
        }

    private fun getSign(s: String) =
        when (s) {
            "Конъюнкция" -> "A ∧ B     A & B     A · B"
            "Дизъюнкция" -> "A ∨ B     A || B     A + B"
            "Инверсия" -> "¬A     ˜A     !A"
            "Импликация" -> "A → B     A  ⇒ B"
            "Эквивалентность" -> "A ⇔ B     A ≡ B     A ↔ B"
            "Исключающее ИЛИ" -> "A ⊕ B     A ⊻ B"
            "Стрелка Пирса" -> "A ↓ B"
            "Штрих Шеффера" -> "A | B"
            else -> ""
        }

    private fun createTable(booleanFunction: String) {
        val bf = BooleanFunction(value = booleanFunction, inputType = REDUCED_ALPHABET.toString())
        val values = bf.getTruthTable(false)
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = activity?.let { TableAdapter(values, it) }
    }

    private fun initUI(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        nameTextView = view.findViewById(R.id.name_text_view)
        descTextView = view.findViewById(R.id.desc_text_view)
        signTextView = view.findViewById(R.id.sign_text_view)
        diagImageView = view.findViewById(R.id.diag_image_view)
    }
}
