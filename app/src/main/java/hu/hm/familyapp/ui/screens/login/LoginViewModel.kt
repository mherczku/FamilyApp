package hu.hm.familyapp.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val emailValue = mutableStateOf("")
    val passwordValue = mutableStateOf("")
    val passwordVisibility = mutableStateOf(false)
    val loadingButton = mutableStateOf(false)

    fun login( success: () -> Unit ) {
        viewModelScope.launch {
            loadingButton.value = true
            delay(1000)
            loadingButton.value = false
            success()
        }
    }

}