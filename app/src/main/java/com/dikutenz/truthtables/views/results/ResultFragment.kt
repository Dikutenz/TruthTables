package com.dikutenz.truthtables.views.results

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.enums.Topic
import com.dikutenz.truthtables.viewModel.HistoryViewModel
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.MainActivity
import com.dikutenz.truthtables.views.adapters.HistoryAdapter
import com.dikutenz.truthtables.views.adapters.ResultPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.viewModel

class ResultFragment : Fragment(), HistoryAdapter.OnItemClickListener, SettingFragment.OnDismissListener {

    private val mainViewModel: MainViewModel by viewModel()
    private val historyViewModel: HistoryViewModel by viewModel()

    private lateinit var historyButton: ImageButton
    private lateinit var settingButton: ImageButton
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var sTextView: TextView
    private lateinit var s2TextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        initUI(view)
        mainViewModel.booleanFunction.observe(viewLifecycleOwner) {
            if (it.isNotEmpty() && mainViewModel.enterFinished) sTextView.text = it
            else sTextView.text = "Нет данных"
            if (mainViewModel.topic == Topic.EQUIVALENCE_FUNCTION) {
                mainViewModel.secondBooleanFunction.observe(viewLifecycleOwner) { secondBooleanFunction ->
                    if (secondBooleanFunction.isNotEmpty() && mainViewModel.enterFinished)
                        s2TextView.text = secondBooleanFunction
                    else s2TextView.text = "Нет данных"
                }
            }
        }
        return view
    }

    private fun initUI(view: View) {
        historyButton = view.findViewById(R.id.history_button)
        settingButton = view.findViewById(R.id.settings_button)
        settingButton.setOnClickListener {
            val dialogFragment = SettingFragment(this)
            dialogFragment.show(requireActivity().supportFragmentManager, "SettingFragment")
        }
        viewPager = view.findViewById(R.id.view_pager)
        setupViewPager(viewPager)
        tabLayout = view.findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
        sTextView = view.findViewById(R.id.s_text_view)
        s2TextView = view.findViewById(R.id.s2_text_view)

        if (mainViewModel.topic == Topic.EQUIVALENCE_FUNCTION) {
            historyButton.visibility = View.GONE
            s2TextView.visibility = View.VISIBLE
        } else {
            historyButton.visibility = View.VISIBLE
            s2TextView.visibility = View.GONE
            historyButton.setOnClickListener {
                historyViewModel.getAll().observe(viewLifecycleOwner) {
                    showHistory(it)
                }
            }
        }
    }

    private fun showHistory(list: List<BooleanFunction>) {
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_history, null)
        val noFuncTextView: TextView = view.findViewById(R.id.no_func_text_view)
        val clearButton: Button = view.findViewById(R.id.clear_button)
        if (list.isNotEmpty()) {
            noFuncTextView.visibility = View.GONE
            clearButton.visibility = View.VISIBLE
            clearButton.setOnClickListener {
                historyViewModel.clear()
                bottomSheetDialog.dismiss()
            }
            val recyclerView: RecyclerView = view.findViewById(R.id.history_recycler)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = HistoryAdapter(this)
            adapter.items = list
            recyclerView.adapter = adapter
        } else {
            noFuncTextView.visibility = View.VISIBLE
            clearButton.visibility = View.GONE
        }
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter =
            ResultPagerAdapter((context as MainActivity).supportFragmentManager)
        if (mainViewModel.topic == Topic.EQUIVALENCE_FUNCTION) {
            adapter.addFragment(ResultEqFragment(), "Сравнение")
        } else {
            adapter.addFragment(ResultAllFragment(), "Общее")
            if (mainViewModel.isWholeTable)
                adapter.addFragment(ResultTruthTableFragment(), "Таблица истинности")
            if (mainViewModel.isSDNF) adapter.addFragment(ResultSDNFFragment(), "СДНФ")
            if (mainViewModel.isSKNF) adapter.addFragment(ResultSKNFFragment(), "СКНФ")
        }
        viewPager.adapter = adapter
    }

    override fun onItemClicked(booleanFunction: BooleanFunction) {
        mainViewModel.setHistory(booleanFunction)
    }

    override fun onDismiss() {
        setupViewPager(viewPager)
    }


}
