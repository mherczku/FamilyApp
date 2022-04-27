package hu.hm.familyapp.data.remote.models

data class RemoteShoppingList(
    val id: Int,

    val name: String,

    val family: RemoteFamily?,

    var users: List<RemoteUser>? = null,

    val remoteShoppingItems: List<RemoteShoppingItem>? = null
)
