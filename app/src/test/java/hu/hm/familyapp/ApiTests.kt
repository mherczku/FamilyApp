package hu.hm.familyapp

import hu.hm.familyapp.data.remote.AddCookiesInterceptor
import hu.hm.familyapp.data.remote.FamilyAPI
import hu.hm.familyapp.data.remote.ReceivedCookiesInterceptor
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

    companion object {
        private var user: RemoteUser = RemoteUser(email = "test@test4.hu", password = "Test1234", ID = 13)
        private var userID = 13
        private var listID = 21
        private var itemID = 1
        private var familyID = 13
    }

    lateinit var api: FamilyAPI

    @Before
    fun init() {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
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
            user = api.register(RemoteCreateUser("Test1234", "test@test15.hu"))
            userID = user.ID
        }
    }

    @Test
    fun t02loginUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.login(RemoteCreateUser("Test1234", user.email))
        }
    }

    @Test
    fun t03createFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val a: RemoteFamily = api.createFamily(RemoteFamily(familyID))
            familyID = a.ID // TODO kisbetűs / nagybetűs idk....
        }
    }

    @Test
    fun t04editFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editFamily(familyID, RemoteFamily(familyID))
        }
    }

    @Test
    fun t05getFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val f = api.getFamily(familyID)
            val f2 = RemoteGetFamily(familyID)
            assertEquals(f2.ID, f.ID)
        }
    }

    @Test
    fun t06addUserToFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.addFamilyMember(familyID, userID)
        }
    }

    @Test
    fun t07removeUserFromFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.removeFamilyMember(familyID, userID)
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
            val l2 = RemoteGetShoppingList(listID, "Lidl", null)
            assertEquals(l2.id, l.id)
            assertEquals(l2.name, l.name)
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
            val i2 = RemoteGetShoppingItem(itemID, "Alma", false, null)
            assertEquals(i2.id, i.id)
            assertEquals(i2.name, i.name)
        }
    }

    @Test
    fun t23editUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editUser(userID, RemoteUser("newtest@email.hu", user.password, ID = userID))
        }
    }

    @Test
    fun t24getUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val u = api.getUser(userID)
            val u2 = RemoteGetUser(email = user.email, password = user.password, id = userID)
            assertEquals(u2.email, u.email)
            assertEquals(u2.id, u.id)
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
            val invs = api.getUserInvite(userID)
        }
    }

    @Test
    fun t27deleteShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun t28deleteShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingList(listID)
        }
    }

    @Test
    fun t29deleteFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteFamily(familyID)
        }
    }

    @Test
    fun t30deleteUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteUser(userID)
        }
    }
}
