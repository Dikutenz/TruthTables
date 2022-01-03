package com.dikutenz.truthtables.model.enums

import com.dikutenz.truthtables.model.LogicOperations
import com.dikutenz.truthtables.model.Solve

enum class CorrectInput {
    OK, // Всё верно
    LACK_OPEN_PARENTHESIS, // Недостаточно открывающих скобок
    LACK_CLOSE_PARENTHESIS, // Недостаточно закрывающих скобок
    ZERO_VARIABLES, // Ноль переменных
    ZERO_OPERATORS, // Ноль операторов
    FIRST_ERROR, // Ошибочный первый символ
    LAST_ERROR, // Ошибочный последний символ
    OTHER_ERROR // Другие ошибки
}

