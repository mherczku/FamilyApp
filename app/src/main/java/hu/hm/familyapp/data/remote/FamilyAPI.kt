package hu.hm.familyapp.data.remote

import hu.hm.familyapp.BuildConfig
import hu.hm.familyapp.data.model.*
import hu.hm.familyapp.data.remote.models.RemoteUser
import retrofit2.http.*

interface FamilyAPI {

    companion object {
        const val url = BuildConfig.API_BASE_URL
    }

    // Auth
    @POST("$url/login")
    suspend fun login(@Body user: RemoteUser)

    @POST("$url/register")
    suspend fun register(@Body user: RemoteUser)

    // User
    @GET("$url/user/{id}")
    suspend fun getUser(@Path("id") userID: String): User

    @PUT("$url/user/{id}")
    suspend fun editUser(
        @Path("id") userID: String,
        @Body user: User
    )

    @DELETE("$url/user/{id}")
    suspend fun deleteUser(
        @Path("id") userID: String,
    )

    @PUT("$url/user/{id}/sendinvite")
    suspend fun inviteUser(
        @Path("id") userID: String,
        @Body invite: Invite
    )

    // Family
    @GET("$url/family/{id}")
    suspend fun getFamily(@Path("id") familyID: String): Family

    @PUT("$url/family/{id}")
    suspend fun editFamily(
        @Path("id") familyID: String,
        @Body family: Family
    )

    @DELETE("$url/family/{id}")
    suspend fun deleteFamily(
        @Path("id") familyID: String,
    )

    @POST("$url/family/create/{id}")
    suspend fun createFamily(
        @Path("id") userID: String,
        @Body family: Family
    )

    @PUT("$url/family/{id}/adduser")
    suspend fun addFamilyMember(
        @Path("id") familyID: String,
        @Body userID: String
    )

    @PUT("$url/family/{id}/removeuser")
    suspend fun removeFamilyMember(
        @Path("id") familyID: String,
        @Body userID: String
    )

    // ShoppingList
    @GET("$url/shoppinglist/{id}")
    suspend fun getShoppingList(@Path("id") listID: String): ShoppingList

    @PUT("$url/shoppinglist/{id}")
    suspend fun editShoppingList(
        @Path("id") listID: String,
        @Body list: ShoppingList
    )

    @DELETE("$url/shoppinglist/{id}")
    suspend fun deleteShoppingList(
        @Path("id") listID: String,
    )

    @POST("$url/shoppinglist/create/{id}")
    suspend fun createShoppingList(
        @Path("id") userID: String,
        @Body list: ShoppingList
    )

    @GET("$url/shoppinglist/byfamily/{id}")
    suspend fun getShoppingListsByFamily(
        @Path("id") familyID: String
    ): List<ShoppingList>

    @PUT("$url/shoppinglist/{id}/adduser")
    suspend fun addFamilyMemberToList(
        @Path("id") listID: String,
        @Body userID: String
    )

    @PUT("$url/shoppinglist/{id}/removeuser")
    suspend fun removeFamilyMemberFromList(
        @Path("id") listID: String,
        @Body userID: String
    )

    // ShoppingListItem
    @GET("$url/shoppinglist/{listID}/shoppingitem/{itemID}")
    suspend fun getShoppingListItem(
        @Path("listID") listID: String,
        @Path("itemID") itemID: String
    ): ShoppingListItem

    @PUT("$url/shoppinglist/{listID}/shoppingitem/{itemID}")
    suspend fun editShoppingListItem(
        @Path("id") listID: String,
        @Path("itemID") itemID: String,
        @Body item: ShoppingListItem
    )

    @DELETE("$url/shoppinglist/{listID}/shoppingitem/{itemID}")
    suspend fun deleteShoppingListItem(
        @Path("id") listID: String,
        @Path("itemID") itemID: String
    )

    @POST("$url/shoppinglist/{listID}/shoppingitem/{itemID}/add")
    suspend fun addShoppingListItem(
        @Path("id") userID: String,
        @Path("itemID") itemID: String,
        @Body shoppingItem: ShoppingListItem
    )

    @GET("$url/shoppinglist/{listID}/shoppingitem/all")
    suspend fun getShoppingListItemsFromList(
        @Path("id") listID: String
    ): List<ShoppingListItem>

    @PUT("$url/shoppinglist/{listID}/shoppingitem/{itemID}/done")
    suspend fun markDoneShoppingListItem(
        @Path("id") listID: String,
        @Path("itemID") itemID: String
    )

    @PUT("$url/shoppinglist/{listID}/shoppingitem/{itemID}/undone")
    suspend fun markUndoneShoppingListItem(
        @Path("id") listID: String,
        @Path("itemID") itemID: String
    )
}
