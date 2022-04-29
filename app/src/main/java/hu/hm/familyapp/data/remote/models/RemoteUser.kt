package hu.hm.familyapp.data.remote.models

import java.util.*

data class RemoteUser(
    val email: String,
    val password: String,
    val id: Int,
    val phonenumber: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val username: String? = null,
    val lastModTime: Date? = null,
    val category: String? = null
)
