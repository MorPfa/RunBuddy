package app.auth.domain

import app.core.domain.util.DataError
import app.core.domain.util.EmptyResult

interface AuthRepository {

    suspend fun login(email : String, password : String) : EmptyResult<DataError.Network>
    suspend fun register(email : String, password : String) : EmptyResult<DataError.Network>

}