package hu.hm.familyapp.ui.screens.shoppingLists

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.repository.Repository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ShoppingListsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val newName = mutableStateOf("")
    val dialogShown = mutableStateOf(false)
    val shoppingLists = mutableListOf<ShoppingList>()

    init {
        loadLists()
    }

    private fun loadLists() {
        repository.getShoppingLists().toCollection(shoppingLists)
    }

    fun addNewList() {
        repository.addShoppingList(RoomShoppingList(id = Random().nextInt().toString(), name = newName.value, items = listOf()))
        loadLists()
    }
}
