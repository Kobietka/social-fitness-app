package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.model.Group
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository


class GetGroupUseCase(private val groupRepository: GroupRepository) {
    suspend operator fun invoke(groupId: String): Group {
        return groupRepository.getGroupById(groupId = groupId).toGroup()
    }
}