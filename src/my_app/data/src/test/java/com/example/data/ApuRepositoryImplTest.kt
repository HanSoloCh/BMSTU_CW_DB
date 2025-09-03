package com.example.data

import com.example.data.local.entity.BbkEntity
import com.example.data.local.repository.ApuRepositoryImpl
import com.example.data.local.repository.BbkRepositoryImpl
import com.example.domain.model.ApuModel
import com.example.domain.specification.apu.ApuTermSpecification
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.util.UUID

class ApuRepositoryImplTest : BasePostgresIntegrationTest() {

    private var repository = ApuRepositoryImpl(db)
    private lateinit var bbkId: UUID

    @Before
    fun setup() {
        transaction(db) {
            bbkId = BbkEntity.insertAndGetId {
                it[code] = "Test Code"
                it[description] = "Test Description"
            }.value
        }
    }

    @Test
    fun `simple create apu test`() = runTest {
        val apu = ApuModel(UUID.randomUUID(), "Test", bbkId)
        val id = repository.create(apu)
        assertEquals(apu.id, id)

        val found = repository.readById(id)
        assertNotNull(found)
        assertEquals(apu, found)
    }

    @Test
    fun `read with specification test`() = runTest {
        val apu = ApuModel(
            id = UUID.randomUUID(),
            term = "Test",
            bbkId = bbkId
        )

        repository.create(apu)
        repository.create(
            ApuModel(
                id = UUID.randomUUID(),
                term = "Other APU",
                bbkId = bbkId
            )
        )

        val result = repository.query(ApuTermSpecification("Test"))
        assertEquals(apu, result.firstOrNull())
    }

    @Test
    fun `delete apu test`() = runTest {
        val newApu = UUID.randomUUID()
        val apu = ApuModel(
            id = newApu,
            term = "Test",
            bbkId = bbkId
        )

        repository.create(apu)

        BbkRepositoryImpl(db).deleteById(bbkId)

        val found = repository.readById(newApu)
        assertNull(found)
    }
}
