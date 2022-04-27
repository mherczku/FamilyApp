package hu.hm.familyapp.data.remote.models

data class RemoteGetFamily(
    val ID: Int,
    var userIDs: List<Int>? = null,
    var shoppingListIDs: List<Int>? = null,
    var inviteIDs: List<Int>? = null
)
