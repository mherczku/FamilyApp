package hu.hm.familyapp.ui.screens.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.repository.Repository
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val emailValue = mutableStateOf("")
    val passwordValue = mutableStateOf("")
    val passwordVisibility = mutableStateOf(false)
    val loading = mutableStateOf(false)

    fun register(success: () -> Unit) {
        viewModelScope.launch {
            loading.value = true
            if(repository.register(emailValue.value, passwordValue.value)) success()
            loading.value = false
        }
    }
}
