package hu.hm.familyapp.data.remote.models

data class RemoteShoppingList(
    val ID: Int,

    val name: String,

    val family: RemoteFamily?, // TODO backendben nem nullos

    var users: List<RemoteUser>? = null,

    val remoteShoppingItems: List<RemoteShoppingItem>? = null
)