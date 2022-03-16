package hu.hm.familyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.hm.familyapp.data.local.model.RoomShoppingList

@Database(entities = [RoomShoppingList::class], version = 1, exportSchema = true)
abstract class FamilyDatabase : RoomDatabase() {

    abstract fun familyDao(): FamilyDao
}
