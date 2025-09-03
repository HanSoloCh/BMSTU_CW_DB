package com.example.domain.usecase.reservation

import com.example.domain.model.ReservationModel
import com.example.domain.repository.ReservationRepository
import com.example.domain.specification.reservation.ReservationBookIdSpecification
import java.util.*

class ReadReservationByBookIdUseCase(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(bookId: UUID): List<ReservationModel> {
        return reservationRepository.query(ReservationBookIdSpecification(bookId))
    }
}