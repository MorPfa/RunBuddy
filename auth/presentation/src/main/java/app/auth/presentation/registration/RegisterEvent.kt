

package app.auth.presentation.registration

import app.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent

    data class Error(val error : UiText) : RegisterEvent

}
