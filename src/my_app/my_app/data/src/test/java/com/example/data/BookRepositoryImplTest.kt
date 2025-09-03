package com.example.data

import com.example.data.local.entity.AuthorEntity
import com.example.data.local.entity.BbkEntity
import com.example.data.local.entity.PublisherEntity
import com.example.data.local.repository.BookRepositoryImpl
import com.example.domain.model.BookModel
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.util.UUID

class BookRepositoryImplTest : BasePostgresIntegrationTest() {

    private val repository = BookRepositoryImpl(db)
    private lateinit var authorId: UUID
    private lateinit var publisherId: UUID
    private lateinit var bbkId: UUID

    @Before
    fun setup() {
        transaction(db) {
            authorId = AuthorEntity.insertAndGetId {
                it[name] = "Test Author"
            }.value
            publisherId = PublisherEntity.insertAndGetId {
                it[name] = "Test Publisher"
            }.value
            bbkId = BbkEntity.insertAndGetId {
                it[code] = "Test code bbk"
                it[description] = "Test desc"
            }.value
        }
    }

    @Test
    fun `simple create book test`() = runTest {
        val newBookId = UUID.randomUUID()
        val book = BookModel(
            id = newBookId,
            title = "Test Book",
            authors = listOf(authorId),
            publisherId = publisherId,
            bbkId = bbkId
        )

        val createdId = repository.create(book)
        assertEquals(newBookId, createdId)

        val found = repository.readById(createdId)
        assertNotNull(found)
        assertEquals(book, found)
    }

    @Test
    fun `update book test`() = runTest {
        val originalBook = BookModel(
            id = UUID.randomUUID(),
            title = "Original Title",
            authors = listOf(authorId),
            publisherId = publisherId,
            bbkId = bbkId,
        )
        repository.create(originalBook)

        // Новый автор
        val newAuthorId = transaction(db) {
            AuthorEntity.insertAndGetId {
                it[name] = "Second Author"
            }.value
        }

        val updatedBook = originalBook.copy(
            title = "Updated Title",
            authors = listOf(newAuthorId),
        )

        repository.update(updatedBook)

        val result = repository.readById(originalBook.id)!!
        assertEquals(result, updatedBook)
    }

    @Test
    fun `delete book test`() = runTest {
        val book = BookModel(
            id = UUID.randomUUID(),
            title = "Test Book",
            authors = listOf(authorId),
            publisherId = publisherId,
            bbkId = bbkId,
        )
        repository.create(book)

        repository.deleteById(book.id)

        val result = repository.readById(book.id)
        assertNull(result)
    }

    @Test
    fun `get books by author`() = runTest {
        val book = BookModel(
            id = UUID.randomUUID(),
            title = "Test Book",
            authors = listOf(authorId),
            publisherId = publisherId,
            bbkId = bbkId,
        )
        repository.create(book)
        lateinit var anotherAuthorId: UUID
        transaction(db) {
            anotherAuthorId = AuthorEntity.insertAndGetId {
                it[name] = "Test Author 2"
            }.value
        }

        val anotherBook = BookModel(
            id = UUID.randomUUID(),
            title = "Test Book 2",
            authors = listOf(anotherAuthorId),
            publisherId = publisherId,
            bbkId = bbkId,
        )
        repository.create(anotherBook)

        val result = repository.readByAuthorId(authorId)

        assertEquals(result, listOf(book))
    }

}
