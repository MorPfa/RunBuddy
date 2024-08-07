@file:OptIn(ExperimentalFoundationApi::class)

package app.auth.presentation.registration


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegistrationViewModel(private val userDataValidator: UserDataValidator) : ViewModel() {

    var state by mutableStateOf(RegisterState())
    init {
        state.email.textAsFlow().onEach { email ->
            state = state.copy(
                isEmailValid = userDataValidator.isValidEmail(email.toString())
            )
        }.launchIn(viewModelScope)

        state.password.textAsFlow().onEach { password ->
            state = state.copy(
                passwordValidationState = userDataValidator.validatePassword(password.toString())
            )
        }.launchIn(viewModelScope)
    }


    fun onAction(action : RegisterAction){

    }
}