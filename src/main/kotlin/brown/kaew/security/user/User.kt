package brown.kaew.security.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "_user")  // avoid keyword user in postgresql
data class User(
        @Id
        @GeneratedValue
        var id: Int,
        var firstname: String,
        var lastname: String,
        var email: String,
        var password: String
)
