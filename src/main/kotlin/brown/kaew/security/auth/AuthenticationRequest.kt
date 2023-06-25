package brown.kaew.security.auth

data class AuthenticationRequest(
        val email: String,
        val password: String,
)
