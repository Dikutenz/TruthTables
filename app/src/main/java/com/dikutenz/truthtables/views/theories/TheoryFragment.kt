package com.dikutenz.truthtables.views.theories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.viewModel.TheoryViewModel
import com.dikutenz.truthtables.views.MainActivity
import com.dikutenz.truthtables.views.adapters.TheoryAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class TheoryFragment : Fragment(), TheoryAdapter.OnItemClickListener {

    private val theoryViewModel: TheoryViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theory, container, false)
        initUI(view)
        return view
    }

    override fun onItemClicked(s: String) {
        theoryViewModel.s = s
        val fragment: Fragment = LogicOperationsFragment()
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    private fun initUI(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TheoryAdapter(this)
        adapter.items = arrayListOf(
            "Конъюнкция",
            "Дизъюнкция",
            "Инверсия",
            "Импликация",
            "Эквивалентность",
            "Исключающее ИЛИ",
            "Штрих Шеффера",
            "Стрелка Пирса"
        )
        recyclerView.adapter = adapter
    }
}
