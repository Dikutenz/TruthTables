package com.dikutenz.truthtables.model

object LogicOperations {

    var notChar = '¬'
    var andChar = '∧'
    var orChar = '∨'
    var implyChar = '→'
    var eqChar = '⇔'
    var xorChar = '⊕'
    var norChar = '↓'
    var nandChar = '|'
    var openingParenthesis = '('
    var closingParenthesis = ')'

    fun getPriorByOper(logicOpers: Char) =
        when (logicOpers) {
            norChar -> 1
            eqChar -> 2
            implyChar -> 3
            xorChar -> 4
            orChar -> 5
            andChar -> 6
            notChar -> 7
            else -> 10
        }


    fun getResultByOper(x: ArrayList<Int>, char: Char, y: ArrayList<Int>): ArrayList<Int> {
        var result: ArrayList<Int> = arrayListOf()
        when {
            (char == andChar) -> result = and(x, y)
            (char == orChar) -> result = or(x, y)
            (char == xorChar) -> result = xor(x, y)
            (char == implyChar) -> result = imply(x, y)
            (char == eqChar) -> result = eq(x, y)
            (char == nandChar) -> result = nand(x, y)
            (char == norChar) -> result = nor(x, y)
        }
        return result
    }

    private fun and(x: ArrayList<Int>, y: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == 1 && y[i] == 1) result.add(1)
            else result.add(0)
        return result
    }

    private fun or(x: ArrayList<Int>, y: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == 1 || y[i] == 1) result.add(1)
            else result.add(0)
        return result
    }

    fun not(x: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == 1) result.add(0)
            else result.add(1)
        return result
    }

    private fun imply(x: ArrayList<Int>, y: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == 1 && y[i] == 0) result.add(0)
            else result.add(1)
        return result
    }

    private fun eq(x: ArrayList<Int>, y: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == y[i]) result.add(1)
            else result.add(0)
        return result
    }

    private fun xor(x: ArrayList<Int>, y: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == y[i]) result.add(0)
            else result.add(1)
        return result
    }

    private fun nor(x: ArrayList<Int>, y: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == 0 && y[i] == 0) result.add(1)
            else result.add(0)
        return result
    }

    private fun nand(x: ArrayList<Int>, y: ArrayList<Int>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        for (i in x.indices)
            if (x[i] == 1 && y[i] == 1) result.add(0)
            else result.add(1)
        return result
    }
}