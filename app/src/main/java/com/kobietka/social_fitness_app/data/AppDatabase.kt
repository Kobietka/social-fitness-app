package com.kobietka.social_fitness_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity

@Database(entities = [UserCredentialsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userCredentialsDao(): UserCredentialsDao
}