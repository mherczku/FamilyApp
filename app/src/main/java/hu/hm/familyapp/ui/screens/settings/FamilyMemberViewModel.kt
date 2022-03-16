package hu.hm.familyapp.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.hm.familyapp.data.model.User
import hu.hm.familyapp.data.model.UserCategory
import javax.inject.Inject

@HiltViewModel
class FamilyMemberViewModel @Inject constructor() : ViewModel() {

    val user = mutableStateOf(User())
    val category = mutableStateOf(UserCategory())
    val menuExpanded = mutableStateOf(false)
}
