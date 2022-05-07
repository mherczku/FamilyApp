package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp
import java.util.*

data class RemoteInvite(
    val id: Int,
    val remoteFamily: RemoteFamily? = null,
    val lastModTime: Timestamp? = null,
    val user: RemoteUser? = null
)
