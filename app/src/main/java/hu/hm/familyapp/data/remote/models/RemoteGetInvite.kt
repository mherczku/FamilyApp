package hu.hm.familyapp.data.remote.models

import java.util.Date

data class RemoteGetInvite(
    val id: Int,
    val familyID: Int? = null,
    val userID: Int? = null,
    val lastModTime: Date? = null,
)
