package hu.hm.familyapp.data.model

data class ShoppingListItem(
    val id: String = "",
    val name: String = "TestItem",
    var done: Boolean = false
)
