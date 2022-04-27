package hu.hm.familyapp.data.remote.models

data class RemoteGetInvite(
    val id: Int,
    val familyID: Int? = null,
    val userID: Int? = null
)
