package com.random.users.domain.models

sealed class UsersErrors : Throwable() {
    data object NetworkError : UsersErrors()
    data object SeedError : UsersErrors()
    data object UserError : UsersErrors()
}
