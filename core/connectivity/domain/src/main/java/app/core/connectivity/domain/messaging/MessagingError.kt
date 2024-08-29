package app.core.connectivity.domain.messaging

import app.core.domain.util.Error

enum class MessagingError : Error {
    CONNECTION_INTERRUPTED,
    DISCONNECTED,
    UNKNOWN

}