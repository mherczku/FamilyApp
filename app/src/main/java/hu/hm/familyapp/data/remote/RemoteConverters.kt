package hu.hm.familyapp.data.remote

import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.remote.models.RemoteCreateShoppingList

fun RoomShoppingList.convertToRemoteCreateShoppingList(familyID: Int?): RemoteCreateShoppingList {
    return RemoteCreateShoppingList(name = name, familyID = familyID)
}
