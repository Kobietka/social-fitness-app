package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity

@Dao
interface UserCredentialsDao {

    @Insert
    suspend fun insert(userCredentialsEntity: UserCredentialsEntity)

    @Query("delete from userCredentials")
    suspend fun deleteAllCredentials()
}