@file:OptIn(ExperimentalFoundationApi::class)

package app.auth.presentation.registration


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {

    var state by mutableStateOf(RegisterState())

    fun onAction(action : RegisterAction){

    }
}