package com.dikutenz.truthtables.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.repositories.BooleanFunctionRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: BooleanFunctionRepository) : ViewModel() {

    fun getAll() = repository.getAll()

    fun insert(booleanFunction: BooleanFunction) {
        viewModelScope.launch {
            repository.insert(booleanFunction)
        }
    }

    fun clear() {
        viewModelScope.launch {
            repository.clear()
        }
    }

}
