package runbuddy.wear.run.domain

import app.core.connectivity.domain.DeviceNode
import kotlinx.coroutines.flow.StateFlow

interface PhoneConnector {

    val connectedNode: StateFlow<DeviceNode?>
}