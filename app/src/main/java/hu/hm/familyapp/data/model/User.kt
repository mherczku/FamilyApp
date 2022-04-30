package hu.hm.familyapp.data.model

import java.util.*

data class User(
    val id: Int = 1,
    val password: String = "testPass",
    val email: String = "test@test.hu",
    val username: String? = "TestUser",
    val lastname: String? = "Testing",
    val firstname: String? = "Tester",
    val phoneNumber: String? = null,
    val profilePicture: String? = null,
    val categoryID: String? = null, // !
    val lastModTime: Date = Date(System.currentTimeMillis()),
    val shoppingListsID: List<String> = listOf(),
    val familyID: String? = null,
    val inviteID: String? = null
)
