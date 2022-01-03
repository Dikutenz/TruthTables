package com.dikutenz.truthtables.model.repositories

import com.dikutenz.truthtables.model.AppDatabase
import com.dikutenz.truthtables.model.entities.BooleanFunction

class BooleanFunctionRepository(database: AppDatabase) {

    private val booleanFunctionDao = database.booleanFunctionDao

    fun getAll() = booleanFunctionDao.getAll()

    suspend fun insert(booleanFunction: BooleanFunction) {
        //Log.d("insert repository", booleanFunction.value)
        booleanFunctionDao.insert(booleanFunction)
    }

    suspend fun clear() {
        booleanFunctionDao.clear()
    }
}