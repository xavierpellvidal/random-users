package com.random.users.data.mapper

import com.random.users.api.model.ResponseInfoDto

object SeedMapper {
    fun ResponseInfoDto.toDomain() = this.seed
}
