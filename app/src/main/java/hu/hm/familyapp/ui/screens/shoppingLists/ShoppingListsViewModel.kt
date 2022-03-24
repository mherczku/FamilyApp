package hu.hm.familyapp.ui.screens.shoppingLists

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.model.ShoppingList
import javax.inject.Inject

@HiltViewModel
class ShoppingListsViewModel @Inject constructor() : ViewModel() {

    val newName = mutableStateOf("")
    val dialogShown = mutableStateOf(false)
    val shoppingLists = mutableListOf<ShoppingList>()

    init {
        loadLists()
    }

    private fun loadLists() {
        shoppingLists.add(ShoppingList())
        shoppingLists.add(ShoppingList())
    }

    fun addNewList() {
        shoppingLists.add(ShoppingList(id = "2", name = newName.value))
    }
}
