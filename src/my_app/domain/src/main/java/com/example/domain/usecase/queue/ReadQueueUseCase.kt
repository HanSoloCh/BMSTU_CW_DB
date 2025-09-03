package com.example.domain.usecase.queue

import com.example.domain.exception.InvalidValueException
import com.example.domain.model.QueueModel
import com.example.domain.repository.QueueRepository
import com.example.domain.specification.AndSpecification
import com.example.domain.specification.queue.QueueBookIdSpecification
import com.example.domain.specification.queue.QueueUserIdSpecification
import java.util.UUID

class ReadQueueUseCase(
    private val queueRepository: QueueRepository
) {
    suspend operator fun invoke(bookId: UUID?, userId: UUID?): List<QueueModel> {
        if (bookId != null && userId != null)
            return queueRepository.query(
                AndSpecification(
                    listOf(
                        QueueBookIdSpecification(bookId),
                        QueueUserIdSpecification(userId)
                    )
                )
            )
        else if (bookId != null)
            return queueRepository.query(
                QueueBookIdSpecification(bookId),
            )
        else if (userId != null)
            return queueRepository.query(QueueUserIdSpecification(userId))
        else
            throw InvalidValueException("bookId, userId", "null, null")
    }
}