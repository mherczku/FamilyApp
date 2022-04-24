package hu.hm.familyapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists")
data class RoomShoppingList(
    @PrimaryKey
    val id: Int,
    val name: String,
    var items: List<RoomShoppingListItem>
)
