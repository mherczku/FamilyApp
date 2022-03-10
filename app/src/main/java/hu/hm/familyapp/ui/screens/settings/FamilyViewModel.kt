package hu.hm.familyapp.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FamilyViewModel @Inject constructor() : ViewModel() {

    val hasFamily = mutableStateOf(false)
    val adults = mutableListOf("Name1", "Name2")
    val children = mutableListOf("Child1", "Child2")
    val pets = mutableListOf("Pet1", "Pet2")

    fun createFamily(){

    }

    fun joinFamily(){
        hasFamily.value = true
    }

}
