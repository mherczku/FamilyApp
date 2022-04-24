package hu.hm.familyapp.data.model

data class User(
    val id: Int = 1,
    val password: String = "testPass",
    val email: String = "test@test.hu",
    val username: String = "TestUser",
    val lastname: String = "Testing",
    val firstname: String = "Tester",
    val phoneNumber: String = "06701234567",
    val profilePicture: String = "",
    val categoryID: String = "cat1", // !

    val shoppingListsID: List<String> = listOf("0", "1")
)
