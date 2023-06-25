package brown.kaew.security.auth

import brown.kaew.security.config.JwtService
import brown.kaew.security.user.Role
import brown.kaew.security.user.User
import brown.kaew.security.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
        val userRepository: UserRepository,
        val passwordEncoder: BCryptPasswordEncoder,
        val jwtService: JwtService,
        val authenticationManager: AuthenticationManager
) {


    fun registor(request: RegisterRequest): AuthenticationResponse {
        val user = User(
                null,
                request.firstname,
                request.lastname,
                request.email,
                passwordEncoder.encode(request.password),
                Role.USER)
        val save = userRepository.save(user)
        val jwtToken = jwtService.generateToken(save)
        return AuthenticationResponse(jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        val authenticate = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        request.email,
                        request.password
                )
        )
        if (!authenticate.isAuthenticated) {
            throw UsernameNotFoundException("User not authenticated")
        }

        val user = userRepository.findByEmail(request.email)
        user ?: throw UsernameNotFoundException("User not found")

        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }
}