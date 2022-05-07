package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp
import java.util.Date

data class RemoteGetFamily(
    val id: Int,
    var userIDs: List<Int>? = null,
    var shoppingListIDs: List<Int>? = null,
    var inviteIDs: List<Int>? = null,
    val lastModTime: Timestamp? = null
)
