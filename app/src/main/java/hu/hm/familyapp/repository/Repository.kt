package hu.hm.familyapp.repository

import hu.hm.familyapp.data.convertToShoppingList
import hu.hm.familyapp.data.local.FamilyDao
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.model.ShoppingList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val familyDao: FamilyDao) {

    fun getShoppingLists(): List<ShoppingList> {

        return familyDao.getAllShoppingLists().map {
            convertToShoppingList(it)
        }
    }

    fun addShoppingList(shoppingList: RoomShoppingList) {
        familyDao.insertShoppingList(shoppingList)
    }

    fun removeShoppingList(listID: String) {
        familyDao.removeShoppingList(listID)
    }
}
