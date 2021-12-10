package com.dikutenz.truthtables.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dikutenz.truthtables.model.daos.BooleanFunctionDao
import com.dikutenz.truthtables.model.entities.BooleanFunction

@Database(
    entities = [BooleanFunction::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract val booleanFunctionDao: BooleanFunctionDao
    //abstract val entranceDao: EntranceDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "truth_tables"
                    )
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS entrance (primary_key INTEGER, entrance INTEGER, PRIMARY KEY(primary_key))")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS entrance")
    }
}