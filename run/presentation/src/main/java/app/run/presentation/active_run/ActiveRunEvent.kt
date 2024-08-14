package app.run.presentation.active_run

import app.core.presentation.ui.UiText

sealed interface ActiveRunEvent {
    data class Error(val error : UiText) : ActiveRunAction
    data object RunSaved : ActiveRunAction
}