package com.kobietka.social_fitness_app.domain.usecase.group

import app.cash.turbine.test
import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat
import com.kobietka.social_fitness_app.data.repository.TestGroupRemoteRepository
import com.kobietka.social_fitness_app.data.repository.TestGroupRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteGroupUseCaseTest {

    private val groupRemoteRepository = mockk<TestGroupRemoteRepository>()
    private val groupRepository = mockk<TestGroupRepository>()

    @Test
    fun deleteGroup_Success() = runBlocking {
        coEvery {
            groupRemoteRepository.deleteGroup("groupId")
        } returns NetworkResult.Success(data = true)
        coEvery {
            groupRepository.deleteGroupById("groupId")
        } returns Unit
        val deleteGroup = DeleteGroupUseCase(
            groupRepository = groupRepository,
            groupRemoteRepository = groupRemoteRepository
        )
        deleteGroup(groupId = "groupId").test {
            assertThat(awaitItem()).isInstanceOf(Progress.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(Progress.Finished::class.java)
            awaitComplete()
        }
    }

}















