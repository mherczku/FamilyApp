package hu.hm.familyapp.data.remote.models

data class RemoteInvite(
    val ID: Int,
    val remoteFamily: RemoteFamily? = null,
    val user: RemoteUser? = null
)
