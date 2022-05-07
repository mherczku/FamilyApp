package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp
import java.util.Date

data class RemoteGetShoppingItem(
    val id: Int,
    val name: String,
    val done: Boolean,
    var shoppingListID: Int?,
    val lastModTime: Timestamp? = null,
)
