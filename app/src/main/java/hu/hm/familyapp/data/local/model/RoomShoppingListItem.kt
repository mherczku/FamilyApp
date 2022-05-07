package hu.hm.familyapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class RoomShoppingListItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    var done: Boolean,
    val lastModTime: Timestamp
)
