package com.example.data.local.repository

import com.example.data.local.entity.IssuanceEntity
import com.example.data.local.mapping.IssuanceMapper
import com.example.data.local.specification.IssuanceSpecToExpressionMapper
import com.example.domain.model.IssuanceModel
import com.example.domain.repository.IssuanceRepository
import com.example.domain.specification.Specification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class IssuanceRepositoryImpl(
    private val db: Database
) : IssuanceRepository {
    override suspend fun create(issuanceModel: IssuanceModel) = withContext(Dispatchers.IO) {
        transaction(db) {
            IssuanceEntity.insertAndGetId {
                IssuanceMapper.toInsertStatement(issuanceModel, it)
            }.value
//            TODO("Сделать вызов функции по уменьшению общего числа книг")
        }
    }

    override suspend fun update(issuanceModel: IssuanceModel) = withContext(Dispatchers.IO) {
        transaction(db) {
            IssuanceEntity.update({ IssuanceEntity.id eq issuanceModel.id }) {
                IssuanceMapper.toUpdateStatement(issuanceModel, it)
            }
        }
    }

    override suspend fun deleteById(issuanceId: UUID) = withContext(Dispatchers.IO) {
        transaction(db) {
            IssuanceEntity.deleteWhere { id eq issuanceId }
        }
    }

    override suspend fun isContain(spec: Specification<IssuanceModel>) =
        withContext(Dispatchers.IO) {
            query(spec).isNotEmpty()
        }

    override suspend fun query(spec: Specification<IssuanceModel>): List<IssuanceModel> =
        withContext(Dispatchers.IO) {
            val expression = IssuanceSpecToExpressionMapper.map(spec)

            transaction(db) {
                IssuanceEntity.selectAll().where { expression }.map { IssuanceMapper.toDomain(it) }
            }
        }
}