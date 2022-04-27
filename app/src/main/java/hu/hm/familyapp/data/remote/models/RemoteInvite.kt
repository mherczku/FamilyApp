package hu.hm.familyapp.data.remote.models

data class RemoteInvite(
    val id: Int,
    val remoteFamily: RemoteFamily? = null,
    val user: RemoteUser? = null
)
