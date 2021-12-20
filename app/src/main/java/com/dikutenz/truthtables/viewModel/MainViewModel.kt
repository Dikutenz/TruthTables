package com.dikutenz.truthtables.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.model.enums.InputType.*

class MainViewModel : ViewModel() {

    var isWholeTable = true
    var isSDNF = true
    var isSKNF = true
    var inputType = REDUCED_ALPHABET

    var booleanFunction: MutableLiveData<String> = MutableLiveData("")
    var secondBooleanFunction: MutableLiveData<String> = MutableLiveData("")
    var enterFinished: Boolean = false

    fun addChar(char: Char) {
        enterFinished = false
        booleanFunction.value = booleanFunction.value + char
    }

    fun addCharSecond(char: Char) {
        enterFinished = false
        secondBooleanFunction.value = secondBooleanFunction.value + char
    }

    fun clearFunction() {
        enterFinished = false
        booleanFunction.value = ""
    }

    fun clearFunctionSecond() {
        enterFinished = false
        secondBooleanFunction.value = ""
    }

    fun backspaceChar() {
        enterFinished = false
        val bs = booleanFunction.value!!
        booleanFunction.value = bs.substring(0, bs.length - 1)
    }

    fun backspaceCharSecond() {
        enterFinished = false
        val bs = secondBooleanFunction.value!!
        secondBooleanFunction.value = bs.substring(0, bs.length - 1)
    }

    fun getInputTypeToString(): String = when (inputType) {
        WHOLE_ALPHABET -> "whole_alphabet"
        REDUCED_ALPHABET -> "reduce_alphabet"
        BINARY -> "binary"
        EQUIVALENCE_FUNCTION -> "equivalence_function"
    }

    private fun getStringToInputType(inputType: String): InputType = when (inputType) {
        "whole_alphabet" -> WHOLE_ALPHABET
        "reduce_alphabet" -> REDUCED_ALPHABET
        "binary" -> BINARY
        "equivalence_function" -> EQUIVALENCE_FUNCTION
        else -> WHOLE_ALPHABET
    }

    fun setHistory(bf: BooleanFunction) {
        inputType = getStringToInputType(bf.inputType)
        booleanFunction.value = bf.value
        enterFinished = true
    }
}