package hu.hm.familyapp.data.remote.models

data class RemoteFamily(
    val id: Int,
    var users: List<RemoteUser>? = null,
    val shoppingLists: List<RemoteShoppingList>? = null,
    val remoteInvites: List<RemoteInvite>? = null
)
