package hu.hm.familyapp.data.remote

import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.model.User
import hu.hm.familyapp.data.remote.models.*
import java.util.*

fun RoomShoppingList.convertToRemoteCreateShoppingList(familyID: Int?): RemoteCreateShoppingList {
    return RemoteCreateShoppingList(name = name, familyID = familyID)
}

fun RemoteGetShoppingList.convertToRoomShoppingList(items: List<RemoteGetShoppingItem>?): RoomShoppingList {
    val roomItems = mutableListOf<RoomShoppingListItem>()
    if (items != null) {
        for (item in items) {
            roomItems.add(item.convertToRoomShoppingListItem())
        }
    }
    return RoomShoppingList(id = id, name = name, items = roomItems)
}

fun RemoteGetShoppingItem.convertToRoomShoppingListItem(): RoomShoppingListItem {
    return RoomShoppingListItem(id = id, name = name, done = done)
}

fun RemoteGetUser.convertToUser(): User {
    return User(
        id = id,
        password = password,
        email = email,
        username = username,
        lastname = lastname,
        firstname = firstname,
        phoneNumber = phonenumber,
        categoryID = category,
        lastModTime = lastModTime ?: Date(System.currentTimeMillis()),
        familyID = familyID.toString(),
        inviteID = inviteID.toString()
    )
}

fun RoomShoppingList.convertToRemoteShoppingList(): RemoteShoppingList {
    return RemoteShoppingList(
        id = id,
        name = name,
        family = null, // TODO ezt én nem tudom beállítani tehát backendnek kéne korrigálni...
        users = null, // TODO ezt én nem tudom beállítani tehát backendnek kéne korrigálni...
        lastModTime = lastModTime,
        remoteShoppingItems = items.convertToRemoteListOfShoppingItems()
    )
}

fun List<RoomShoppingListItem>.convertToRemoteListOfShoppingItems(): List<RemoteShoppingItem> {
    val list = mutableListOf<RemoteShoppingItem>()
    for (item in this) {
        list.add(
            RemoteShoppingItem(
                id = item.id,
                name = item.name,
                done = item.done,
                lastModTime = item.lastModTime,
                remoteShoppingList = null // TODO ezt én nem tudom beállítani tehát backendnek kéne korrigálni...
            )
        )
    }
    return list
}
