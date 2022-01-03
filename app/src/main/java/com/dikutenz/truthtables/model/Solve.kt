package com.dikutenz.truthtables.model

import android.util.Log
import com.dikutenz.truthtables.model.LogicOperations.closingParenthesis
import com.dikutenz.truthtables.model.LogicOperations.getPriorByOper
import com.dikutenz.truthtables.model.LogicOperations.getResultByOper
import com.dikutenz.truthtables.model.LogicOperations.not
import com.dikutenz.truthtables.model.LogicOperations.notChar
import com.dikutenz.truthtables.model.LogicOperations.openingParenthesis
import com.dikutenz.truthtables.model.entities.LogicOperInFun
import com.dikutenz.truthtables.model.entities.Operand
import com.dikutenz.truthtables.model.enums.setOperators

object Solve {

    fun split(s: String, values: ArrayList<Operand>): ArrayList<Int> {
        Log.d("s = ", s)
        if (s.length == 1) {
            return vars01(s, values)
        } else {
            val logicOpers: ArrayList<LogicOperInFun> = getListLogicOpersInFun(s)

            if (logicOpers.size > 0) {
                val iPrior = indHighestPriority(logicOpers)
                val result: ArrayList<Int> =
                    if (s[iPrior] == notChar) {
                        val x = split(s.substring(1, s.length), values)
                        not(x)
                    } else {
                        val x = split(s.substring(0, iPrior), values)
                        val y = split(s.substring(iPrior + 1, s.length), values)
                        getResultByOper(x, s[iPrior], y)
                    }
//
                if (values.none { it.name == s }) values.add(Operand(s, result))
                return result
            } else {
                //убрать скобки если крайнее вышло скобки
                return split(s.substring(1, s.length - 1), values)
            }
        }
    }

    private fun getListLogicOpersInFun(s: String): ArrayList<LogicOperInFun> {
        val logicOpers: ArrayList<LogicOperInFun> = ArrayList()
        var level = 0 //уровень вхождения в скобки
        for (i in s.indices)
            if (s[i] in setOperators && level == 0) logicOpers.add(LogicOperInFun(s[i], i))
            else if (s[i] == openingParenthesis) level++
            else if (s[i] == closingParenthesis) level--

        return logicOpers
    }

    private fun indHighestPriority(logicOpers: ArrayList<LogicOperInFun>): Int {
        var iPrior = logicOpers[0].position
        var operPrior = logicOpers[0].name

        for (i in 1 until logicOpers.size)
            if (getPriorByOper(logicOpers[i].name) < getPriorByOper(operPrior)) {
                operPrior = logicOpers[i].name
                iPrior = logicOpers[i].position
            }

        return iPrior
    }

    //функция берет нули и единицы переменных
    private fun vars01(s: String, values: ArrayList<Operand>): ArrayList<Int> =
        values.filter { it.name == s }[0].values


    fun getCountVar(count01: Int): Int {
        var x = count01
        var count = 1
        while (x > 2) {
            count++
            x /= 2
        }
        return count
    }
}


