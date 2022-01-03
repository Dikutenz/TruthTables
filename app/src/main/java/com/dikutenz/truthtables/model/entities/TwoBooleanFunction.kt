package com.dikutenz.truthtables.model.entities

import com.dikutenz.truthtables.model.Solve
import com.dikutenz.truthtables.model.enums.setVariables

class TwoBooleanFunction(var value1:String, var value2:String) {

    fun firstTruthTable(): ArrayList<Operand> {
        val values = addVariables()
        Solve.split(value1, values)
        return values
    }

    fun secondTruthTable(): ArrayList<Operand> {
        val values = addVariables()
        Solve.split(value2, values)
        values.reverse()
        return values
    }



    private fun addVariables(): ArrayList<Operand> {
        val values = arrayListOf<Operand>()
        val count01 = 2.pow(countVar())
        val variables = listVar()
        for (j in variables.indices) {
            val list01 = arrayListOf<Int>()
            var znachenie = 0
            val period = count01 / ((j + 1) * 2)
            for (i in 1..count01) {
                list01.add(znachenie)
                if (i % period == 0) znachenie = if (znachenie == 0) 1 else 0
            }
            values.add(Operand(variables[j], list01))
        }
        return values
    }

    private fun setVar(): Set<String> {
        val setVar = mutableSetOf<String>()
        for (ind in value1.indices)
            if (setVariables.contains(value1[ind])) setVar.add(value1[ind].toString())
        for (ind in value2.indices)
            if (setVariables.contains(value2[ind])) setVar.add(value2[ind].toString())
        return setVar
    }

    private fun listVar(): ArrayList<String> {
        val setVar = setVar()
        val list = arrayListOf<String>()
        for (item in setVar) list.add(item)
        return list
    }

    private fun countVar(): Int = setVar().size

    private fun Int.pow(n: Int): Int {
        var values = this
        for (i in 1 until n) values *= this
        return values
    }
}