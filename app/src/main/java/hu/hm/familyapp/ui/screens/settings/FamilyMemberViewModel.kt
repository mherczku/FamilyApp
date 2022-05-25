package hu.hm.familyapp.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.model.User
import javax.inject.Inject

@HiltViewModel
class FamilyMemberViewModel @Inject constructor() : ViewModel() {

    val user = mutableStateOf(User(1, "", "tester.bela@gmail.com", "bela10", "Tester", "Béla", "+36-70-123-1234"))
    val menuExpanded = mutableStateOf(false)
}
