package hu.hm.familyapp.ui.screens.shoppingLists

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.local.model.RoomShoppingList
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.repository.Repository
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ShoppingListsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val newName = mutableStateOf("")
    val dialogShown = mutableStateOf(false)
    val shoppingLists = mutableListOf<ShoppingList>()

    init {
        loadLists()
    }

    private fun loadLists() {
        viewModelScope.launch {
            shoppingLists.clear()
            val list = repository.getShoppingLists()
            shoppingLists.addAll(list)
        }
    }

    fun addNewList() {
        viewModelScope.launch {
            repository.addShoppingList(
                RoomShoppingList(
                    id = Random().nextInt().toString(),
                    name = newName.value,
                    items = listOf()
                )
            )
            loadLists()
        }
    }

    fun removeList(shoppingList: ShoppingList) {
        // shoppingLists.remove(shoppingList)
        viewModelScope.launch {
            repository.removeShoppingList(shoppingList.id)
            loadLists()
        }
    }
}
