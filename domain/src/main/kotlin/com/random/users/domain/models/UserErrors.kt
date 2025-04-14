package com.random.users.domain.models

sealed class UserErrors : Throwable() {
    data object NetworkError : UserErrors()
    data object SeedError : UserErrors()
}
