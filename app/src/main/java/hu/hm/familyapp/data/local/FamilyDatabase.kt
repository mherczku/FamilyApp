package hu.hm.familyapp.data.local

import androidx.room.RoomDatabase

// @Database(entities = [Family::class], version = 1, exportSchema = true)
abstract class FamilyDatabase : RoomDatabase() {

    abstract fun familyDao(): FamilyDao
}
