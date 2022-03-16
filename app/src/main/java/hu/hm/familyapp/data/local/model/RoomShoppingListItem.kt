package hu.hm.familyapp.data.local.model

import androidx.room.PrimaryKey

data class RoomShoppingListItem(
    @PrimaryKey
    val id: String,
    val name: String,
    val done: String
)
