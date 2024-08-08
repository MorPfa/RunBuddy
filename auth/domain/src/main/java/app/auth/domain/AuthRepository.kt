package app.auth.domain

import app.core.domain.util.DataError
import app.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email : String, password : String) : EmptyResult<DataError.Network>

}