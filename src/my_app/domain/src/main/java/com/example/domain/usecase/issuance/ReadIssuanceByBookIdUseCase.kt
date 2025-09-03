package com.example.domain.usecase.issuance

import com.example.domain.model.IssuanceModel
import com.example.domain.repository.IssuanceRepository
import com.example.domain.specification.issuance.IssuanceBookIdSpecification
import java.util.*

class ReadIssuanceByBookIdUseCase(
    private val issuanceRepository: IssuanceRepository
) {
    suspend operator fun invoke(bookId: UUID): List<IssuanceModel> {
        return issuanceRepository.query(IssuanceBookIdSpecification(bookId))
    }
}