package runbuddy.wear.run.domain

import app.core.connectivity.domain.DeviceNode
import app.core.connectivity.domain.messaging.MessagingAction
import app.core.connectivity.domain.messaging.MessagingError
import app.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PhoneConnector {

    val connectedNode: StateFlow<DeviceNode?>
    val messagingActions : Flow<MessagingAction>
    suspend fun sendActionToPhone(action: MessagingAction) : EmptyResult<MessagingError>
}