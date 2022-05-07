package hu.hm.familyapp.repository

import android.os.Build
import androidx.annotation.RequiresApi
import hu.hm.familyapp.data.local.FamilyDao
import hu.hm.familyapp.data.local.convertToShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.data.remote.*
import hu.hm.familyapp.data.remote.models.*
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
    var currentUser: RemoteGetUser? = null

    suspend fun syncronizeDBwithAPI() = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            if (currentUser != null) {
                Timber.d("Synchronizing DB with API")
                val remoteListIds = familyAPI.getShoppingListsByUser(currentUser!!.id)
                val remoteLists = mutableListOf<RoomShoppingList>()
                for (id in remoteListIds) {
                    val list = familyAPI.getShoppingList(id)
                    val items = familyAPI.getShoppingListItemsFromList(id)
                    remoteLists.add(list.convertToRoomShoppingList(items))
                }

                for (remoteList in remoteLists) {
                    val localList = familyDao.getShoppingListById(remoteList.id.toString())
                    if (localList == null) {
                        familyDao.insertShoppingList(remoteList)
                    } else if (localList.lastModTime < remoteList.lastModTime) {
                        familyDao.setShoppingList(remoteList)
                    } else if (localList.lastModTime > remoteList.lastModTime) {
                        familyAPI.editShoppingList(
                            localList.id,
                            localList.convertToRemoteShoppingList()
                        )
                    }
                }
            } else Timber.d("Synchronization failed, currentUser = null")
        }
    }

    suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            try {
                Timber.d("Logging in with $email")
                currentUser = familyAPI.login(RemoteCreateUser(password, email))
            } catch (e: Exception) {
                Timber.d("Error while login: ${e.message}")
                return@withContext
            }
        }
    }

    suspend fun register(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            try {
                Timber.d("Registering for $email")
                val user = familyAPI.register(RemoteCreateUser(password, email))
                return@withContext true
            } catch (e: Exception) {
                Timber.d("Error while register: ${e.message}")
                return@withContext false
            }
        }
        return@withContext false
    }

    suspend fun getInvite(): RemoteGetInvite? = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            Timber.d("Getting invite for user")
            return@withContext familyAPI.getUserInvite(currentUser!!.id)
        }
        return@withContext null
    }

    suspend fun invite(email: String) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            Timber.d("Inviting $email to family ${currentUser!!.familyID}")
            familyAPI.inviteUser(RemoteCreateInvite(email, currentUser!!.familyID!!.toInt()))
        }
    }

    suspend fun editProfile(newUserData: RemoteUser) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            Timber.d("Updating profile ${currentUser!!.id}")
            familyAPI.editUser(currentUser!!.id, newUserData)
        }
    }

    suspend fun getFamily(): RemoteGetFamily? = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            if (currentUser!!.familyID == null) {
                Timber.d("Current user has no family ID")
                return@withContext null
            }
            Timber.d("Getting user family data ${currentUser!!.id}, ${currentUser!!.familyID}")
            return@withContext familyAPI.getFamily(currentUser!!.familyID!!.toInt())
        }
        return@withContext null
    }

    suspend fun editFamily(newFamilyData: RemoteFamily) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            if (currentUser!!.familyID != null) {
                Timber.d("Updating family ${currentUser!!.familyID}")
                familyAPI.editFamily(currentUser!!.familyID!!.toInt(), newFamilyData)
            } else Timber.d("User has no family ID")
        }
    }

    suspend fun createFamily(newFamily: RemoteFamily): RemoteGetFamily? =
        withContext(Dispatchers.IO) {
            if (deviceOnline && currentUser != null) {
                Timber.d("Creating family for ${currentUser!!.id}")
                return@withContext familyAPI.createFamily(newFamily)
            }
            return@withContext null
        }

    suspend fun getShoppingLists(): List<ShoppingList> = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            Timber.d("Getting Shopping Lists from API")
            if (currentUser != null) {
                val listIds = familyAPI.getShoppingListsByUser(currentUser!!.id)
                val lists = mutableListOf<RoomShoppingList>()
                for (id in listIds) {
                    val list = familyAPI.getShoppingList(id)
                    val items = familyAPI.getShoppingListItemsFromList(id)

                    lists.add(list.convertToRoomShoppingList(items))
                }
                familyDao.setShoppingLists(lists)
            } else Timber.d("Getting Shopping Lists from API Failed, currentUser = null")
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
            val list =
                remoteList.convertToRoomShoppingList(familyAPI.getShoppingListItemsFromList(listID.toInt()))
            return@withContext convertToShoppingList(list)
        } else {
            Timber.d("Getting List $listID from DB")
            val list = familyDao.getShoppingListById(listID = listID) ?: return@withContext null
            return@withContext convertToShoppingList(list)
        }
    }

    suspend fun addShoppingItem(theListID: String, roomShoppingListItem: RoomShoppingListItem) =
        withContext(Dispatchers.IO) {
            if (deviceOnline) {
                Timber.d("Adding Shopping Item to List $theListID to API")
                familyAPI.addShoppingListItem(
                    theListID.toInt(),
                    RemoteCreateShoppingItem(
                        name = roomShoppingListItem.name,
                        done = roomShoppingListItem.done
                    )
                )
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
    suspend fun checkShoppingItem(theListID: String, roomShoppingListItem: RoomShoppingListItem) =
        withContext(Dispatchers.IO) {
            if (deviceOnline) {
                if (roomShoppingListItem.done) {
                    familyAPI.markDoneShoppingListItem(theListID.toInt(), roomShoppingListItem.id)
                } else familyAPI.markUndoneShoppingListItem(
                    theListID.toInt(),
                    roomShoppingListItem.id
                )
            } else {
                val list = familyDao.getShoppingListById(theListID)
                val items = list?.items?.toMutableList() ?: return@withContext
                items.removeIf { it.id == roomShoppingListItem.id }
                items.add(roomShoppingListItem)
                list.items = items
                familyDao.setShoppingList(list)
            }
        }

    suspend fun getEventsByUser(): List<RemoteEvent>? = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            return@withContext familyAPI.getEventsByUser(currentUser!!.id)
        }
        return@withContext null
    }

    suspend fun createEvent(event: RemoteEvent) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            familyAPI.createEvent(event = event)
        }
    }

    suspend fun deleteEvent(eventID: Int) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            familyAPI.deleteEvent(eventID)
        }
    }
}
