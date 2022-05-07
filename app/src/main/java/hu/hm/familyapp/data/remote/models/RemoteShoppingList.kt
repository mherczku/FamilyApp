package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp
import java.util.*

data class RemoteShoppingList(
    val id: Int,
    val name: String,
    val family: RemoteFamily?,
    var users: List<RemoteUser>? = null,
    val lastModTime: Timestamp? = null,
    val remoteShoppingItems: List<RemoteShoppingItem>? = null
)
