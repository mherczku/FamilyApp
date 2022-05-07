package hu.hm.familyapp.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.repository.Repository
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val emailValue = mutableStateOf("")
    val passwordValue = mutableStateOf("")
    val passwordVisibility = mutableStateOf(false)
    val loadingButton = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val emailError = mutableStateOf(false)

    fun login(success: () -> Unit) {
        viewModelScope.launch {
            loadingButton.value = true
            repository.login(emailValue.value, passwordValue.value)
            loadingButton.value = false
            if (repository.currentUser != null) success()
        }
    }

    fun validatePassword() {
        if (passwordValue.value.isEmpty())
            passwordError.value = true
        passwordError.value = false
    }

    fun validateEmail() {
        if (emailValue.value.isEmpty())
            emailError.value = true
        emailError.value = !Patterns.EMAIL_ADDRESS.matcher(emailValue.value).matches()
    }
}
