package hu.hm.familyapp.ui.screens.shoppingList

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.model.ShoppingListItem
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor() : ViewModel() {

    val shoppingItems = mutableListOf<ShoppingListItem>()

    init {
        loadLists()
    }

    fun loadLists() {
        shoppingItems.add(ShoppingListItem())
        shoppingItems.add(ShoppingListItem())
    }
}
