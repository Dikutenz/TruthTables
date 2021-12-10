package com.dikutenz.truthtables.model.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dikutenz.truthtables.model.entities.BooleanFunction

@Dao
interface BooleanFunctionDao {

    @Query("SELECT * FROM boolean_function")
    fun getAll(): LiveData<List<BooleanFunction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booleanFunction: BooleanFunction)

    @Query("DELETE FROM boolean_function")
    suspend fun clear()

}