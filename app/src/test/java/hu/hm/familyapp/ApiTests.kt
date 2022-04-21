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
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ApiTests {

    lateinit var api: FamilyAPI
    private var userID = 1
    private var listID = 1
    private var itemID = 1

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
    fun t01registerUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            userID = api.register(RemoteCreateUser("password", "email@test4.hu"))
        }
    }

    @Test
    fun t02loginUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.login(RemoteCreateUser("password", "email@test2.hu"))
        }
    }

    @Test
    fun t03createFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.createFamily(RemoteFamily(1))
        }
    }

    @Test
    fun t04editFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editFamily(1, RemoteFamily(1))
        }
    }

    @Test
    fun t05getFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val f = api.getFamily(1)
            val f2 = RemoteFamily(1)
            assertEquals(f2, f)
        }
    }

    @Test
    fun t06addUserToFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.addFamilyMember(1, userID)
        }
    }

    @Test
    fun t07removeUserFromFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.removeFamilyMember(1, userID)
        }
    }

    @Test
    fun t08deleteFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteFamily(1)
        }
    }

    @Test
    fun t09createShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            listID = api.createShoppingList(RemoteCreateShoppingList(null, "testList"))
        }
    }

    @Test
    fun t10editShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editShoppingList(listID, RemoteShoppingList(listID, "Lidl", null))
        }
    }

    @Test
    fun t11getShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val l = api.getShoppingList(listID)
            val l2 = RemoteShoppingList(listID, "Lidl", null)
            assertEquals(l2, l)
        }
    }

    @Test
    fun t12addUserToShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.addFamilyMemberToList(listID, userID)
        }
    }

    @Test
    fun t13removeUserToShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.removeFamilyMemberFromList(listID, userID)
        }
    }

    @Test
    fun t14getFamilyShoppingListsTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.getShoppingListsByFamily(familyID = 2)
        }
    }

    @Test
    fun t15addShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            itemID = api.addShoppingListItem(listID, RemoteCreateShoppingItem("Sajt", false))
        }
    }
    @Test
    fun t16doneShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.markDoneShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun t17undoneShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.markUndoneShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun t18editShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editShoppingListItem(listID, itemID, RemoteShoppingItem(itemID, "Alma", false, null))
        }
    }

    @Test
    fun t19getAllShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val ids = api.getShoppingListItemsFromList(listID)
        }
    }

    @Test
    fun t20getShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val i = api.getShoppingListItem(listID, itemID)
            val i2 = RemoteShoppingItem(itemID, "Alma", false, null)
            assertEquals(i, i2)
        }
    }

    @Test
    fun t21deleteShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun t22eleteShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingList(listID)
        }
    }

    @Test
    fun t23editUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editUser(userID, RemoteUser("newtest@email.hu", "password"))
        }
    }

    @Test
    fun t24getUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val u = api.getUser(userID)
            val u2 = RemoteUser("newtest@email.hu", "password")
            assertEquals(u, u2)
        }
    }

    @Test
    fun t25inviteUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.inviteUser(userID, RemoteCreateInvite("newtest@email.hu", 1))
        }
    }
    @Test
    fun t26getUserInvitesTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val invs = api.getUserInvites(userID)
        }
    }

    @Test
    fun t27deleteUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteUser(userID)
        }
    }
}
