package com.dikutenz.truthtables.model

import com.dikutenz.truthtables.model.LogicOperations.andChar
import com.dikutenz.truthtables.model.LogicOperations.notChar
import com.dikutenz.truthtables.model.LogicOperations.orChar
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.entities.Operand
import com.dikutenz.truthtables.model.enums.InputType

object SNF {

    fun getSDNF(truthTable: ArrayList<Operand>): String {
        val res = truthTable[truthTable.size - 1].values
        var s = ""
        val variables = arrayListOf<String>()
        for (i in 0 until Solve.getCountVar(res.size)) variables.add(truthTable[i].name)
        for (i in 0 until res.size) {
            if (res[i] == 1) {
                s += LogicOperations.openingParenthesis
                for (j in 0 until variables.size) {
                    if (truthTable[j].values[i] == 0) s += notChar
                    s += variables[j]
                    if (j < (variables.size - 1)) s += andChar
                }
                s += LogicOperations.closingParenthesis
                s += orChar
            }
        }
        return if (s.isNotEmpty()) s.substring(0, s.length - 1) else "не существует"
    }

    fun getSKNF(truthTable: ArrayList<Operand>): String {
        val res = truthTable[truthTable.size - 1].values
        var s = ""
        val variables = arrayListOf<String>()
        for (i in 0 until Solve.getCountVar(res.size)) variables.add(truthTable[i].name)
        for (i in 0 until res.size) {
            if (res[i] == 0) {
                s += LogicOperations.openingParenthesis
                for (j in 0 until variables.size) {
                    if (truthTable[j].values[i] == 1) s += notChar
                    s += variables[j]
                    if (j < (variables.size - 1)) s += orChar
                }
                s += LogicOperations.closingParenthesis
                s += andChar
            }
        }
        return if (s.isNotEmpty()) s.substring(0, s.length - 1) else "не существует"
    }

    fun getStep1SDNF(s: String, inputType: InputType): String {
        val bf = BooleanFunction(value = s, inputType = inputType.toString())
        var step1 = ""
        val values = bf.getTruthTable(false)
        val res = values.last().values
        for (i in res.indices) {
            if (res[i] == 1) {
                step1 += "{"
                for (j in 0 until values.size - 1) {
                    step1 += " ${values[j].values[i]}"
                    step1 += if (j < values.size - 2) "," else " "
                }
                step1 += "} "
            }
        }
        return step1
    }

    fun getStep2SDNF(s: String, inputType: InputType): String {
        val bf = BooleanFunction(value = s, inputType = inputType.toString())
        var step2 = ""
        val values = bf.getTruthTable(false)
        val res = values.last().values
        var count = 1
        for (i in res.indices) {
            if (res[i] == 1) {
                step2 += "K$count : {"
                for (j in 0 until values.size - 1) {
                    step2 += " ${values[j].values[i]}"
                    step2 += if (j < values.size - 2) "," else " "
                }
                step2 += "} = "
                for (j in 0 until values.size - 1) {
                    if (values[j].values[i] == 0) step2 += notChar
                    step2 += values[j].name
                    if (j < values.size - 2) step2 += andChar
                }
                step2 += "\n"
                count++
            }
        }
        return step2
    }

    fun getStep1SKNF(s: String, inputType: InputType): String {
        val bf = BooleanFunction(value = s, inputType = inputType.toString())
        var step1 = ""
        val values = bf.getTruthTable(false)
        val res = values.last().values
        for (i in res.indices) {
            if (res[i] == 0) {
                step1 += "{"
                for (j in 0 until values.size - 1) {
                    step1 += " ${values[j].values[i]}"
                    step1 += if (j < values.size - 2) "," else " "
                }
                step1 += "}"
            }
        }
        return step1
    }

    fun getStep2SKNF(s: String, inputType: InputType): String {
        val bf = BooleanFunction(value = s, inputType = inputType.toString())
        var step2 = ""
        val values = bf.getTruthTable(false)
        val res = values.last().values
        var count = 1
        for (i in res.indices) {
            if (res[i] == 0) {
                step2 += "K$count : {"
                for (j in 0 until values.size - 1) {
                    step2 += " ${values[j].values[i]}"
                    step2 += if (j < values.size - 2) "," else " "
                }
                step2 += "} = "
                for (j in 0 until values.size - 1) {
                    if (values[j].values[i] == 1) step2 += notChar
                    step2 += values[j].name
                    if (j < values.size - 2) step2 += orChar
                }
                step2 += "\n"
                count++
            }
        }
        return step2
    }
}