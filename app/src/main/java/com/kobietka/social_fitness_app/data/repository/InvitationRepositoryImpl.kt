package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.InvitationDao
import com.kobietka.social_fitness_app.data.entity.InvitationEntity
import com.kobietka.social_fitness_app.domain.repository.local.InvitationRepository
import kotlinx.coroutines.flow.Flow


class InvitationRepositoryImpl(private val invitationDao: InvitationDao) : InvitationRepository {
    override suspend fun insert(invitationEntity: InvitationEntity) {
        invitationDao.insert(invitationEntity = invitationEntity)
    }

    override fun getInvitationForGroup(groupId: String): Flow<InvitationEntity?> {
        return invitationDao.getInvitationForGroup(groupId = groupId)
    }

    override suspend fun deleteInvitationById(id: String) {
        invitationDao.deleteInvitationById(id = id)
    }
}