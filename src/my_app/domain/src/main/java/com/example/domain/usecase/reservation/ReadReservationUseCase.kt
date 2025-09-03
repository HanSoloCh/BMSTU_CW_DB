package com.example.domain.usecase.reservation

import com.example.domain.exception.InvalidValueException
import com.example.domain.model.ReservationModel
import com.example.domain.repository.ReservationRepository
import com.example.domain.specification.AndSpecification
import com.example.domain.specification.reservation.ReservationBookIdSpecification
import com.example.domain.specification.reservation.ReservationUserIdSpecification
import java.util.UUID

class ReadReservationUseCase(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(bookId: UUID?, userId: UUID?): List<ReservationModel> {
        if (bookId != null && userId != null)
            return reservationRepository.query(
                AndSpecification(
                    listOf(
                        ReservationBookIdSpecification(bookId),
                        ReservationUserIdSpecification(userId)
                    )
                )
            )
        else if (bookId != null)
            return reservationRepository.query(
                ReservationBookIdSpecification(bookId),
            )
        else if (userId != null)
            return reservationRepository.query(ReservationUserIdSpecification(userId))
        else
            throw InvalidValueException("bookId, userId", "null, null")
    }
}