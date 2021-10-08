package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCredentialsDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(userCredentialsEntity: UserCredentialsEntity)

    @Query("delete from user")
    suspend fun deleteAllCredentials()

    @Query("select * from user")
    fun getAllUsers(): Flow<List<UserCredentialsEntity>>

}