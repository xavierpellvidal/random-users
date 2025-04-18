package com.random.users.data.mapper

import com.random.users.api.model.ResponseInfoDto

internal object SeedMapper {
    fun ResponseInfoDto.toDomain() = this.seed
}
