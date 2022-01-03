package com.dikutenz.truthtables.model

import android.util.Log
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.enums.CorrectInput
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.model.enums.setOperators
import com.dikutenz.truthtables.model.enums.setVariables

object CorrectError {

    fun checkCorrect(booleanFunction: String): CorrectInput {
        val bf = BooleanFunction(value = booleanFunction,
            inputType = InputType.WHOLE_ALPHABET.toString())
        return when {
            bf.variables.isEmpty() -> CorrectInput.ZERO_VARIABLES
            countEntry(booleanFunction) > 0 -> CorrectInput.LACK_CLOSE_PARENTHESIS
            countEntry(booleanFunction) < 0 -> CorrectInput.LACK_OPEN_PARENTHESIS
            countOpers(booleanFunction) == 0 -> CorrectInput.ZERO_OPERATORS
            booleanFunction[0] == ')' -> CorrectInput.FIRST_ERROR
            booleanFunction[0] in setOperators && booleanFunction[0] != LogicOperations.notChar -> CorrectInput.FIRST_ERROR
            booleanFunction[booleanFunction.length - 1] == '(' -> CorrectInput.LAST_ERROR
            booleanFunction[booleanFunction.length - 1] in setOperators -> CorrectInput.LAST_ERROR
            isError(booleanFunction) -> CorrectInput.OTHER_ERROR
            else -> CorrectInput.OK
        }
    }

    private fun isError(s: String): Boolean {
        for (i in 1 until s.length) {
            if (s[i - 1] in setVariables && s[i] in setVariables) return true
            if (s[i - 1] in setVariables && s[i] == LogicOperations.notChar) return true
            if (s[i - 1] in setVariables && s[i] == LogicOperations.openingParenthesis) return true

            if (s[i - 1] == LogicOperations.notChar && s[i] in setOperators) return true
            if (s[i - 1] == LogicOperations.notChar && s[i] == LogicOperations.closingParenthesis) return true

            if (s[i - 1] in setOperators && s[i] in setOperators && s[i] != LogicOperations.notChar) return true
            if (s[i - 1] in setOperators && s[i] == LogicOperations.closingParenthesis) return true

            if (s[i - 1] == LogicOperations.openingParenthesis && s[i] == LogicOperations.closingParenthesis) return true
            if (s[i - 1] == LogicOperations.openingParenthesis && s[i] in setOperators && s[i] != LogicOperations.notChar) return true

            if (s[i - 1] == LogicOperations.closingParenthesis && s[i] == LogicOperations.openingParenthesis) return true
            if (s[i - 1] == LogicOperations.closingParenthesis && s[i] == LogicOperations.notChar) return true
        }
        return false
    }

    //количество операторов
    fun countOpers(booleanFunction: String): Int {
        var count = 0
        for (ind in booleanFunction.indices)
            if (booleanFunction[ind] in setOperators) count++
        return count
    }

    //количество вхождений
    fun countEntry(booleanFunction: String): Int {
        var countEntry = 0
        for (ind in booleanFunction.indices)
            if (booleanFunction[ind] == LogicOperations.openingParenthesis) countEntry++
            else if (booleanFunction[ind] == LogicOperations.closingParenthesis) countEntry--
        return countEntry
    }
}