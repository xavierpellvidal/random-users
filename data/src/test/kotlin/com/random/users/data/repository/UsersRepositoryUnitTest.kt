package com.random.users.data.repository

import arrow.core.left
import arrow.core.right
import com.random.users.data.datasource.SeedLocalDataSource
import com.random.users.data.datasource.UsersLocalDataSource
import com.random.users.data.datasource.UsersRemoteDataSource
import com.random.users.data.mapper.UserMapper.toDomain
import com.random.users.data.mother.RandomUsersResponseMother
import com.random.users.data.mother.ResponseInfoDtoMother
import com.random.users.data.mother.UserDtoMother
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.repository.UsersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class UsersRepositoryUnitTest {
    private lateinit var usersRepository: UsersRepository
    private val usersLocalDataSource: UsersLocalDataSource = mockk()
    private val usersRemoteDataSource: UsersRemoteDataSource = mockk()
    private val seedLocalDataSource: SeedLocalDataSource = mockk()

    @Before
    fun setUp() {
        usersRepository = UsersRepositoryImpl(usersLocalDataSource, usersRemoteDataSource, seedLocalDataSource)
    }

    @Test
    fun `GIVEN users from remote data source WHEN getUsers THEN return users successfully`() =
        runBlocking {
            val mockSeed = "mock-seed"
            val newSeed = "new-seed"
            val users = listOf(UserDtoMother.createModel())

            coEvery { seedLocalDataSource.getSeed() } returns mockSeed.right()
            coEvery { usersRemoteDataSource.getUsers(PAGE, RESULTS, mockSeed) } returns
                RandomUsersResponseMother
                    .createModel(
                        results = users,
                        info = ResponseInfoDtoMother.createModel(seed = newSeed),
                    ).right()
            coEvery { seedLocalDataSource.saveSeed(newSeed) } returns Unit.right()

            val result = usersRepository.getUsers(PAGE, RESULTS)

            Assert.assertEquals(users.toDomain().right(), result)
            coVerify { seedLocalDataSource.getSeed() }
            coVerify { usersRemoteDataSource.getUsers(PAGE, RESULTS, mockSeed) }
            coVerify { seedLocalDataSource.saveSeed(newSeed) }
        }

    @Test
    fun `GIVEN error from remote data source WHEN getUsers THEN return error`() =
        runBlocking {
            val error = UsersErrors.NetworkError
            coEvery { seedLocalDataSource.getSeed() } returns null.right()
            coEvery { usersRemoteDataSource.getUsers(PAGE, RESULTS, null) } returns error.left()

            val result = usersRepository.getUsers(PAGE, RESULTS)

            Assert.assertEquals(error.left(), result)
            coVerify { seedLocalDataSource.getSeed() }
            coVerify { usersRemoteDataSource.getUsers(PAGE, RESULTS, null) }
            coVerify(exactly = 0) { seedLocalDataSource.saveSeed(any()) }
        }

    @Test
    fun `GIVEN deleted users from local data source WHEN getDeletedUsers THEN return deleted users`() =
        runBlocking {
            val deletedUsers = listOf("uuid-1", "uuid-2")
            coEvery { usersLocalDataSource.getDeletedUsers() } returns deletedUsers.right()

            val result = usersRepository.getDeletedUsers()

            Assert.assertEquals(deletedUsers.right(), result)
            coVerify { usersLocalDataSource.getDeletedUsers() }
        }

    @Test
    fun `GIVEN error when deleting user WHEN deleteUser THEN return error`() =
        runBlocking {
            val uuid = "uuid-1"
            val error = UsersErrors.UserError
            coEvery { usersLocalDataSource.deleteUser(uuid) } returns error.left()

            val result = usersRepository.deleteUser(uuid)

            Assert.assertEquals(error.left(), result)
            coVerify { usersLocalDataSource.deleteUser(uuid) }
        }

    @Test
    fun `GIVEN successful deletion of user WHEN deleteUser THEN return success`() =
        runBlocking {
            val uuid = "uuid-1"
            coEvery { usersLocalDataSource.deleteUser(uuid) } returns Unit.right()

            val result = usersRepository.deleteUser(uuid)

            Assert.assertEquals(Unit.right(), result)
            coVerify { usersLocalDataSource.deleteUser(uuid) }
        }

    companion object {
        private const val PAGE = 1
        private const val RESULTS = 15
    }
}
