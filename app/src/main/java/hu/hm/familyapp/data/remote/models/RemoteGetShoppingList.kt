package hu.hm.familyapp.data.remote.models

data class RemoteGetShoppingList(
    val ID: Int,
    val name: String,
    var familyID: Int,
    var userIDs: List<Int>? = null,
    var shoppingItemIDs: List<Int>? = null
)
