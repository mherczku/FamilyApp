package hu.hm.familyapp.data.remote.models

import java.util.*

data class RemoteFamily(
    val id: Int,
    var users: List<RemoteUser>? = null,
    val shoppingLists: List<RemoteShoppingList>? = null,
    val lastModTime: Date? = null,
    val remoteInvites: List<RemoteInvite>? = null
)
