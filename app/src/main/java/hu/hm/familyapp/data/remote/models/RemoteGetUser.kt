package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp
import java.util.Date

data class RemoteGetUser(
    val id: Int = 0,
    val email: String,
    val password: String,
    val phonenumber: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val username: String? = null,
    val category: String? = null,
    var familyID: Int? = null,
    var inviteID: Int? = null,
    var shoppingListIDs: List<Int>? = null,
    val lastModTime: Timestamp? = null
)
