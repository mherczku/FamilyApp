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
        private var user: RemoteUser = RemoteUser(email = "test@test4.hu", password = "Test1234", id = 13)
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

    /*@Test
    fun t01registerUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            user = api.register(RemoteCreateUser("password", "email@test11.hu"))
            userID = user.id
        }
    }*/

    @Test
    fun t02loginUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.login(RemoteCreateUser(user.password, user.email))
        }
    }

    @Test
    fun t03createFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val a: RemoteFamily = api.createFamily(RemoteFamily(familyID))
            familyID = a.id
        } // TODO két fajta idt ad vissza id, ID
    } // TODO aztán egy idő után infinite lista ez is, valami nagyon fura

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
            assertEquals(f2.id, f.id)
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
    fun t08deleteFamilyTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteFamily(familyID)
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
    } // TODO végtelen dolgokat akar visszaadni

    @Test
    fun t11getShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            val l = api.getShoppingList(listID)
            val l2 = RemoteShoppingList(listID, "Lidl", null)
            assertEquals(l2.ID, l.ID)
            assertEquals(l2.name, l.name)
        }
    }
    /*

    Expected :RemoteShoppingList(ID=21, name=Lidl, family=null, users=null, remoteShoppingItems=null)
    Actual   :RemoteGetShoppingList(ID=0, name=Lidl, familyID=0, userIDs=[13], shoppingItemIDs=[])
    */

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
    } // TODO vegtelen megint

    @Test
    fun t17undoneShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.markUndoneShoppingListItem(listID, itemID)
        }
    } // TODO vegtelen megint

    @Test
    fun t18editShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editShoppingListItem(listID, itemID, RemoteShoppingItem(itemID, "Alma", false, null))
        }
    } // TODO vegtelen megint

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
            assertEquals(i2, i)
        }
    } // TODO 1 idju get kéréshez 0 es idju itemet kaptam

    @Test
    fun t21deleteShoppingItemTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingListItem(listID, itemID)
        }
    }

    @Test
    fun t22deleteShoppingListTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteShoppingList(listID)
        }
    }

    @Test
    fun t23editUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.editUser(userID, RemoteUser("newtest@email.hu", user.password, id = userID))
        }
    } // TODO UserID not match with the userE's id
    // https://family-app-kotlin-backend.herokuapp.com/api/user/13
    // {"email":"newtest@email.hu","password":"Test1234","id":13}

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
    fun t27deleteUserTest(): Unit = runBlocking {

        launch(Dispatchers.Default) {
            api.deleteUser(userID)
        }
    } // TODO 204 no content
}
