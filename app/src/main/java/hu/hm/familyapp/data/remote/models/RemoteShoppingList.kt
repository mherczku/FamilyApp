package hu.hm.familyapp.data.remote.models

import java.util.*

data class RemoteShoppingList(
    val id: Int,
    val name: String,
    val family: RemoteFamily?,
    var users: List<RemoteUser>? = null,
    val lastModTime: Date? = null,
    val remoteShoppingItems: List<RemoteShoppingItem>? = null
)
