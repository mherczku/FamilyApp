package hu.hm.familyapp.data.remote.models

import java.util.Date

data class RemoteGetShoppingItem(
    val id: Int,
    val name: String,
    val done: Boolean,
    var shoppingListID: Int?,
    val lastModTime: Date? = null,
)
