package hu.hm.familyapp.data.remote.models

import java.util.*

data class RemoteInvite(
    val id: Int,
    val remoteFamily: RemoteFamily? = null,
    val lastModTime: Date? = null,
    val user: RemoteUser? = null
)
