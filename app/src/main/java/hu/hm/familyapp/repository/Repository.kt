package hu.hm.familyapp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.hm.familyapp.data.local.FamilyDao
import hu.hm.familyapp.data.local.convertToShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.data.remote.*
import hu.hm.familyapp.data.remote.models.*
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@Singleton
class Repository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val familyDao: FamilyDao,
    private val familyAPI: FamilyAPI
) {
    var deviceOnline: Boolean = false
    var currentUser: RemoteGetUser? = null
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("family", Context.MODE_PRIVATE)


    @SuppressLint("MissingPermission")
    fun isNetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    suspend fun syncronizeDBwithAPI() = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            if (currentUser != null) {
                Timber.d("Synchronizing DB with API")
                val remoteGetlists = familyAPI.getShoppingListsByUser(currentUser!!.id)
                val remoteLists = mutableListOf<RoomShoppingList>()
                for (list in remoteGetlists) {
                    val items = familyAPI.getShoppingListItemsFromList(list.id)
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
        if (true) {
            try {
                Timber.d("Logging in with $email")
                currentUser = familyAPI.login(RemoteCreateUser(password, email))
                val cookie = Preferences.userRoot().get("cookie", "")
                if (cookie.isNotEmpty()) sharedPreferences.edit().putString("cookie", cookie).apply()
                sharedPreferences.edit().putString("userID", currentUser!!.id.toString()).apply()
                syncronizeDBwithAPI()
            } catch (e: Exception) {
                Timber.d("Error while login: ${e.message}")
                return@withContext
            }
        }
    }

    suspend fun getUser(userID: String): RemoteGetUser? = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            try {
                Timber.d("Getting user $userID")
                return@withContext familyAPI.getUser(userID.toInt())
            } catch (e: Exception) {
                Timber.d("Error while login: ${e.message}")
                return@withContext null
            }
        }
        return@withContext null
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
            try {
                Timber.d("Getting invite for user")
                return@withContext familyAPI.getUserInvite(currentUser!!.id)
            } catch (e: Exception) {
                Timber.d("Error while getting invite: ${e.message}")
                return@withContext null
            }
        }
        return@withContext null
    }

    suspend fun invite(email: String) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            try {
                Timber.d("Inviting $email to family ${currentUser!!.familyID}")
                familyAPI.inviteUser(RemoteCreateInvite(email, currentUser!!.familyID!!.toInt()))
            } catch (e: Exception) {
                Timber.d("Error while register: ${e.message}")
            }
        }
    }

    suspend fun editProfile(newUserData: RemoteUser) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            try {
                Timber.d("Updating profile ${currentUser!!.id}")
                familyAPI.editUser(currentUser!!.id, newUserData)
            } catch (e: Exception) {
                Timber.d("Error while editing profile: ${e.message}")
            }
        }
    }

    suspend fun getFamily(): RemoteGetFamily? = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            if (currentUser!!.familyID == null) {
                Timber.d("Current user has no family ID")
                return@withContext null
            }
            try {
                Timber.d("Getting user family data ${currentUser!!.id}, ${currentUser!!.familyID}")
                return@withContext familyAPI.getFamily(currentUser!!.familyID!!.toInt())
            } catch (e: Exception) {
                Timber.d("Error while getting family: ${e.message}")
                return@withContext null
            }
        }
        return@withContext null
    }

    suspend fun editFamily(newFamilyData: RemoteFamily) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            if (currentUser!!.familyID != null) {
                try {
                    Timber.d("Updating family ${currentUser!!.familyID}")
                    familyAPI.editFamily(currentUser!!.familyID!!.toInt(), newFamilyData)
                } catch (e: Exception) {
                    Timber.d("Error while updating family: ${e.message}")
                    return@withContext
                }
            } else Timber.d("User has no family ID")
        }
    }

    suspend fun createFamily(newFamily: RemoteFamily): RemoteGetFamily? =
        withContext(Dispatchers.IO) {
            if (deviceOnline && currentUser != null) {
                try {
                    Timber.d("Creating family for ${currentUser!!.id}")
                    return@withContext familyAPI.createFamily(newFamily)
                } catch (e: Exception) {
                    Timber.d("Error while creating family: ${e.message}")
                    return@withContext null
                }
            }
            return@withContext null
        }

    suspend fun getShoppingLists(): List<ShoppingList> = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            Timber.d("Getting Shopping Lists from API")
            if (currentUser != null) {
                try {
                    val remoteLists = familyAPI.getShoppingListsByUser(currentUser!!.id)
                    val lists = mutableListOf<RoomShoppingList>()
                    for (list in remoteLists) {
                        val items = familyAPI.getShoppingListItemsFromList(list.id)

                        lists.add(list.convertToRoomShoppingList(items))
                    }
                    familyDao.setShoppingLists(lists)
                } catch (e: Exception) {
                    Timber.d("Error while getting shoppingLists: ${e.message}")
                }
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
            try {
                Timber.d("Adding Shopping List to API")
                familyAPI.createShoppingList(
                    shoppingList.convertToRemoteCreateShoppingList(currentUser?.familyID)
                )
            } catch (e: Exception) {
                Timber.d("Error while creating shoppingList: ${e.message}")
            }
        } else {
            Timber.d("Adding Shopping List to DB")
            familyDao.insertShoppingList(shoppingList)
        }
    }

    suspend fun removeShoppingList(listID: Int) = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            try {
                Timber.d("Removing Shopping List $listID from API")
                familyAPI.deleteShoppingList(listID)
            } catch (e: Exception) {
                Timber.d("Error while removing shoppingList: ${e.message}")
            }
        } else {
            familyDao.removeShoppingList(listID)
            Timber.d("Removing Shopping List $listID from DB")
        }
    }

    suspend fun getListById(listID: String): ShoppingList? = withContext(Dispatchers.IO) {
        if (deviceOnline) {
            try {
                Timber.d("Getting List $listID from API")
                val remoteList = familyAPI.getShoppingList(listID.toInt())
                val list =
                    remoteList.convertToRoomShoppingList(
                        familyAPI.getShoppingListItemsFromList(
                            listID.toInt()
                        )
                    )
                return@withContext convertToShoppingList(list)
            } catch (e: Exception) {
                Timber.d("Error while getting shoppingList by id: ${e.message}")
                return@withContext null
            }
        } else {
            Timber.d("Getting List $listID from DB")
            val list = familyDao.getShoppingListById(listID = listID) ?: return@withContext null
            return@withContext convertToShoppingList(list)
        }
    }

    suspend fun addShoppingItem(theListID: String, roomShoppingListItem: RoomShoppingListItem) =
        withContext(Dispatchers.IO) {
            if (deviceOnline) {
                try {
                    Timber.d("Adding Shopping Item to List $theListID to API")
                    familyAPI.addShoppingListItem(
                        theListID.toInt(),
                        RemoteCreateShoppingItem(
                            name = roomShoppingListItem.name,
                            done = roomShoppingListItem.done
                        )
                    )
                } catch (e: Exception) {
                    Timber.d("Error while adding shoppingItem: ${e.message}")
                }
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
                try {
                    if (roomShoppingListItem.done) {
                        familyAPI.markDoneShoppingListItem(
                            theListID.toInt(),
                            roomShoppingListItem.id
                        )
                    } else familyAPI.markUndoneShoppingListItem(
                        theListID.toInt(),
                        roomShoppingListItem.id
                    )
                } catch (e: Exception) {
                    Timber.d("Error while checking shoppingItem: ${e.message}")
                }
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
            try {
                return@withContext familyAPI.getEventsByUser(currentUser!!.id)
            } catch (e: Exception) {
                Timber.d("Error while getting events by user: ${e.message}")
            }
        }
        return@withContext null
    }

    suspend fun createEvent(event: RemoteEvent) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            try {
                familyAPI.createEvent(event = event)
            } catch (e: Exception) {
                Timber.d("Error while creating event: ${e.message}")
            }
        }
    }

    suspend fun deleteEvent(eventID: Int) = withContext(Dispatchers.IO) {
        if (deviceOnline && currentUser != null) {
            try {
                familyAPI.deleteEvent(eventID)
            } catch (e: Exception) {
                Timber.d("Error while deleting event: ${e.message}")
            }
        }
    }
}
