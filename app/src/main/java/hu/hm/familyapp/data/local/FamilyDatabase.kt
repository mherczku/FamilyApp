package hu.hm.familyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import hu.hm.familyapp.data.local.model.ListConverter
import hu.hm.familyapp.data.local.model.RoomShoppingList

@Database(entities = [RoomShoppingList::class], version = 1, exportSchema = true)
@TypeConverters(ListConverter::class)
abstract class FamilyDatabase : RoomDatabase() {

    abstract fun familyDao(): FamilyDao
}
