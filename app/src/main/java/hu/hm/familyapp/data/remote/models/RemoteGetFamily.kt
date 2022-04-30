package hu.hm.familyapp.data.remote.models

import java.util.Date

data class RemoteGetFamily(
    val id: Int,
    var userIDs: List<Int>? = null,
    var shoppingListIDs: List<Int>? = null,
    var inviteIDs: List<Int>? = null,
    val lastModTime: Date? = null
)
