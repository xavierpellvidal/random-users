package com.random.users.users.mapper

import com.random.users.domain.models.UsersErrors
import com.random.users.users.contract.UsersErrorUiState

internal object UsersErrorsMapper {
    fun UsersErrors.toUiError() =
        when (this) {
            is UsersErrors.NetworkError -> UsersErrorUiState.LoadUsersError
            is UsersErrors.UserError -> UsersErrorUiState.DeleteError
            else -> UsersErrorUiState.UnknownError
        }
}
