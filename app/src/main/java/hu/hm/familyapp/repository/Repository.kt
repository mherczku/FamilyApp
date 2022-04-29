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
import hu.hm.familyapp.data.remote.models.RemoteCreateShoppingItem
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

    suspend fun syncronizeDBwithAPI() = withContext(Dispatchers.IO) {
    }

    suspend fun login() = withContext(Dispatchers.IO) {
        if (deviceOnline) {
        }
    }

    suspend fun getShoppingLists(): List<ShoppingList> = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            Timber.d("Getting Shopping Lists from API")
            val listIds = familyAPI.getShoppingListsByUser(user.id)
            val lists = mutableListOf<RoomShoppingList>()
            for (id in listIds) {
                val list = familyAPI.getShoppingList(id)
                val items = familyAPI.getShoppingListItemsFromList(id)

                lists.add(list.convertToRoomShoppingList(items))
            }
            familyDao.setShoppingLists(lists)
        }
        Timber.d("Loading Shopping Lists from DB")
        val list = familyDao.getAllShoppingLists().map {
            convertToShoppingList(it)
        }
        return@withContext list
    }

    suspend fun addShoppingList(shoppingList: RoomShoppingList) = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            Timber.d("Adding Shopping List to API")
            familyAPI.createShoppingList(
                shoppingList.convertToRemoteCreateShoppingList(
                    null
                )
            )
        } else {
            Timber.d("Adding Shopping List to DB")
            familyDao.insertShoppingList(shoppingList)
        }
    }

    suspend fun removeShoppingList(listID: Int) = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            Timber.d("Removing Shopping List $listID from API")
            familyAPI.deleteShoppingList(listID)
        } else {
            familyDao.removeShoppingList(listID)
            Timber.d("Removing Shopping List $listID from DB")
        }
    }

    suspend fun getListById(listID: String): ShoppingList? = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            Timber.d("Getting List $listID from API")
            val remoteList = familyAPI.getShoppingList(listID.toInt())
            val list = remoteList.convertToRoomShoppingList(familyAPI.getShoppingListItemsFromList(listID.toInt()))
            return@withContext convertToShoppingList(list)
        } else {
            Timber.d("Getting List $listID from DB")
            val list = familyDao.getShoppingListById(listID = listID) ?: return@withContext null
            return@withContext convertToShoppingList(list)
        }
    }

    suspend fun addShoppingItem(theListID: String, roomShoppingListItem: RoomShoppingListItem) = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            Timber.d("Adding Shopping Item to List $theListID to API")
            familyAPI.addShoppingListItem(theListID.toInt(), RemoteCreateShoppingItem(name = roomShoppingListItem.name, done = roomShoppingListItem.done))
        } else {
            Timber.d("Adding Shopping Item to List $theListID to DB")
            val list = familyDao.getShoppingListById(theListID)
            val items = list?.items?.toMutableList() ?: return@withContext
            items.add(roomShoppingListItem)
            list.items = items
            familyDao.setShoppingList(list)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun checkShoppingItem(theListID: String, roomShoppingListItem: RoomShoppingListItem) = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            if (roomShoppingListItem.done) {
                familyAPI.markDoneShoppingListItem(theListID.toInt(), roomShoppingListItem.id)
            } else familyAPI.markUndoneShoppingListItem(theListID.toInt(), roomShoppingListItem.id)
        } else {
            val list = familyDao.getShoppingListById(theListID)
            val items = list?.items?.toMutableList() ?: return@withContext
            items.removeIf { it.id == roomShoppingListItem.id }
            items.add(roomShoppingListItem)
            list.items = items
            familyDao.setShoppingList(list)
        }
    }
}
