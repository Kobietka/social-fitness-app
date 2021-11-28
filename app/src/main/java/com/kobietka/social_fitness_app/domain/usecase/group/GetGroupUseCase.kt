package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.model.Group
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetGroupUseCase(private val groupRepository: GroupRepository) {
    operator fun invoke(groupId: String): Flow<Group?> {
        return groupRepository.getGroupById(groupId = groupId).map { groupEntity ->
            groupEntity?.toGroup()
        }
    }
}