package com.example.dragitem

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.dragitem.fragment.CheckCallButtonStateDao

@Database(entities = [CheckCallButtonState::class], version = 1)
abstract class CheckCallButtonStateDataBase: RoomDatabase() {

    abstract fun checkCallButtonStateDao(): CheckCallButtonStateDao
    companion object{
        private var INSTANCE: CheckCallButtonStateDataBase? = null

        fun getCheckCallButtonStateDataBase(context: Context): CheckCallButtonStateDataBase?{
            if (INSTANCE == null){
                INSTANCE =
                    Room.databaseBuilder<CheckCallButtonStateDataBase>(context,
                        CheckCallButtonStateDataBase::class.java, "DataBaseCheckCallState")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return INSTANCE
        }
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

}