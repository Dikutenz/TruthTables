package com.dikutenz.truthtables.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.FragmentChangeListener
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.model.enums.Topic.CREATE_TRUTH_TABLE
import com.dikutenz.truthtables.model.enums.Topic.EQUIVALENCE_FUNCTION
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
        incEntrance()

    }

    private fun initStartFragment() {
        mainViewModel.topic = CREATE_TRUTH_TABLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TopicFragment())
            .commit()
    }

    private val navListener: NavigationBarView.OnItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            val selectedFragment: Fragment =
                when (item.itemId) {
                    R.id.action_theory -> TheoryFragment()
                    R.id.action_start -> TopicFragment()
                    R.id.action_solve ->
                        if (mainViewModel.topic == EQUIVALENCE_FUNCTION) {
                            CalculatorEqTwoFunctionsFragment()
                        } else {
                            when (mainViewModel.inputType) {
                                InputType.REDUCED_ALPHABET -> CalculatorReducedAlphabetFragment()
                                InputType.WHOLE_ALPHABET -> CalculatorWholeAlphabetFragment()
                                InputType.BINARY -> CalculatorBinaryFragment()
                            }
                        }
                    R.id.action_result -> ResultFragment()
                    else -> TopicFragment()
                }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
            true
        }

    override fun replaceFragment(fragment: Fragment) {
        val idItemMenu =
            when (fragment.javaClass) {
                CalculatorReducedAlphabetFragment().javaClass, CalculatorWholeAlphabetFragment().javaClass, CalculatorBinaryFragment().javaClass -> R.id.action_solve
                TheoryFragment().javaClass, LogicOperationsFragment().javaClass -> R.id.action_theory
                TopicFragment().javaClass, SettingCreateTruthTableFragment().javaClass -> R.id.action_start
                ResultFragment().javaClass -> R.id.action_result
                else -> R.id.action_start
            }
        bottomNav.menu.findItem(idItemMenu).isChecked = true
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun incEntrance() {
        historyViewModel.getAll().observeOnce(this) {
            if (it.isNotEmpty() && it.size.rem(10) == 0)
                RatingFragment().show(supportFragmentManager, "RatingFragment")
        }
    }

    private fun initUI() {
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener(navListener)
        bottomNav.menu.findItem(R.id.action_start).isChecked = true
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
