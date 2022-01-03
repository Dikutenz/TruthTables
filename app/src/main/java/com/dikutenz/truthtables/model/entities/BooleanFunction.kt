package com.dikutenz.truthtables.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dikutenz.truthtables.model.Solve
import com.dikutenz.truthtables.model.enums.InputType
import com.dikutenz.truthtables.model.enums.listVariables

@Entity(tableName = "boolean_function")
data class BooleanFunction(
    @PrimaryKey @ColumnInfo(name = "boolean_function") val value: String,
    @ColumnInfo(name = "input_type") val inputType: String,
) {
    @Ignore
    var variables: MutableList<Operand> = mutableListOf()

    init {
        for (ind in value.indices)
            if (listVariables.contains(value[ind].toString()) && variables.none { it.name == value[ind].toString() })
                variables.add(Operand(value[ind].toString(), ArrayList()))

        val count01 = 2.pow(variables.size)
        for (j in variables.indices) {
            val list01 = arrayListOf<Int>()
            var current = 0
            var period = count01
            for (cv in 0..j) period /= 2

            for (i in 1..count01) {
                list01.add(current)
                if (i % period == 0) current = if (current == 0) 1 else 0
            }
            variables[j].values = list01
        }
    }

    //получить таблицу истинности по типу функции
    fun getTruthTable(isWhole: Boolean) =
        if (inputType == InputType.BINARY.toString()) getBinaryTruthTable()
        else if (isWhole) getAlphabetTruthTable()
        else getShortTruthTable()

    //получить таблицу истинности алфавитных переменных
    private fun getAlphabetTruthTable(): ArrayList<Operand> {
        val values = variables as ArrayList<Operand>
        Solve.split(value, values)
        return values
    }

    //получить сокращенную таблицу истинности алфавитных прееменных
    private fun getShortTruthTable(): ArrayList<Operand> {
        val values = getAlphabetTruthTable()
        val res: ArrayList<Operand> = arrayListOf()
        for (i in variables.indices) res.add(values[i])
        res.add(values[values.size - 1])
        return res
    }

    //получить тиблицу истинности бинарных переменных
    private fun getBinaryTruthTable(): ArrayList<Operand> {
        val values: ArrayList<Operand> = arrayListOf()
        var count = 0
        var x = value.length
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
            values.add(Operand(listVariables[j], list01))
        }
        val res = arrayListOf<Int>()
        for (i in value.indices)
            res.add(value[i].toString().toInt())
        values.add(Operand("Result", res))

        return values
    }

    //степень числа
    private fun Int.pow(n: Int): Int {
        var values = this
        for (i in 1 until n) values *= this
        return values
    }
}