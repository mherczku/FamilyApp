package hu.hm.familyapp.data.model

data class ShoppingList(
    val id : String,
    val name : String,

    val itemsID : List<String>
)
