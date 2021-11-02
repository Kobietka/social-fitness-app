package com.kobietka.social_fitness_app.domain.usecase.invitation

import com.kobietka.social_fitness_app.data.entity.toInvitation
import com.kobietka.social_fitness_app.domain.model.Invitation
import com.kobietka.social_fitness_app.domain.repository.local.InvitationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetInvitationUseCase(private val invitationRepository: InvitationRepository) {
    operator fun invoke(groupId: String): Flow<Invitation?> {
        return invitationRepository.getInvitationForGroup(groupId = groupId).map {
            it?.toInvitation()
        }
    }
}