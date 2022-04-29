package hu.hm.familyapp.data.remote.models

import java.util.*

data class RemoteShoppingItem(
    val id: Int,
    val name: String,
    var done: Boolean = false,
    val lastModTime: Date? = null,
    val remoteShoppingList: RemoteShoppingList?
)
