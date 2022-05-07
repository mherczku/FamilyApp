package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp
import java.util.Date

data class RemoteGetShoppingList(
    val id: Int,
    val name: String,
    var familyID: Int?,
    var userIDs: List<Int>? = null,
    var shoppingItemIDs: List<Int>? = null,
    val lastModTime: Timestamp? = null
)
