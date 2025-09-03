package com.example.domain.usecase.publisher

import com.example.domain.model.PublisherModel
import com.example.domain.repository.PublisherRepository
import com.example.domain.specification.publisher.PublisherNameSpecification

class ReadPublisherByNameUseCase(
    private val publisherRepository: PublisherRepository
) {
    suspend operator fun invoke(publisherName: String): List<PublisherModel> {
        return publisherRepository.query(PublisherNameSpecification(publisherName))
    }
}

