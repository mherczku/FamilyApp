package hu.hm.familyapp.ui.screens.shoppingLists

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.model.ShoppingList
import javax.inject.Inject

@HiltViewModel
class ShoppingListsViewModel @Inject constructor() : ViewModel() {

    val shoppingLists = mutableListOf<ShoppingList>()

    init {
        loadLists()
    }

    fun loadLists() {
        shoppingLists.add(ShoppingList())
        shoppingLists.add(ShoppingList())
    }
}
