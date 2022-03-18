package hu.hm.familyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.model.ShoppingList

@Dao
abstract class FamilyDao {

    @Query("SELECT * FROM lists")
    abstract fun getAllShoppingLists(): List<RoomShoppingList>

    @Query("SELECT * FROM lists WHERE id = :listID")
    abstract fun getShoppingListById(listID: String): RoomShoppingList?

    @Insert
    protected abstract fun insertShoppingList(list: RoomShoppingList)

    @Insert
    protected abstract fun insertShoppingLists(lists: List<RoomShoppingList>)

    @Query("DELETE FROM lists WHERE id = :listID")
    protected abstract fun removeShoppingList(listID: String)

    @Query("DELETE FROM lists")
    protected abstract fun removeAllShoppingLists()

    @Transaction
    open fun setShoppingList(list: RoomShoppingList) {
        removeShoppingList(list.id)
        insertShoppingList(list)
    }

    @Transaction
    open fun setShoppingLists(lists: List<RoomShoppingList>) {
        removeAllShoppingLists()
        insertShoppingLists(lists)
    }
}
