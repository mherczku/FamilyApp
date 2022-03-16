package hu.hm.familyapp.data.model

data class Family(
    val id: String,

    val usersID: List<String>,
    val listsID: List<String>
)
