package hu.hm.familyapp.ui.screens.events

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
            if (list != null) items.postValue(list)
            else items.postValue(listOf())
            val mock = mutableListOf<RemoteEvent>()
            mock.add(RemoteEvent(1, "Étkező", "", "Vacsora", Timestamp(10), Timestamp(20), 1, 1))
            mock.add(RemoteEvent(1, "János-hegy", "", "Kirándulás", Timestamp(10), Timestamp(20), 1, 1))
            items.postValue(mock)
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
