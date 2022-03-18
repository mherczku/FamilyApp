package hu.hm.familyapp.data.local.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import hu.hm.familyapp.data.model.ShoppingListItem

class ListConverter {

    @TypeConverter
    fun convertToJson(list: List<RoomShoppingListItem>): String? {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun convertFromJson(json: String): List<RoomShoppingListItem> {
        val array = Gson().fromJson(json, Array<RoomShoppingListItem>::class.java)
        val a = arrayListOf<RoomShoppingListItem>()
        a.addAll(array)
        return a.toList()
    }
}