package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.InvitationEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface InvitationDao {

    @Insert
    suspend fun insert(invitationEntity: InvitationEntity)

    @Query("select * from invitation where groupId = :groupId")
    fun getInvitationForGroup(groupId: String): Flow<InvitationEntity?>

    @Query("delete from invitation where id = :id")
    suspend fun deleteInvitationById(id: String)

}