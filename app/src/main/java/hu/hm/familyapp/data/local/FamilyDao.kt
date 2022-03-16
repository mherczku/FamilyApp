package hu.hm.familyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.model.ShoppingList

@Dao
abstract class FamilyDao {

    @Query("SELECT * FROM shoppingLists")
    abstract fun getAllShoppingLists(): List<RoomShoppingList>

    @Query("SELECT * FROM shoppingLists WHERE id = :listID")
    abstract fun getShoppingListById(listID: String): RoomShoppingList?

    @Insert
    protected abstract fun insertShoppingList(list: ShoppingList)

    @Insert
    protected abstract fun insertShoppingLists(lists: List<ShoppingList>)

    @Query("DELETE FROM shoppingLists WHERE id = :listID")
    protected abstract fun removeShoppingList(listID: String)

    @Query("DELETE FROM shoppingLists")
    protected abstract fun removeAllShoppingLists()

    @Transaction
    open fun setShoppingList(list: ShoppingList) {
        removeShoppingList(list.id)
        insertShoppingList(list)
    }

    @Transaction
    open fun setShoppingLists(lists: List<ShoppingList>) {
        removeAllShoppingLists()
        insertShoppingLists(lists)
    }
}