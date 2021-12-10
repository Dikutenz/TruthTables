package com.dikutenz.truthtables.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boolean_function")
data class BooleanFunction(
    @PrimaryKey @ColumnInfo(name = "boolean_function") val value: String,
    @ColumnInfo(name = "input_type") val inputType: String
) {
}