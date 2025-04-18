package com.random.users.domain.usecase

import arrow.core.left
import arrow.core.right
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.mother.UserMother
import com.random.users.domain.repository.UsersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

class GetUserListUseCaseUnitTest {
    private lateinit var usersRepository: UsersRepository
    private lateinit var getUserListUseCase: GetUserListUseCase

    @Before
    fun setUp() {
        usersRepository = mockk()
        getUserListUseCase = GetUserListUseCase(usersRepository)
    }

    @Test
    fun `GIVEN deleted user in get users result WHEN getUserListUseCase THEN deleted user removed from result`() =
        runBlocking {
            val users =
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                )
            val deletedUsers = listOf("2")
            coEvery { usersRepository.getUsers(any(), any()) } returns users.right()
            coEvery { usersRepository.getDeletedUsers() } returns deletedUsers.right()

            val result = getUserListUseCase(any<Int>())

            Assert.assertEquals(
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "3"),
                ).right(),
                result,
            )
            coVerify { usersRepository.getUsers(any(), any()) }
            coVerify { usersRepository.getDeletedUsers() }
        }

    @Test
    fun `GIVEN no deleted users in get users result WHEN getUserListUseCase THEN return all results`() =
        runBlocking {
            val users =
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                )
            coEvery { usersRepository.getUsers(any(), any()) } returns users.right()
            coEvery { usersRepository.getDeletedUsers() } returns emptyList<String>().right()

            val result = getUserListUseCase(any<Int>())

            Assert.assertEquals(
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                ).right(),
                result,
            )
            coVerify { usersRepository.getUsers(any(), any()) }
            coVerify { usersRepository.getDeletedUsers() }
        }

    @Test
    fun `GIVEN all users deleted in get users result WHEN getUserListUseCase THEN return no results`() =
        runBlocking {
            val users =
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                )
            val deletedUsers = listOf("1", "2", "3")
            coEvery { usersRepository.getUsers(any(), any()) } returns users.right()
            coEvery { usersRepository.getDeletedUsers() } returns deletedUsers.right()

            val result = getUserListUseCase(any<Int>())

            Assert.assertEquals(emptyList<String>().right(), result)
            coVerify { usersRepository.getUsers(any(), any()) }
            coVerify { usersRepository.getDeletedUsers() }
        }

    @Test
    fun `GIVEN left in getUsers WHEN getUserListUseCase THEN returned left`() =
        runBlocking {
            val error = UsersErrors.NetworkError
            coEvery { usersRepository.getUsers(any(), any()) } returns error.left()

            val result = getUserListUseCase(any<Int>())

            Assert.assertEquals(error.left(), result)
            coVerify { usersRepository.getUsers(any(), any()) }
            coVerify(exactly = 0) { usersRepository.getDeletedUsers() }
        }

    @Test
    fun `GIVEN left in getDeletedUsers and users in getUsers WHEN getUserListUseCase THEN return all results`() =
        runBlocking {
            val users =
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                )
            coEvery { usersRepository.getUsers(any(), any()) } returns users.right()
            coEvery { usersRepository.getDeletedUsers() } returns UsersErrors.UserError.left()

            val result = getUserListUseCase(any<Int>())

            Assert.assertEquals(
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                ).right(),
                result,
            )
            coVerify { usersRepository.getUsers(any(), any()) }
            coVerify { usersRepository.getDeletedUsers() }
        }
}
