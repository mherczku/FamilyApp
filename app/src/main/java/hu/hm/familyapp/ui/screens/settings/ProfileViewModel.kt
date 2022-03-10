package hu.hm.familyapp.ui.screens.settings

import android.telephony.PhoneNumberUtils
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor() : ViewModel() {

    val loading = mutableStateOf(false)
    val nickName = mutableStateOf("")
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val phoneNumber = mutableStateOf("")
    private val countryCode: String = Locale.getDefault().country
    val phoneNumberError = mutableStateOf(false)


    fun pn(number: String) {

        //TODO CountryCode chooser?
        val phoneNumberFormatter = PhoneNumberUtils.formatNumber(number, "hu")
        Timber.d("Number $number -> Formatted: $phoneNumberFormatter, Cc = $countryCode")
        phoneNumber.value = phoneNumberFormatter?.toString() ?: number
        if(phoneNumberFormatter != null){
            phoneNumberError.value = !((phoneNumberFormatter.length == 14 || phoneNumberFormatter.length == 15)
                    && !phoneNumberFormatter.contains(regex = Regex("[a-zA-Z]+")))
        } else phoneNumberError.value = true

    }

}