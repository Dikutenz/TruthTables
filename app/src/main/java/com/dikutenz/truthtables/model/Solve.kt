package com.dikutenz.truthtables.model

import android.util.Log
import com.dikutenz.truthtables.model.LogicOperations.andChar
import com.dikutenz.truthtables.model.LogicOperations.closingParenthesis
import com.dikutenz.truthtables.model.LogicOperations.eqChar
import com.dikutenz.truthtables.model.LogicOperations.getResultByOperation
import com.dikutenz.truthtables.model.LogicOperations.implyChar
import com.dikutenz.truthtables.model.LogicOperations.nandChar
import com.dikutenz.truthtables.model.LogicOperations.norChar
import com.dikutenz.truthtables.model.LogicOperations.not
import com.dikutenz.truthtables.model.LogicOperations.notChar
import com.dikutenz.truthtables.model.LogicOperations.openingParenthesis
import com.dikutenz.truthtables.model.LogicOperations.orChar
import com.dikutenz.truthtables.model.LogicOperations.xorChar
import com.dikutenz.truthtables.model.enums.CorrectInput

object Solve {

    private val setVariables = setOf(
        'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T',
        'U', 'V', 'W',
        'X', 'Y', 'Z'
    )
    private val setOperators: Set<Char> = setOf(
        andChar, orChar, implyChar, eqChar, notChar, xorChar, nandChar, norChar
    )
    private val listVariables = listOf(
        "A", "B", "C", "D",
        "E", "F", "G", "H",
        "I", "J", "K", "L",
        "M", "N", "O", "P",
        "Q", "R", "S", "T",
        "U", "V", "W",
        "X", "Y", "Z"
    )

    fun getTruthTable(s: String): ArrayList<Pair<String, ArrayList<Int>>> {
        return try {
            val values = addVariables(s)
            split(s, values)
            values
        } catch (e: StringIndexOutOfBoundsException) {
            arrayListOf()
        }

    }

    fun getShortTruthTable(s: String): ArrayList<Pair<String, ArrayList<Int>>> {
        val values = try {
            getTruthTable(s)
        } catch (e: StringIndexOutOfBoundsException) {
            arrayListOf()
        }
        val res: ArrayList<Pair<String, ArrayList<Int>>> = arrayListOf()
        for (i in 0 until countVar(s)) res.add(values[i])
        res.add(values[values.size - 1])
        return res
    }

    fun firstTruthTable(s1: String, s2: String): ArrayList<Pair<String, ArrayList<Int>>> {
        val values = addVariables(s1, s2)
        split(s1, values)
        return values
    }

    fun secondTruthTable(s1: String, s2: String): ArrayList<Pair<String, ArrayList<Int>>> {
        val values = addVariables(s1, s2)
        split(s2, values)
        values.reverse()
        return values
    }

    fun getBinaryTruthTable(booleanFunction: String): ArrayList<Pair<String, ArrayList<Int>>> {
        val values: ArrayList<Pair<String, ArrayList<Int>>> = arrayListOf()
        var count = 0
        var x = booleanFunction.length
        while (x > 0 && x % 2 == 0) {
            count++
            x /= 2
        }

        val count01 = 2.pow(count)
        for (j in 0 until count) {
            val list01 = arrayListOf<Int>()
            var znachenie = 0
            val period = count01 / ((j + 1) * 2)
            for (i in 1..count01) {
                list01.add(znachenie)
                if (i % period == 0) znachenie = if (znachenie == 0) 1 else 0
            }
            values.add(Pair(listVariables[j], list01))
        }
        val res = arrayListOf<Int>()
        for (i in booleanFunction.indices)
            res.add(booleanFunction[i].toString().toInt())
        values.add(Pair("Result", res))

        return values
    }

    private fun addVariables(s: String): ArrayList<Pair<String, ArrayList<Int>>> {
        val values = arrayListOf<Pair<String, ArrayList<Int>>>()
        val count01 = 2.pow(countVar(s))
        val variables = listVar(s)
        for (j in variables.indices) {
            val list01 = arrayListOf<Int>()
            var znachenie = 0
            val period = count01 / ((j + 1) * 2)
            for (i in 1..count01) {
                list01.add(znachenie)
                if (i % period == 0) znachenie = if (znachenie == 0) 1 else 0
            }
            values.add(Pair(variables[j], list01))
        }
        return values
    }

    private fun addVariables(s1: String, s2: String): ArrayList<Pair<String, ArrayList<Int>>> {
        val values = arrayListOf<Pair<String, ArrayList<Int>>>()
        val count01 = 2.pow(countVar(s1, s2))
        val variables = listVar(s1, s2)
        for (j in variables.indices) {
            val list01 = arrayListOf<Int>()
            var znachenie = 0
            val period = count01 / ((j + 1) * 2)
            for (i in 1..count01) {
                list01.add(znachenie)
                if (i % period == 0) znachenie = if (znachenie == 0) 1 else 0
            }
            values.add(Pair(variables[j], list01))
        }
        return values
    }

    private fun split(s: String, values: ArrayList<Pair<String, ArrayList<Int>>>): ArrayList<Int> {
        Log.d("s = ", s)
        if (s.length == 1) {
            return vars01(s, values)
        } else {
            val logicOpers: ArrayList<Pair<Char, Int>> = getLogicOpersAndPos(s)

            if (logicOpers.size > 0) {
                val iPrior = highestPriority(logicOpers)
                val result: ArrayList<Int> =
                    if (s[iPrior] == notChar) {
                        val x = split(s.substring(1, s.length), values)
                        not(x)
                    } else {
                        val x = split(s.substring(0, iPrior), values)
                        val y = split(s.substring(iPrior + 1, s.length), values)
                        getResultByOperation(x, s[iPrior], y)
                    }

                if (!contain(s, values)) values.add(Pair(s, result))
                return result
            } else {
                //убрать скобки если крайнее вышло скобки
                return split(s.substring(1, s.length - 1), values)
            }
        }
    }

    private fun getLogicOpersAndPos(s: String): ArrayList<Pair<Char, Int>> {
        val logicOpers: ArrayList<Pair<Char, Int>> = ArrayList()
        var level = 0
        for (i in s.indices) {
            if (s[i] in setOperators) {
                if (level == 0) logicOpers.add(Pair(s[i], i))
            } else if (s[i] == openingParenthesis) level++
            else if (s[i] == closingParenthesis) level--
        }
        return logicOpers
    }

    private fun highestPriority(logicOpers: ArrayList<Pair<Char, Int>>): Int {
        var iPrior = -1

        for (i in logicOpers.size - 1 downTo 0)
            if (logicOpers[i].first == norChar && iPrior == -1) iPrior = logicOpers[i].second

        if (iPrior == -1)
            for (i in logicOpers.size - 1 downTo 0)
                if (logicOpers[i].first == nandChar && iPrior == -1) iPrior = logicOpers[i].second

        if (iPrior == -1)
            for (i in logicOpers.size - 1 downTo 0)
                if (logicOpers[i].first == eqChar && iPrior == -1) iPrior = logicOpers[i].second

        if (iPrior == -1)
            for (i in logicOpers.size - 1 downTo 0)
                if (logicOpers[i].first == implyChar && iPrior == -1) iPrior = logicOpers[i].second

        if (iPrior == -1)
            for (i in logicOpers.size - 1 downTo 0)
                if (logicOpers[i].first == xorChar && iPrior == -1) iPrior = logicOpers[i].second

        if (iPrior == -1)
            for (i in logicOpers.size - 1 downTo 0)
                if (logicOpers[i].first == orChar && iPrior == -1) iPrior = logicOpers[i].second


        for (i in logicOpers.size - 1 downTo 0)
            if (logicOpers[i].first == andChar && iPrior == -1) iPrior = logicOpers[i].second

        if (iPrior == -1)
            for (i in logicOpers.size - 1 downTo 0)
                if (logicOpers[i].first == notChar && iPrior == -1) iPrior = logicOpers[i].second

        return iPrior
    }

    private fun vars01(s: String, values: ArrayList<Pair<String, ArrayList<Int>>>): ArrayList<Int> {
        val var01 = arrayListOf<Int>()
        for (item in values)
            if (item.first == s) return item.second
        return var01
    }

    private fun contain(
        s: String, values: ArrayList<Pair<String, ArrayList<Int>>>,
    ): Boolean {
        var b = false
        for (value in values)
            if (value.first == s) b = true
        return b
    }


    private fun listVar(s: String): ArrayList<String> {
        val setVar = setVar(s)
        val list = arrayListOf<String>()
        for (item in setVar) list.add(item)
        return list
    }

    private fun setVar(s: String): Set<String> {
        val setVar = mutableSetOf<String>()
        for (ind in s.indices)
            if (setVariables.contains(s[ind])) setVar.add(s[ind].toString())
        return setVar
    }

    private fun listVar(s1: String, s2: String): ArrayList<String> {
        val setVar = setVar(s1, s2)
        val list = arrayListOf<String>()
        for (item in setVar) list.add(item)
        return list
    }

    private fun setVar(s1: String, s2: String): Set<String> {
        val setVar = mutableSetOf<String>()
        for (ind in s1.indices)
            if (setVariables.contains(s1[ind])) setVar.add(s1[ind].toString())
        for (ind in s2.indices)
            if (setVariables.contains(s2[ind])) setVar.add(s2[ind].toString())
        return setVar
    }

    private fun Int.pow(n: Int): Int {
        var values = this
        for (i in 1 until n) values *= this
        return values
    }

    fun getCountVar(count01: Int): Int {
        var x = count01
        var count = 1
        while (x > 2) {
            count++
            x /= 2
        }
        return count
    }

    fun checkCorrect(booleanFunction: String): CorrectInput =
        when {
            countEntry(booleanFunction) > 0 -> CorrectInput.LACK_CLOSE_PARENTHESIS
            countEntry(booleanFunction) < 0 -> CorrectInput.LACK_OPEN_PARENTHESIS
            countVar(booleanFunction) == 0 -> CorrectInput.ZERO_VARIABLES
            countOpers(booleanFunction) == 0 -> CorrectInput.ZERO_OPERATORS
            booleanFunction[0] == ')' -> CorrectInput.FIRST_ERROR
            booleanFunction[0] in setOperators && booleanFunction[0] != notChar -> CorrectInput.FIRST_ERROR
            booleanFunction[booleanFunction.length - 1] == '(' -> CorrectInput.LAST_ERROR
            booleanFunction[booleanFunction.length - 1] in setOperators -> CorrectInput.LAST_ERROR
            isError(booleanFunction) -> CorrectInput.OTHER_ERROR
            else -> CorrectInput.OK
        }

    private fun countOpers(s: String): Int {
        var count = 0
        for (ind in s.indices)
            if (s[ind] in setOperators) count++
        return count
    }

    private fun countEntry(s: String): Int {
        var countEntry = 0
        for (ind in s.indices)
            if (s[ind] == openingParenthesis) countEntry++
            else if (s[ind] == closingParenthesis) countEntry--
        return countEntry
    }

    private fun countVar(s: String): Int = setVar(s).size
    private fun countVar(s1: String, s2: String): Int = setVar(s1, s2).size

    private fun isError(s: String): Boolean {
        for (i in 1 until s.length) {
            if (s[i - 1] in setVariables && s[i] in setVariables) return true
            if (s[i - 1] in setVariables && s[i] == notChar) return true
            if (s[i - 1] in setVariables && s[i] == openingParenthesis) return true

            if (s[i - 1] == notChar && s[i] in setOperators) return true
            if (s[i - 1] == notChar && s[i] == closingParenthesis) return true

            if (s[i - 1] in setOperators && s[i] in setOperators && s[i] != notChar) return true
            if (s[i - 1] in setOperators && s[i] == closingParenthesis) return true

            if (s[i - 1] == openingParenthesis && s[i] == closingParenthesis) return true
            if (s[i - 1] == openingParenthesis && s[i] in setOperators && s[i] != notChar) return true

            if (s[i - 1] == closingParenthesis && s[i] == openingParenthesis) return true
            if (s[i - 1] == closingParenthesis && s[i] == notChar) return true
        }
        return false
    }




}


