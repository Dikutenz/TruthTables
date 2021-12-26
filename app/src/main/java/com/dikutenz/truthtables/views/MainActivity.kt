package com.dikutenz.truthtables.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.FragmentChangeListener
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.viewModel.HistoryViewModel
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.views.calculators.CalculatorBinaryFragment
import com.dikutenz.truthtables.views.calculators.CalculatorEqTwoFunctionsFragment
import com.dikutenz.truthtables.views.calculators.CalculatorReducedAlphabetFragment
import com.dikutenz.truthtables.views.calculators.CalculatorWholeAlphabetFragment
import com.dikutenz.truthtables.views.results.ResultFragment
import com.dikutenz.truthtables.views.theories.LogicOperationsFragment
import com.dikutenz.truthtables.views.theories.TheoryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), FragmentChangeListener {

    private val mainViewModel: MainViewModel by viewModel()
    private val historyViewModel: HistoryViewModel by viewModel()
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initStartFragment()
        showRating()
    }

    private fun initStartFragment() {
        mainViewModel.inputType = InputType.REDUCED_ALPHABET
        mainViewModel.isSDNF = true
        mainViewModel.isSKNF = true
        mainViewModel.enterFinished = false
        replaceFragment(CalculatorReducedAlphabetFragment())
    }


    override fun replaceFragment(fragment: Fragment) {
        val idItemMenu =
            when (fragment.javaClass) {
                CalculatorReducedAlphabetFragment().javaClass, CalculatorWholeAlphabetFragment().javaClass, CalculatorBinaryFragment().javaClass -> R.id.action_calculator
                TheoryFragment().javaClass, LogicOperationsFragment().javaClass -> R.id.action_theory
                ResultFragment().javaClass -> R.id.action_result
                else -> R.id.action_calculator
            }
        bottomNav.menu.findItem(idItemMenu).isChecked = true
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun showRating() {
        historyViewModel.getAll().observeOnce(this) {
            if (it.isNotEmpty() && it.size.rem(10) == 0)
                RatingFragment().show(supportFragmentManager, "RatingFragment")
        }
    }

    private fun initUI() {
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener(navListener)
        bottomNav.menu.findItem(R.id.action_calculator).isChecked = true
    }

    private val navListener: NavigationBarView.OnItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            replaceFragment(when (item.itemId) {
                R.id.action_theory -> TheoryFragment()
                R.id.action_calculator -> mainViewModel.getFragmentByInputType()
                R.id.action_result -> ResultFragment()
                else -> CalculatorReducedAlphabetFragment()
            })
            true
        }

    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

}
