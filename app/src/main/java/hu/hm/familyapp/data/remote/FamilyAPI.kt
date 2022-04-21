package hu.hm.familyapp.data.remote

import hu.hm.familyapp.data.model.*
import hu.hm.familyapp.data.remote.models.*
import retrofit2.http.*

interface FamilyAPI {

    companion object {
        const val url = "api"
    }

    // Auth
    @POST("$url/login")
    suspend fun login(@Body user: RemoteCreateUser)

    @POST("$url/register")
    suspend fun register(@Body user: RemoteCreateUser): Int

    // User
    @GET("$url/user/{id}")
    suspend fun getUser(@Path("id") userID: Int): RemoteGetUser

    @PUT("$url/user/{id}")
    suspend fun editUser(
        @Path("id") userID: Int,
        @Body user: RemoteUser
    )

    @DELETE("$url/user/{id}")
    suspend fun deleteUser(
        @Path("id") userID: Int,
    )

    @PUT("$url/user/{id}/sendinvite")
    suspend fun inviteUser(
        @Path("id") userID: Int,
        @Body invite: RemoteCreateInvite
    )

    // TODO ilyen fgv nincs
    @PUT("$url/user/{id}/invites")
    suspend fun getUserInvites(
        @Path("id") userID: Int
    ): List<Int>?

    // Family
    @GET("$url/family/{id}")
    suspend fun getFamily(@Path("id") familyID: Int): RemoteGetFamily

    @PUT("$url/family/{id}")
    suspend fun editFamily(
        @Path("id") familyID: Int,
        @Body family: RemoteFamily
    )

    @DELETE("$url/family/{id}")
    suspend fun deleteFamily(
        @Path("id") familyID: Int,
    )

    @POST("$url/family/create")
    suspend fun createFamily(
        @Body family: RemoteFamily
        // TODO backend picture bytearray-t vár --> meeting
    )

    @PUT("$url/family/{id}/adduser")
    suspend fun addFamilyMember(
        @Path("id") familyID: Int,
        @Body userID: Int
    )

    @PUT("$url/family/{id}/removeuser")
    suspend fun removeFamilyMember(
        @Path("id") familyID: Int,
        @Body userID: Int
    )

    // ShoppingList
    @GET("$url/shoppinglist/{id}")
    suspend fun getShoppingList(@Path("id") listID: Int): RemoteGetShoppingList

    @PUT("$url/shoppinglist/{id}")
    suspend fun editShoppingList(
        @Path("id") listID: Int,
        @Body list: RemoteShoppingList
    )

    @DELETE("$url/shoppinglist/{id}")
    suspend fun deleteShoppingList(
        @Path("id") listID: Int,
    )

    @POST("$url/shoppinglist/create")
    suspend fun createShoppingList(
        @Body list: RemoteCreateShoppingList
    ): Int

    @GET("$url/shoppinglist/byfamily/{id}")
    suspend fun getShoppingListsByFamily(
        @Path("id") familyID: Int
    ): List<Int>

    @PUT("$url/shoppinglist/{id}/adduser")
    suspend fun addFamilyMemberToList(
        @Path("id") listID: Int,
        @Body userID: Int
    )

    @PUT("$url/shoppinglist/{id}/removeuser")
    suspend fun removeFamilyMemberFromList(
        @Path("id") listID: Int,
        @Body userID: Int
    )

    // ShoppingListItem
    @GET("$url/shoppinglist/{listID}/shoppingitem/{itemID}")
    suspend fun getShoppingListItem(
        @Path("listID") listID: Int,
        @Path("itemID") itemID: Int
    ): RemoteGetShoppingItem

    @PUT("$url/shoppinglist/{listID}/shoppingitem/{itemID}")
    suspend fun editShoppingListItem(
        @Path("id") listID: Int,
        @Path("itemID") itemID: Int,
        @Body item: RemoteShoppingItem
    )

    @DELETE("$url/shoppinglist/{listID}/shoppingitem/{itemID}")
    suspend fun deleteShoppingListItem(
        @Path("id") listID: Int,
        @Path("itemID") itemID: Int
    )

    @POST("$url/shoppinglist/{listID}/shoppingitem/add")
    suspend fun addShoppingListItem(
        @Path("listID") listID: Int,
        @Body shoppingItem: RemoteCreateShoppingItem
    ): Int // TODO backend visszaadja e az id-t

    @GET("$url/shoppinglist/{listID}/shoppingitem/all")
    suspend fun getShoppingListItemsFromList(
        @Path("id") listID: Int
    ): List<Int>?

    @PUT("$url/shoppinglist/{listID}/shoppingitem/{itemID}/done")
    suspend fun markDoneShoppingListItem(
        @Path("id") listID: Int,
        @Path("itemID") itemID: Int
    )

    @PUT("$url/shoppinglist/{listID}/shoppingitem/{itemID}/undone")
    suspend fun markUndoneShoppingListItem(
        @Path("id") listID: Int,
        @Path("itemID") itemID: Int
    )
}
