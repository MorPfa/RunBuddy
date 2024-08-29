@file:OptIn(ExperimentalCoroutinesApi::class)

package runbuddy.wear.run.data

import app.core.connectivity.domain.DeviceNode
import app.core.connectivity.domain.DeviceType
import app.core.connectivity.domain.NodeDiscovery
import app.core.connectivity.domain.messaging.MessagingAction
import app.core.connectivity.domain.messaging.MessagingClient
import app.core.connectivity.domain.messaging.MessagingError
import app.core.domain.util.EmptyResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import runbuddy.wear.run.domain.PhoneConnector

class WatchToPhoneConnector(
    nodeDiscovery: NodeDiscovery,
    applicationScope: CoroutineScope,
    private val messagingClient: MessagingClient,
) : PhoneConnector {

    private val _connectedNode = MutableStateFlow<DeviceNode?>(null)
    override val connectedNode = _connectedNode.asStateFlow()

    override val messagingActions = nodeDiscovery
        .observeConnectedDevices(DeviceType.WATCH)
        .flatMapLatest { connectedNodes ->
            val node = connectedNodes.firstOrNull()
            if (node != null && node.isNearby) {
                _connectedNode.value = node
                messagingClient.connectToNode(node.id)
            } else flowOf()
        }
        .shareIn(
            applicationScope,
            SharingStarted.Eagerly
        )


    override suspend fun sendActionToPhone(action: MessagingAction): EmptyResult<MessagingError> {
        return messagingClient.sendOrQueueAction(action)
    }
}