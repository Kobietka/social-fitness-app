package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.data.entity.GroupEntity
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import com.kobietka.social_fitness_app.domain.model.User
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.network.response.Invitation
import com.kobietka.social_fitness_app.network.response.MemberDto


class InsertGroupDataUseCase(
    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository
) {
    suspend operator fun invoke(
        groupId: String,
        groupName: String,
        groupDescription: String,
        invitation: Invitation?,
        groupOwner: User,
        groupMembers: List<MemberDto>?
    ) {
        groupRepository.insert(
            GroupEntity(
                id = groupId,
                name = groupName,
                description = groupDescription,
                ownerId = groupOwner.id,
                invitationCode = invitation?.code
            )
        )
        groupMemberRepository.insert(
            GroupMemberEntity(
                userId = groupOwner.id,
                groupId = groupId,
                nickname = groupOwner.nickname,
                joinDate = ""
            )
        )
        groupMembers?.let { members ->
            members.forEach { member ->
                groupMemberRepository.insert(
                    GroupMemberEntity(
                        userId = member.id,
                        groupId = groupId,
                        nickname = member.user.nickname,
                        joinDate = member.assignedAt
                    )
                )
            }
        }
    }
}

















