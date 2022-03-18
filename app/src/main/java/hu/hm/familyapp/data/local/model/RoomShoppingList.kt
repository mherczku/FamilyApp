package hu.hm.familyapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists")
data class RoomShoppingList(
    @PrimaryKey
    val id: String,
    val name: String,
    val items: List<RoomShoppingListItem>
)
