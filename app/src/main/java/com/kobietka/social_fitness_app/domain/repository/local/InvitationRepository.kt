package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.InvitationEntity
import kotlinx.coroutines.flow.Flow


interface InvitationRepository {
    suspend fun insert(invitationEntity: InvitationEntity)
    fun getInvitationForGroup(groupId: String): Flow<InvitationEntity?>
    suspend fun deleteInvitationById(id: String)
}