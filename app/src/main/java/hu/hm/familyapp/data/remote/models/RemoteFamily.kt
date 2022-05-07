package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp
import java.util.*

data class RemoteFamily(
    val id: Int,
    var users: List<RemoteUser>? = null,
    val shoppingLists: List<RemoteShoppingList>? = null,
    val lastModTime: Timestamp? = null,
    val remoteInvites: List<RemoteInvite>? = null
)
