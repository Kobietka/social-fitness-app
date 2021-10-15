package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.model.Group
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetGroupsUseCase(private val groupRepository: GroupRepository) {
    operator fun invoke(): Flow<List<Group>> {
        return groupRepository.getAllGroups().map { groups ->
            groups.map { it.toGroup() }
        }
    }
}