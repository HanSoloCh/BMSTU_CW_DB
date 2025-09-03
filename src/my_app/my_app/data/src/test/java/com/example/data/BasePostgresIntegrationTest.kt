package com.example.data

import com.example.data.local.entity.ApuEntity
import com.example.data.local.entity.AuthorEntity
import com.example.data.local.entity.BbkEntity
import com.example.data.local.entity.BookAuthorCrossRef
import com.example.data.local.entity.BookEntity
import com.example.data.local.entity.IssuanceEntity
import com.example.data.local.entity.PublisherEntity
import com.example.data.local.entity.ReservationEntity
import com.example.data.local.entity.UserEntity
import com.example.data.local.entity.UserFavoriteCrossRef
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.testcontainers.containers.PostgreSQLContainer

abstract class BasePostgresIntegrationTest {
    companion object {
        val container = PostgreSQLContainer<Nothing>("postgres:16").apply {
            withDatabaseName("testdb")
            withUsername("testuser")
            withPassword("testpass")
            start()
        }
    }

    protected var db = Database.connect(
        url = container.jdbcUrl,
        driver = "org.postgresql.Driver",
        user = container.username,
        password = container.password
    )

    // Создаем БД
    init {
        transaction(db) {
            SchemaUtils.createMissingTablesAndColumns(
                ApuEntity,
                AuthorEntity,
                BbkEntity,
                BookAuthorCrossRef,
                BookEntity,
                IssuanceEntity,
                PublisherEntity,
                ReservationEntity,
                UserEntity,
                UserFavoriteCrossRef
            )
        }
    }

    // Чистим БД после каждого теста
    @After
    fun tearDownDatabase() {
        transaction(db) {
            ReservationEntity.deleteAll()
            IssuanceEntity.deleteAll()
            UserFavoriteCrossRef.deleteAll()
            UserEntity.deleteAll()
            AuthorEntity.deleteAll()
            BookEntity.deleteAll()
            BookAuthorCrossRef.deleteAll()
            PublisherEntity.deleteAll()
            ApuEntity.deleteAll()
            BbkEntity.deleteAll()
        }
    }
}
