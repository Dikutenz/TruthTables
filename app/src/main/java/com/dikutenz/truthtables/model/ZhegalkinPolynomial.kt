package com.dikutenz.truthtables.model

import android.util.Log
import com.dikutenz.truthtables.model.Solve.getCountVar
import com.dikutenz.truthtables.model.entities.BooleanFunction
import com.dikutenz.truthtables.model.entities.Operand
import com.dikutenz.truthtables.model.enums.InputType

object ZhegalkinPolynomial {

    fun getPolynomial(truthTable: ArrayList<Operand>): String {
        val bf = BooleanFunction(value = truthTable[truthTable.size - 1].name,
            inputType = InputType.REDUCED_ALPHABET.toString())
        val res = truthTable[truthTable.size - 1].values
        val variables = bf.variables

        val terms = ArrayList<String>(res.size)
        terms.add(0, "1")
        for (i in 1 until res.size) {
            var term = ""
            for (j in variables.indices)
                if (truthTable[j].values[i] == 1) term += variables[j].name
            terms.add(term)
        }

        val a = ArrayList<Int>(res.size)
        a.add(res[0])
        for (i in 1 until res.size) {
            val vars: CharArray = terms[i].toCharArray()

            var f = a[0]
            for (t in terms)
                if (allContains(t, vars)) {
                    val nextV = getIByT(t, terms)
                    f = if (nextV < a.size) {
                        if (f == a[nextV]) 0 else 1
                    } else {
                        if (res[i] == 0) f else if (f == 0) 1 else 0
                    }
                }
            a.add(f)
        }

        var s = ""
        for (i in terms.indices)
            if (a[i] == 1) s += ("${terms[i]}${LogicOperations.xorChar}")
        return s
    }

    private fun allContains(t: String, vars: CharArray): Boolean {
        var isContains = true
        for (i in t.indices)
            if (!vars.contains(t[i])) isContains = false
        return isContains
    }

    private fun getIByT(term: String, terms: List<String>): Int {
        var ia = -1
        for (i in terms.indices)
            if (terms[i] == term) ia = i
        return ia
    }
}
