package hu.hm.familyapp

import hu.hm.familyapp.data.remote.FamilyAPI
import hu.hm.familyapp.data.remote.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTests {

    lateinit var api: FamilyAPI
    var userID = 1
    var listID = 1
    var itemID = 1

    @Before
    fun init() {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(FamilyAPI::class.java)
    }

    @Test
    fun registerUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            userID = api.register(RemoteCreateUser("password", "email@test3.hu"))
        }
    }

    @Test
    fun loginUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.login(RemoteCreateUser("password", "email@test2.hu"))
        }
    }

    @Test
    fun createFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.createFamily(RemoteFamily(1))
        }
    }

    @Test
    fun editFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editFamily(1, RemoteFamily(1))
        }
    }

    @Test
    fun getFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val f = api.getFamily(1)
            val f2 = RemoteFamily(1)
            assertEquals(f2, f)
        }
    }

    @Test
    fun addUserToFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.addFamilyMember(1, userID)
        }
    }

    @Test
    fun removeUserFromFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.removeFamilyMember(1, userID)
        }
    }

    @Test
    fun deleteFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteFamily(1)
        }
    }

    @Test
    fun createShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            listID = api.createShoppingList(RemoteCreateShoppingList(null, "testList"))
        }
    }

    @Test
    fun editShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editShoppingList(listID, RemoteShoppingList(listID, "Lidl", null))
        }
    }

    @Test
    fun getShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val l = api.getShoppingList(listID)
            val l2 = RemoteShoppingList(listID, "Lidl", null)
            assertEquals(l2, l)
        }
    }

    @Test
    fun addUserToShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.addFamilyMemberToList(listID, userID)
        }
    }

    @Test
    fun removeUserToShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.removeFamilyMemberFromList(listID, userID)
        }
    }

    @Test
    fun getFamilyShoppingListsTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.getShoppingListsByFamily(familyID = 2)
        }
    }

    @Test
    fun addShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            itemID = api.addShoppingListItem(listID, RemoteCreateShoppingItem("Sajt", false))
        }
    }
    @Test
    fun doneShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.markDoneShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun undoneShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.markUndoneShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun editShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editShoppingListItem(listID, itemID, RemoteShoppingItem(itemID, "Alma", false, null))
        }
    }

    @Test
    fun getAllShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val ids = api.getShoppingListItemsFromList(listID)
        }
    }

    @Test
    fun getShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val i = api.getShoppingListItem(listID, itemID)
            val i2 = RemoteShoppingItem(itemID, "Alma", false, null)
            assertEquals(i, i2)
        }
    }

    @Test
    fun deleteShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun deleteShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingList(listID)
        }
    }

    @Test
    fun editUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editUser(userID, RemoteUser("newtest@email.hu", "password"))
        }
    }

    @Test
    fun getUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val u = api.getUser(userID)
            val u2 = RemoteUser("newtest@email.hu", "password")
            assertEquals(u, u2)
        }
    }

    @Test
    fun inviteUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.inviteUser(userID, RemoteCreateInvite("newtest@email.hu", 1))
        }
    }
    @Test
    fun getUserInvitesTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val invs = api.getUserInvites(userID)
        }
    }

    fun deleteUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteUser(userID)
        }
    }
}
