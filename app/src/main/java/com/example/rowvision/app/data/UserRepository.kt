package com.example.rowvision.app.data

interface UserRepository {
    /**
     * Returns true if email/password match a stored user.
     */
    fun validateUser(email: String, password: String): Boolean
}