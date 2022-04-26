package hu.hm.familyapp.data.remote.models

data class RemoteGetShoppingItem(
    val ID: Int,
    val name: String,
    val done: Boolean,
    var shoppingListID: Int?
)
