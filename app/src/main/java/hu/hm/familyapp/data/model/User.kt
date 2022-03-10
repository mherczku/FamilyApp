package hu.hm.familyapp.data.model

data class User(
    val id : String,
    val password : String,
    val email : String,
    val username : String,
    val lastname : String,
    val firstname : String,
    val phoneNumber : String,
    val profilePicture : String,
    val categoryID : String, //!

    val shoppingListsID: List<String>
)
