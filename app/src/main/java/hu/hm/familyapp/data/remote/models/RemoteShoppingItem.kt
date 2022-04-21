package hu.hm.familyapp.data.remote.models

data class RemoteShoppingItem(
    val ID: Int,
    val name: String,
    var done: Boolean = false,
    val remoteShoppingList: RemoteShoppingList? // TODO backendben nem nullabe
)
