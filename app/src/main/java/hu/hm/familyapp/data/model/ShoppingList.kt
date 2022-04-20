package hu.hm.familyapp.data.model

import hu.hm.familyapp.data.local.model.RoomShoppingListItem

data class ShoppingList(
    val id: String = "",
    val name: String = "TestList1",

    var items: List<RoomShoppingListItem> = mutableListOf()
)
