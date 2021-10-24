package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.post.CreatePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class PostModule {

    @Provides
    fun provideCreatePostUseCase(
        postRemoteRepository: PostRemoteRepository,
        postRepository: PostRepository
    ): CreatePostUseCase {
        return CreatePostUseCase(
            postRemoteRepository = postRemoteRepository,
            postRepository = postRepository
        )
    }

}