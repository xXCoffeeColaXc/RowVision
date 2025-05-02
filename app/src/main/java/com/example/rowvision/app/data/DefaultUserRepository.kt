package com.example.rowvision.app.data

import com.example.rowvision.app.data.model.User
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(): UserRepository {
    private val users = listOf(
        User("admin@rowvision.com", "admin123")
    )

    override fun validateUser(email: String, password: String): Boolean =
        users.any { it.email.equals(email, ignoreCase = true) && it.password == password }
}