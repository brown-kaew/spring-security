package brown.kaew.security.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "_user")  // avoid keyword user in postgresql
data class User(
        @Id
        @GeneratedValue
        var id: Int? = null,
        var firstname: String,
        var lastname: String,
        var email: String,
        @JvmField var password: String,
        @Enumerated(EnumType.STRING)
        var role: Role
) : UserDetails {

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return mutableListOf(SimpleGrantedAuthority(role.name))
        }

        override fun getPassword(): String {
                return password
        }

        override fun getUsername(): String {
                return email
        }

        override fun isAccountNonExpired(): Boolean {
                return true
        }

        override fun isAccountNonLocked(): Boolean {
                return true
        }

        override fun isCredentialsNonExpired(): Boolean {
                return true
        }

        override fun isEnabled(): Boolean {
               return true
        }

}
