package hu.hm.familyapp.ui.screens.shoppingList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.local.model.RoomShoppingListItem
import hu.hm.familyapp.data.model.ShoppingListItem
import hu.hm.familyapp.repository.Repository
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ShoppingListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val shoppingItems: MutableLiveData<List<ShoppingListItem>> by lazy {
        MutableLiveData()
    }
    val newName = mutableStateOf("")
    val dialogShown = mutableStateOf(false)
    private var theListID: String = ""

    fun loadLists(listID: String) {
        theListID = listID
        viewModelScope.launch {
            val list = repository.getListById(listID)
            val itemsList: MutableList<ShoppingListItem> = mutableListOf()
            list?.itemsID?.forEach { it ->
                itemsList.add(ShoppingListItem(id = it, name = it))
            }
            shoppingItems.postValue(itemsList)
        }
    }

    fun addNewItem() {
        viewModelScope.launch {
            repository.addShoppingItem(
                theListID,
                RoomShoppingListItem(
                    id = Random().nextInt().toString(),
                    name = newName.value,
                    done = false
                )
            )
            loadLists(theListID)
        }
    }
}
