package app.core.domain.run

import app.core.domain.util.DataError
import app.core.domain.util.EmptyResult
import app.core.domain.util.Result

interface RemoteRunDatasource {

    suspend fun getRuns() : Result<List<Run>, DataError.Network>

    suspend fun postRun(run : Run, mapPicture : ByteArray) : Result<Run, DataError.Network>

    suspend fun deleteRun(id : String) : EmptyResult<DataError.Network>
}