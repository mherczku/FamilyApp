package hu.hm.familyapp.ui.screens.events

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.model.ShoppingList
import hu.hm.familyapp.data.remote.models.RemoteEvent
import hu.hm.familyapp.repository.Repository
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EventsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val newName = mutableStateOf("")
    val dialogShown = mutableStateOf(false)
    val items: MutableLiveData<List<RemoteEvent>> by lazy {
        MutableLiveData()
    }

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            val list = repository.getEventsByUser()
            items.postValue(list)
        }
    }

    fun addNewEvent() {
        viewModelScope.launch {
            if (repository.currentUser != null) {
                repository.createEvent(
                    RemoteEvent(
                        id = Random().nextInt(),
                        name = newName.value,
                        location = "",
                        description = "",
                        end = Timestamp(System.currentTimeMillis() + 200),
                        start = Timestamp(System.currentTimeMillis() - 200),
                        userID = repository.currentUser!!.id,
                        familyID = repository.currentUser!!.familyID
                    )
                )
            }
            loadEvents()
        }
    }

    fun removeEvent(event: RemoteEvent) {
        viewModelScope.launch {
            repository.deleteEvent(eventID = event.id)
            loadEvents()
        }
    }
}
