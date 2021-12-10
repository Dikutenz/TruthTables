package com.dikutenz.truthtables.model.repositories

import com.dikutenz.truthtables.model.AppDatabase
import com.dikutenz.truthtables.model.entities.BooleanFunction

class BooleanFunctionRepository(private val database: AppDatabase) {

    private val booleanFunctionDao = database.booleanFunctionDao

    fun getAll() = booleanFunctionDao.getAll()

    suspend fun insert(booleanFunction: BooleanFunction) {
        booleanFunctionDao.insert(booleanFunction)
    }

    suspend fun clear() {
        booleanFunctionDao.clear()
    }
}