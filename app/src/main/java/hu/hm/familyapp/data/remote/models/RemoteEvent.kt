package hu.hm.familyapp.data.remote.models

import java.sql.Timestamp

data class RemoteEvent(
    val id: Int,
    val location: String,
    val description: String,
    val name: String,
    val end: Timestamp,
    val start: Timestamp,
    val userID: Int,
    val familyID: Int
)
