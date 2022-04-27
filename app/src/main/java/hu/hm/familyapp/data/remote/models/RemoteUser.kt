package hu.hm.familyapp.data.remote.models

data class RemoteUser(
    val email: String,
    val password: String,
    val ID: Int,
    val phonenumber: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val username: String? = null,
    val category: String? = null
)
