package hu.hm.familyapp.data

import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.model.ShoppingList

fun convertToShoppingList(roomShoppingList: RoomShoppingList): ShoppingList {
    return ShoppingList(
        id = roomShoppingList.id,
        name = roomShoppingList.name,
        itemsID = roomShoppingList.items.map { it.id }
    )
}
