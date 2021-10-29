package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.data.entity.toGroupMember
import com.kobietka.social_fitness_app.domain.model.GroupMember
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetGroupMembersUseCase(private val groupMemberRepository: GroupMemberRepository) {
    operator fun invoke(groupId: String): Flow<List<GroupMember>> {
        return groupMemberRepository.getMembersByGroupId(groupId = groupId).map { entities ->
            entities.map { entity -> entity.toGroupMember() }
        }
    }
}