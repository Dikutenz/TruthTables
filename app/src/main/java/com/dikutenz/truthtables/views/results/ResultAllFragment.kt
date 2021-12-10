package com.dikutenz.truthtables.views.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.SNF
import com.dikutenz.truthtables.model.SNF.getSDNF
import com.dikutenz.truthtables.model.SNF.getSKNF
import com.dikutenz.truthtables.model.Solve.getBinaryTruthTable
import com.dikutenz.truthtables.model.Solve.getShortTruthTable
import com.dikutenz.truthtables.model.Solve.getTruthTable
import com.dikutenz.truthtables.model.enums.InputType.BINARY
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.adapters.TableAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class ResultAllFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var resultLayout: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var sdnfCard: CardView
    private lateinit var sdnfTextView: TextView
    private lateinit var sknfCard: CardView
    private lateinit var sknfTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result_all, container, false)
        initUI(view)
        loadData()
        return view
    }

    private fun loadData() {
        mainViewModel.booleanFunction.observe(viewLifecycleOwner) { booleanFunction ->
            if (booleanFunction.isNotEmpty() && mainViewModel.enterFinished) {
                resultLayout.visibility = View.VISIBLE
                createTable()
                if (mainViewModel.isSDNF) sdnfTextView.text =
                    getSDNF(
                        if (mainViewModel.inputType == BINARY) getBinaryTruthTable(booleanFunction)
                        else getTruthTable(booleanFunction)
                    )
                else sdnfCard.visibility = View.GONE
                if (mainViewModel.isSKNF) sknfTextView.text =
                    getSKNF(
                        if (mainViewModel.inputType == BINARY) getBinaryTruthTable(booleanFunction)
                        else getTruthTable(booleanFunction)
                    )
                else sknfCard.visibility = View.GONE
            } else {
                resultLayout.visibility = View.GONE
            }
        }

    }

    private fun createTable() {
        val booleanFunction = mainViewModel.booleanFunction.value!!
        val values = if (mainViewModel.inputType == BINARY) getBinaryTruthTable(booleanFunction)
        else getShortTruthTable(booleanFunction)

        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = activity?.let { TableAdapter(values, it) }
    }


    private fun initUI(view: View) {
        resultLayout = view.findViewById(R.id.result_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
        sdnfCard = view.findViewById(R.id.sdnf_card)
        sdnfTextView = view.findViewById(R.id.sdnf_text_view)
        sknfCard = view.findViewById(R.id.sknf_card)
        sknfTextView = view.findViewById(R.id.sknf_text_view)
    }

}
