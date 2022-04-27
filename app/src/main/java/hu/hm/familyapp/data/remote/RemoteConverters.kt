package hu.hm.familyapp.data.remote

import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.remote.models.RemoteCreateShoppingList
import hu.hm.familyapp.data.remote.models.RemoteGetShoppingItem
import hu.hm.familyapp.data.remote.models.RemoteGetShoppingList

fun RoomShoppingList.convertToRemoteCreateShoppingList(familyID: Int?): RemoteCreateShoppingList {
    return RemoteCreateShoppingList(name = name, familyID = familyID)
}

fun RemoteGetShoppingList.convertToRoomShoppingList(items: List<RemoteGetShoppingItem>?): RoomShoppingList {
    val roomItems = mutableListOf<RoomShoppingListItem>()
    if (items != null) {
        for(item in items){
            roomItems.add(item.convertToRoomShoppingListItem())
        }
    }
    return RoomShoppingList(id = id, name = name, items = roomItems)
}

fun RemoteGetShoppingItem.convertToRoomShoppingListItem(): RoomShoppingListItem {
    return RoomShoppingListItem(id = id, name = name, done = done)
}
