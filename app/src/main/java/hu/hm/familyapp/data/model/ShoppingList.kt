package hu.hm.familyapp.data.model

data class ShoppingList(
    val id: String = "",
    val name: String = "TestList1",

    var itemsID: List<String> = listOf("item1", "item2")
)
