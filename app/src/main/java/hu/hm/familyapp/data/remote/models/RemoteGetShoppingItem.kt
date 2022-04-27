package hu.hm.familyapp.data.remote.models

data class RemoteGetShoppingItem(
    val id: Int,
    val name: String,
    val done: Boolean,
    var shoppingListID: Int?
)
