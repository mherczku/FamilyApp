package hu.hm.familyapp.data.local

import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.data.model.ShoppingListItem
import java.util.Date

fun convertToShoppingList(roomShoppingList: RoomShoppingList): ShoppingList {
    return ShoppingList(
        id = roomShoppingList.id,
        name = roomShoppingList.name,
        items = roomShoppingList.items
    )
}

fun convertToRoomShoppingListItem(shoppingListItem: ShoppingListItem): RoomShoppingListItem {
    return RoomShoppingListItem(
        id = shoppingListItem.id,
        name = shoppingListItem.name,
        done = shoppingListItem.done,
        lastModTime = Date(System.currentTimeMillis())
    )
}
