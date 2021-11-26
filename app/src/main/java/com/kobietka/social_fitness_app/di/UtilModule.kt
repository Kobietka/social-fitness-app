package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.util.DateUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Provides
    fun provideDateUtil(): DateUtil {
        return DateUtil()
    }

}