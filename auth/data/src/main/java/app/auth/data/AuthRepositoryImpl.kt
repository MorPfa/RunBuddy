package app.auth.data

import app.auth.domain.AuthRepository
import app.core.data.networking.post
import app.core.domain.util.DataError
import app.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(email = email, password = password)
        )
    }
}