package hu.hm.familyapp.repository

import hu.hm.familyapp.data.convertToShoppingList
import hu.hm.familyapp.data.local.FamilyDao
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.model.ShoppingList
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class Repository @Inject constructor(private val familyDao: FamilyDao) {

    suspend fun getShoppingLists(): List<ShoppingList> = withContext(Dispatchers.IO) {
        val list = familyDao.getAllShoppingLists().map {
            convertToShoppingList(it)
        }
        println("Size = " + list.size)
        return@withContext list
    }

    suspend fun addShoppingList(shoppingList: RoomShoppingList) = withContext(Dispatchers.IO) {
        familyDao.insertShoppingList(shoppingList)
    }

    suspend fun removeShoppingList(listID: String) = withContext(Dispatchers.IO) {
        familyDao.removeShoppingList(listID)
    }

    suspend fun getListById(listID: String): ShoppingList? = withContext(Dispatchers.IO) {
        val list = familyDao.getShoppingListById(listID = listID) ?: return@withContext null
        return@withContext convertToShoppingList(list)
    }

    suspend fun addShoppingItem(theListID: String, roomShoppingListItem: RoomShoppingListItem) = withContext(Dispatchers.IO) {
        val list = familyDao.getShoppingListById(theListID)
        val items = list?.items?.toMutableList() ?: return@withContext
        items.add(roomShoppingListItem)
        list.items = items
        familyDao.setShoppingList(list)
    }
}
