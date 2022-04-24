package hu.hm.familyapp.data.model

data class ShoppingListItem(
    val id: Int = 1,
    val name: String = "TestItem",
    var done: Boolean = false
)
