package com.example.domain.usecase.favorite

import com.example.domain.model.BookModel
import com.example.domain.repository.UserFavoriteRepository
import java.util.UUID

class ReadFavoriteByUserIdUseCase(
    private val userFavoriteRepository: UserFavoriteRepository
) {
    suspend operator fun invoke(userId: UUID): List<BookModel> {
        return userFavoriteRepository.readByUserId(userId)
    }
}