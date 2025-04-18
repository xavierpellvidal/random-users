package com.random.users.users.mapper

import com.random.users.domain.models.UsersErrors
import com.random.users.users.contract.UsersErrorUiEventsState

internal object UsersErrorsMapper {
    fun UsersErrors.toUiError() =
        when (this) {
            is UsersErrors.NetworkError -> UsersErrorUiEventsState.LoadUsersError
            is UsersErrors.UserError -> UsersErrorUiEventsState.DeleteError
            else -> UsersErrorUiEventsState.UnknownError
        }
}
