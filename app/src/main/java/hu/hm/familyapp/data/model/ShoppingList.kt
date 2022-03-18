package hu.hm.familyapp.data.model

data class ShoppingList(
    val id: String = "",
    val name: String = "TestList1",

    val itemsID: List<String> = listOf("item1", "item2")
)
