package hu.hm.familyapp.repository

import android.os.Build
import androidx.annotation.RequiresApi
import hu.hm.familyapp.data.local.FamilyDao
import hu.hm.familyapp.data.local.convertToShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.data.model.User
import hu.hm.familyapp.data.remote.FamilyAPI
import hu.hm.familyapp.data.remote.convertToRemoteCreateShoppingList
import hu.hm.familyapp.data.remote.convertToRoomShoppingList
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@Singleton
class Repository @Inject constructor(
    private val familyDao: FamilyDao,
    private val familyAPI: FamilyAPI
) {

    var deviceOnline: Boolean = false
    var user: User = User()

    suspend fun getShoppingLists(): List<ShoppingList> = withContext(Dispatchers.IO) {

        if (deviceOnline) {
            val listIds = familyAPI.getShoppingListsByUser(user.id)
            val lists = mutableListOf<RoomShoppingList>()
            for (id in listIds) {
                val list = familyAPI.getShoppingList(id)
                val items = familyAPI.getShoppingListItemsFromList(id)

                lists.add(list.convertToRoomShoppingList(items))
            }
            familyDao.setShoppingLists(lists)
        }
        val list = familyDao.getAllShoppingLists().map {
            convertToShoppingList(it)
        }
        return@withContext list
    }

    suspend fun addShoppingList(shoppingList: RoomShoppingList) = withContext(Dispatchers.IO) {
        familyDao.insertShoppingList(shoppingList)
        Timber.wtf("ad")
        familyAPI.createShoppingList(
            shoppingList.convertToRemoteCreateShoppingList(
                null
            )
        )
    }

    suspend fun removeShoppingList(listID: Int) = withContext(Dispatchers.IO) {
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

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun checkShoppingItem(theListID: String, roomShoppingListItem: RoomShoppingListItem) = withContext(Dispatchers.IO) {
        val list = familyDao.getShoppingListById(theListID)
        val items = list?.items?.toMutableList() ?: return@withContext
        items.removeIf { it.id == roomShoppingListItem.id }
        items.add(roomShoppingListItem)
        list.items = items
        familyDao.setShoppingList(list)
    }
}
